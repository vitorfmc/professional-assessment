package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.model.AssessmentTemplate;
import com.rovitapps.professionalassessment.repository.AssessmentTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentTemplateService {

    @Autowired
    private AssessmentTemplateRepository repository;

    public AssessmentTemplate findByName(String name){
        return repository.findByName(name);
    }

}
