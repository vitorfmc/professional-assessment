package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.dto.OneAOneListDto;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.model.*;
import com.rovitapps.professionalassessment.repository.OneAOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;
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

        var applicantUser = userService.findByUserName(creatorUsername);
        var guestUser = userService.findByUserName(evaluatedUsername);
        var assessmentTemplate = assessmentTemplateService.findByName(assessmentTemplateName);

        List<Concept> concepts = new ArrayList<>();
        if(assessmentTemplate != null){
            for(CompetenceTypeEnum type: assessmentTemplate.getDistinctCompetenceTypes()){
                concepts.addAll(getConcepts(assessmentTemplate, type));
            }
        }

        validate(applicantUser, guestUser, assessmentTemplate, concepts, oneAOneDate);

        var applicantUserAssessment = new Assessment(concepts);
        var guestUserAssessment = new Assessment(concepts);

        var oneAOne = new OneAOne(oneAOneDate, applicantUser, applicantUserAssessment, guestUser, guestUserAssessment);

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

    private void validate(User applicantUser, User guestUser, AssessmentTemplate assessmentTemplate,
                          List<Concept> concepts, Date oneAOneDate) throws DataValidationException {

        List<String> messages = new ArrayList<>();

        if(applicantUser == null)
            messages.add(messageSource.getMessage("oneaone.error.applicantUser.null", null, Locale.getDefault()));

        if(guestUser == null)
            messages.add(messageSource.getMessage("oneaone.error.guestUser.null", null, Locale.getDefault()));

        if(assessmentTemplate == null)
            messages.add(messageSource.getMessage("oneaone.error.assessmentTemplate.null", null, Locale.getDefault()));

        if(concepts == null)
            messages.add(messageSource.getMessage("oneaone.error.concepts.null", null, Locale.getDefault()));

        if(oneAOneDate == null){
            messages.add(messageSource.getMessage("oneaone.error.date.null", null, Locale.getDefault()));
        }else if(oneAOneDate.before(new Date())){
            messages.add(messageSource.getMessage("oneaone.error.date.past", null, Locale.getDefault()));
        }

        if(applicantUser != null && guestUser != null
                && !repository.findDuplicate(applicantUser.getUsername(), guestUser.getUsername(), oneAOneDate).isEmpty()){
            messages.add(messageSource.getMessage("oneaone.error.duplicated", null, Locale.getDefault()));
        }

        if(!messages.isEmpty()){
            throw new DataValidationException(messages);
        }

    }

    public OneAOneListDto findAllByUser(String username) {

        List<OneAOneListDto> response = new ArrayList<>();
        var resp = repository.findAllByApplicantUserOrGuestUser(username, username);

        return new OneAOneListDto();
    }

    public List<OneAOne> findAllByUsers(String creator, String evaluated) {

        if(evaluated == null)
            return null;

        var result = repository.findAllByApplicantUserAndGuestUserOrderByApplicantUser(creator, evaluated);

        return result;
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

    public OneAOne find(String id) throws DataValidationException {

        if(id == null)
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.id.mandatory", null, Locale.getDefault())));

        var op = repository.findById(id);

        return op.isPresent() ? op.get() : null;
    }

    public OneAOne update(String id, Date date, String comments, String actions) throws DataValidationException {

        var oneAOne = find(id);

        if(oneAOne == null)
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));

        if(date == null){
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.date.null", null, Locale.getDefault())));
        }else if(date.before(new Date())){
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.date.past", null, Locale.getDefault())));
        }

        oneAOne.setDate(date);
        oneAOne.setComments(comments);
        oneAOne.setActions(actions);

        oneAOne.setEnabled(false);

        return repository.save(oneAOne);
    }

    public OneAOne close(String id) throws DataValidationException {

        var oneAOne = find(id);

        if(oneAOne == null)
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));

        oneAOne.setEnabled(false);

        return repository.save(oneAOne);
    }

    public OneAOne save(OneAOne oneAOne) {
        return repository.save(oneAOne);
    }
}