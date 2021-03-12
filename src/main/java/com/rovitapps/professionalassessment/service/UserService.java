package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rovitapps.professionalassessment.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findByUserName(String username){
        return repository.findByUsername(username);
    }

}
