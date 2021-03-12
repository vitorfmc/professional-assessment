package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rovitapps.professionalassessment.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findByUserName(String username){
        return repository.findByUsername(username);
    }

}
