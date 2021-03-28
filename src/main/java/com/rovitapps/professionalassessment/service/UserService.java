package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.controller.AssessmentTemplateRestController;
import com.rovitapps.professionalassessment.model.User;
import com.rovitapps.professionalassessment.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService implements ClientDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findByUserName(String username){
        LOGGER.debug(String.format("UserService.findByUserName %s", username));
        return repository.findByUsername(username);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        LOGGER.debug(String.format("UserService.loadClientByClientId %s", clientId));

        BaseClientDetails clientDetails;
        try{
            var user = repository.findByUsername(clientId);
            LOGGER.debug(String.format("UserService.loadClientByClientId user: %s", user.getId()));
            clientDetails = new BaseClientDetails();
            clientDetails.setClientId(user.getUsername());
            clientDetails.setClientSecret(user.getPassword());
            clientDetails.setResourceIds(Arrays.asList("professional-assessment-api"));
            clientDetails.setScope(Arrays.asList("webservice-read"));
            clientDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials"));

            var roles = user.getRoles().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
            String[] itemsArray = new String[roles.size()];
            clientDetails.setAuthorities(AuthorityUtils.createAuthorityList(roles.toArray(itemsArray)));

            clientDetails.setAccessTokenValiditySeconds(600);

        }catch (Exception e){
            LOGGER.error("UserService.loadClientByClientId ERROR", e);
            String messsage = messageSource.getMessage("generic.error", null, Locale.getDefault());
            throw new ClientRegistrationException(messsage);
        }

        return clientDetails;
    }

}
