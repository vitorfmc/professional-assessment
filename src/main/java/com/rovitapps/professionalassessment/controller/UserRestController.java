package com.rovitapps.professionalassessment.controller;

import com.rovitapps.professionalassessment.dto.GenericResponseDto;
import com.rovitapps.professionalassessment.service.UserService;
import com.rovitapps.professionalassessment.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@PreAuthorize("#oauth2.hasScope('webservice-read')")
@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin
public class UserRestController {

    private static final Logger LOGGER = LogManager.getLogger(UserRestController.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity findAll(){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[UserRestController.findAll][" + requestId + "] Started");

            var response = service.findAll();

            LOGGER.info("[UserRestController.findAll][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), response, requestId));

        }catch (Exception e){
            LOGGER.error("[UserRestController.findAll][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

}
