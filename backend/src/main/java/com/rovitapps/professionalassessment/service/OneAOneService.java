package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.model.*;
import com.rovitapps.professionalassessment.repository.OneAOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OneAOneService {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private GradeTypeService gradeTypeService;

    @Autowired
    private AssessmentTemplateService assessmentTemplateService;

    @Autowired
    private OneAOneRepository repository;

    public OneAOne create(String creatorUsername, String evaluatedUsername, String assessmentTemplateName, Date oneAOneDate)
            throws DataValidationException {

        var creator = userService.findByUserName(creatorUsername);
        var evaluated = userService.findByUserName(evaluatedUsername);
        var assessmentTemplate = assessmentTemplateService.findByName(assessmentTemplateName);

        List<Concept> concepts = new ArrayList<>();
        if(assessmentTemplate != null){
            for(CompetenceTypeEnum type: assessmentTemplate.getDistinctCompetenceTypes()){
                concepts.addAll(getConcepts(assessmentTemplate, type));
            }
        }

        validate(creator, evaluated, assessmentTemplate, concepts, oneAOneDate);

        var creatorAssessment = new Assessment(concepts, creator, AssessmentStatusEnum.TO_DO);
        var evaluatedAssessment = evaluated != null ? new Assessment(concepts, evaluated, AssessmentStatusEnum.TO_DO) : null;

        var oneAOne = new OneAOne(oneAOneDate, creatorAssessment, evaluatedAssessment);

        var result = repository.save(oneAOne);

        return result;
    }

    private List<Concept> getConcepts(AssessmentTemplate assessmentTemplate, CompetenceTypeEnum type){

        GradeType gradeType = gradeTypeService.findByType(type);

        if(gradeType == null) return new ArrayList<>();

        return assessmentTemplate.getCompetences()
                .stream().filter( x -> x.getType() == type)
                .map( x -> new Concept(x, gradeType.getType(), null))
                .collect(Collectors.toList());
    }

    private void validate(User creator, User evaluated, AssessmentTemplate assessmentTemplate,
                          List<Concept> concepts, Date oneAOneDate) throws DataValidationException {

        List<String> messages = new ArrayList<>();

        if(creator == null)
            messages.add(messageSource.getMessage("oneaone.error.creator.null", null, Locale.getDefault()));

        if(assessmentTemplate == null)
            messages.add(messageSource.getMessage("oneaone.error.assessmentTemplate.null", null, Locale.getDefault()));

        if(concepts == null)
            messages.add(messageSource.getMessage("oneaone.error.concepts.null", null, Locale.getDefault()));

        if(oneAOneDate == null){
            messages.add(messageSource.getMessage("oneaone.error.date.null", null, Locale.getDefault()));
        }else if(oneAOneDate.before(new Date())){
            messages.add(messageSource.getMessage("oneaone.error.date.past", null, Locale.getDefault()));
        }

        if(evaluated != null && creator != null
                && repository.findDuplicate(creator.getUsername(), evaluated.getUsername(), oneAOneDate) != null){
            messages.add(messageSource.getMessage("oneaone.error.duplicated", null, Locale.getDefault()));
        }

        if(!messages.isEmpty()){
            throw new DataValidationException(messages);
        }

    }

    public List<OneAOne> findAll() {
        return repository.findAll();
    }

    public void delete(String id) throws DataValidationException {

        List<String> messages = new ArrayList<>();

        if(id == null || id.isEmpty())
            messages.add(messageSource.getMessage("oneaone.error.id.mandatory", null, Locale.getDefault()));

        try {
            repository.deleteById(id);
        }catch (Exception e){
            messages.add(messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault()));
        }

        if(!messages.isEmpty()){
            throw new DataValidationException(messages);
        }
    }
}
