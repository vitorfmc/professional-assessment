package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.model.CompetenceTypeEnum;
import com.rovitapps.professionalassessment.repository.GradeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rovitapps.professionalassessment.model.GradeType;

@Service
public class GradeTypeService {

    @Autowired
    private GradeTypeRepository repository;

    public GradeType findByType(CompetenceTypeEnum type){
        return repository.findByType(type.name());
    }

}
