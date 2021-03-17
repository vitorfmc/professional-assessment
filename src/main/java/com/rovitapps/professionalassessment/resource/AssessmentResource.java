package com.rovitapps.professionalassessment.resource;

import com.rovitapps.professionalassessment.dto.AssessmentUpdateDTO;
import com.rovitapps.professionalassessment.dto.GenericResponseDto;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.service.AssessmentService;
import com.rovitapps.professionalassessment.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@PreAuthorize("#oauth2.hasScope('webservice-read')")
@RestController
@RequestMapping(value = "/api/v1/assessment")
@CrossOrigin
public class AssessmentResource {

    private static final Logger LOGGER = LogManager.getLogger(AssessmentResource.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private AssessmentService service;

    @GetMapping(value = "/{oneAOneId}/{username}")
    public ResponseEntity findOne(@PathVariable("oneAOneId") String oneAOneId, @PathVariable("username") String username){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[AssessmentResource.findOne][" + requestId + "] Started");

            var response = service.findByOneAOneAndUser(oneAOneId, username);

            LOGGER.info("[AssessmentResource.findOne][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), response, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[AssessmentResource.findOne][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

    @PutMapping(value = "/{oneAOneId}/{username}")
    public ResponseEntity update(@PathVariable("oneAOneId") String oneAOneId, @PathVariable("username") String username,
                                 @Valid @RequestBody AssessmentUpdateDTO dto){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[AssessmentResource.update][" + requestId + "] Started");

            var response = service.update(oneAOneId, username, dto.getConcepts());

            LOGGER.info("[AssessmentResource.update][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), response, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[AssessmentResource.update][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }
    }

}
