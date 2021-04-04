package com.rovitapps.professionalassessment.security.oauth2;

import com.rovitapps.professionalassessment.exception.OAuth2AuthenticationProcessingException;
import com.rovitapps.professionalassessment.model.AuthProvider;
import com.rovitapps.professionalassessment.model.Role;
import com.rovitapps.professionalassessment.model.User;
import com.rovitapps.professionalassessment.repository.UserRepository;
import com.rovitapps.professionalassessment.security.Roles;
import com.rovitapps.professionalassessment.security.UserPrincipal;
import com.rovitapps.professionalassessment.security.oauth2.user.OAuth2UserInfo;
import com.rovitapps.professionalassessment.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClient rest;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void validateOAuth2UserInfo(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
                oAuth2UserRequest.getClientRegistration(),
                oAuth2UserInfo.getName(),
                oAuth2UserRequest.getAccessToken());
        String url = (String) oAuth2UserInfo.getAttributes().get("organizations_url");

        List<Map<String, Object>> orgs = rest
                .get().uri(url)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (!orgs.stream().anyMatch(org -> "AmeDigital".equals(org.get("login")))) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in AmeDigital Team", ""));
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        validateOAuth2UserInfo(oAuth2UserRequest, oAuth2UserInfo);

        Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getUserName());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setUsername(oAuth2UserInfo.getUserName());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.getRoles().add(new Role(Roles.ROLE_USER.name()));

        // caso seja o primeiro User, ganha todas as roles
        if (userRepository.count() == 0) {
            user.setRoles(Roles.stream().map(Enum::name).map(Role::new).collect(Collectors.toList()));
        }

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
