package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.model.User;
import com.rovitapps.professionalassessment.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public Optional<User> findByUserName(String username){
        LOGGER.debug(String.format("UserService.findByUserName %s", username));
        return repository.findByUsername(username);
    }

}
