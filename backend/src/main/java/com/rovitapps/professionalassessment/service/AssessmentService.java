package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.model.Assessment;
import com.rovitapps.professionalassessment.model.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class AssessmentService {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private OneAOneService oneAOneService;


    public Assessment findByOneAOneAndUser(String oneAOneId, String username) throws DataValidationException {

        var oneAOne = oneAOneService.find(oneAOneId);

        var assessment = oneAOne.getCreatorAssessments().getOwner().getUsername().equals(username) ?
                oneAOne.getCreatorAssessments() : oneAOne.getEvaluatedAssessments();

        if(assessment == null)
            throw new DataValidationException(Arrays.asList(
                    messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));

        return assessment;
    }


    public Assessment update(String oneAOneId, String username, List<Concept> concepts) throws DataValidationException {

        Assessment assessment;
        var oneAOne = oneAOneService.find(oneAOneId);

        if(oneAOne.getCreatorAssessments().getOwner().getUsername().equals(username)){
            assessment = oneAOne.getCreatorAssessments();
            oneAOne.getCreatorAssessments().setConcepts(concepts);
            oneAOne.getCreatorAssessments().setUpdateDate(new Date());

        }else if(oneAOne.getEvaluatedAssessments() != null
                && oneAOne.getEvaluatedAssessments().getOwner().getUsername().equals(username)){
            assessment = oneAOne.getEvaluatedAssessments();
            oneAOne.getEvaluatedAssessments().setConcepts(concepts);
            oneAOne.getEvaluatedAssessments().setUpdateDate(new Date());

        }else {
            throw new DataValidationException(Arrays.asList(
                    messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));
        }

        oneAOneService.save(oneAOne);

        return assessment;
    }

}
