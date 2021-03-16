package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.model.User;
import com.rovitapps.professionalassessment.repository.UserRepository;
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
public class UserService  implements ClientDetailsService {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findByUserName(String username){
        return repository.findByUsername(username);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        BaseClientDetails clientDetails;
        try{
            var user = repository.findByUsername(clientId);
            clientDetails = new BaseClientDetails();
            clientDetails.setClientId(user.getUsername());
            clientDetails.setClientSecret(user.getPassword());
            clientDetails.setResourceIds(Arrays.asList("front-app"));
            clientDetails.setScope(Arrays.asList("webservice-read"));
            clientDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials"));

            var roles = user.getRoles().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
            String[] itemsArray = new String[roles.size()];
            clientDetails.setAuthorities(AuthorityUtils.createAuthorityList(roles.toArray(itemsArray)));

            clientDetails.setAccessTokenValiditySeconds(600);

        }catch (Exception e){
            String messsage = messageSource.getMessage("generic.error", null, Locale.getDefault());
            throw new ClientRegistrationException(messsage);
        }

        return clientDetails;
    }

}
