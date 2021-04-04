package com.rovitapps.professionalassessment.controller;

import com.rovitapps.professionalassessment.dto.GenericResponseDto;
import com.rovitapps.professionalassessment.dto.OneAOneCreateDTO;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.service.OneAOneService;
import com.rovitapps.professionalassessment.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@PreAuthorize("hasRole('ROLE_USER')")
@RestController
@RequestMapping(value = "/api/v1/oneaone")
@CrossOrigin
public class OneAOneRestController {

    private static final Logger LOGGER = LogManager.getLogger(OneAOneRestController.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private OneAOneService service;

    @GetMapping
    public ResponseEntity findAll(@RequestParam(required = false, defaultValue = "10") int limit,
                                  @RequestParam(required = false, defaultValue = "0") int offset){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[OneAOneRestController.findAll][" + requestId + "] Started");

            var response = service.findAllByUser(getLoggedUsername(), limit, offset);

            LOGGER.info("[OneAOneRestController.findAll][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), response, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneRestController.findAll][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findOne(@PathVariable("id") String id){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[OneAOneRestController.findOne][" + requestId + "] Started");

            var actual = service.find(id, getLoggedUsername());

            LOGGER.info("[OneAOneRestController.findOne][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), actual, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneRestController.findOne][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody OneAOneCreateDTO dto) {
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[OneAOneRestController.create][" + requestId + "] Started - body: " + dto);

            var date = Utils.strToDate(dto.getOneAOneDate());

            var response = service.create(dto.getCreatorUsername(), dto.getEvaluatedUsername(),
                    dto.getAssessmentTemplateId(), date);

            LOGGER.info("[OneAOneRestController.create][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new GenericResponseDto(HttpStatus.CREATED.value(), new ArrayList<>(), response, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneRestController.create][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }

    }

    private String getLoggedUsername(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
