package com.rovitapps.professionalassessment.controller;

import com.rovitapps.professionalassessment.dto.GenericResponseDto;
import com.rovitapps.professionalassessment.dto.ReunionAssessmentUpdateDTO;
import com.rovitapps.professionalassessment.dto.ReunionUpdateDTO;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.service.ReunionService;
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
@RequestMapping(value = "/api/v1/reunion")
@CrossOrigin
public class ReunionRestController {

    private static final Logger LOGGER = LogManager.getLogger(ReunionRestController.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private ReunionService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity findOne(@PathVariable("id") String id){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[ReunionRestController.findOne][" + requestId + "] Started");

            var actual = service.findOne(id, getLoggedUsername());

            LOGGER.info("[ReunionRestController.findOne][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), actual, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[ReunionRestController.findOne][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateReunionInfo(@PathVariable("id") String id, @Valid @RequestBody ReunionUpdateDTO dto){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[ReunionRestController.updateReunionInfo][" + requestId + "] Started");

            var date = Utils.strToDate(dto.getDate());

            service.updateReunionInfo(id, date, dto.getComments(), dto.getActions(), getLoggedUsername());

            LOGGER.info("[ReunionRestController.updateReunionInfo][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), null, null, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[ReunionRestController.updateReunionInfo][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @PutMapping(value = "/updateAssessment/{id}")
    public ResponseEntity updateAssessment(@PathVariable("id") String id, @Valid @RequestBody ReunionAssessmentUpdateDTO dto){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[ReunionRestController.updateAssessment][" + requestId + "] Started");

            var date = Utils.strToDate(dto.getUpdateDate());

            service.updateAssessment(id, dto.getAssessmentId(), date, dto.getConcepts(), getLoggedUsername());

            LOGGER.info("[ReunionRestController.updateAssessment][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), null, null, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[ReunionRestController.updateAssessment][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @PutMapping(value = "/close/{id}")
    public ResponseEntity close(@PathVariable("id") String id){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[ReunionRestController.close][" + requestId + "] Started");

            service.close(id, getLoggedUsername());

            LOGGER.info("[ReunionRestController.close][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), null, null, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[ReunionRestController.close][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[ReunionRestController.delete][" + requestId + "] Started");

            service.delete(id, getLoggedUsername());

            LOGGER.info("[ReunionRestController.delete][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new GenericResponseDto(HttpStatus.NO_CONTENT.value(), null, null, requestId));

        }catch (Exception e){
            LOGGER.error("[ReunionRestController.delete][" + requestId + "] Error: " + e.getMessage(), e);

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
