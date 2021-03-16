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

        var assessment = oneAOne.getApplicantUser().getUsername().equals(username) ?
                oneAOne.getApplicantUserAssessment() : oneAOne.getGuestUserAssessment();

        if(assessment == null)
            throw new DataValidationException(Arrays.asList(
                    messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));

        return assessment;
    }


    public Assessment update(String oneAOneId, String username, List<Concept> concepts) throws DataValidationException {

        Assessment assessment;
        var oneAOne = oneAOneService.find(oneAOneId);

        if(oneAOne.getApplicantUserAssessment() != null
                && oneAOne.getApplicantUser().getUsername().equals(username)){
            assessment = oneAOne.getApplicantUserAssessment();
            oneAOne.getApplicantUserAssessment().setConcepts(concepts);
            oneAOne.getApplicantUserAssessment().setUpdateDate(new Date());

        }else if(oneAOne.getGuestUserAssessment() != null
                && oneAOne.getGuestUser().getUsername().equals(username)){
            assessment = oneAOne.getGuestUserAssessment();
            oneAOne.getGuestUserAssessment().setConcepts(concepts);
            oneAOne.getGuestUserAssessment().setUpdateDate(new Date());

        }else {
            throw new DataValidationException(Arrays.asList(
                    messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));
        }

        oneAOneService.save(oneAOne);

        return assessment;
    }

}
