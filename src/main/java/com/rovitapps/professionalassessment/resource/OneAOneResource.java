package com.rovitapps.professionalassessment.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rovitapps.professionalassessment.dto.GenericResponseDto;
import com.rovitapps.professionalassessment.dto.OneAOneCreateDTO;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.service.OneAOneService;
import com.rovitapps.professionalassessment.util.Utils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@RestController
@RequestMapping(value = "/api/v1/oneaone")
@CrossOrigin
public class OneAOneResource {

    private static final Logger LOGGER = LogManager.getLogger(OneAOneService.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private OneAOneService service;

    @GetMapping
    public ResponseEntity findAll(){
        String requestId = Utils.getUuid();

        try{
            LOGGER.info("[OneAOneResource.findAll][" + requestId + "] Started");

            var response = service.findAll();

            LOGGER.info("[OneAOneResource.findAll][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponseDto(HttpStatus.OK.value(), new ArrayList<>(), response, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneResource.findAll][" + requestId + "] Error: " + e.getMessage(), e);

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
            LOGGER.info("[OneAOneResource.delete][" + requestId + "] Started");

            service.delete(id);

            LOGGER.info("[OneAOneResource.delete][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new GenericResponseDto(HttpStatus.NO_CONTENT.value(), Arrays.asList("Deleted"), null, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneResource.delete][" + requestId + "] Error: " + e.getMessage(), e);

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
            LOGGER.info("[OneAOneResource.create][" + requestId + "] Started - body: " + dto);

            var date = Utils.strToDate(dto.getOneAOneDate());

            var response = service.create(dto.getCreatorUsername(), dto.getEvaluatedUsername(),
                    dto.getAssessmentTemplateName(), date);

            LOGGER.info("[OneAOneResource.create][" + requestId + "] Ended");

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new GenericResponseDto(HttpStatus.CREATED.value(), new ArrayList<>(), response, requestId));

        }catch (DataValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessages(), null, requestId));

        }catch (Exception e){
            LOGGER.error("[OneAOneResource.create][" + requestId + "] Error: " + e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Arrays.asList(messageSource.getMessage("generic.error", null,
                                    Locale.getDefault())), null, requestId));
        }

    }

}
