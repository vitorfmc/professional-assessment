package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.dto.OneAOneListDto;
import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.model.*;
import com.rovitapps.professionalassessment.repository.OneAOneRepository;
import com.rovitapps.professionalassessment.util.Utils;
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
    private ReunionService reunionService;

    @Autowired
    private AssessmentTemplateService assessmentTemplateService;

    @Autowired
    private OneAOneRepository repository;

    public OneAOne create(String creatorUsername, String evaluatedUsername, String assessmentTemplateId, Date oneAOneDate)
            throws DataValidationException {

        var applicantUser = userService.findByUserName(creatorUsername).orElseThrow();
        var guestUser = userService.findByUserName(evaluatedUsername).orElseThrow();
        var assessmentTemplate = assessmentTemplateService.findById(assessmentTemplateId);

        List<Concept> concepts = new ArrayList<>();
        if(assessmentTemplate != null){
            for(CompetenceTypeEnum type: assessmentTemplate.getDistinctCompetenceTypes()){
                concepts.addAll(getConcepts(assessmentTemplate, type));
            }
        }

        var oneAOne = applicantUser != null && guestUser != null?
                repository.findByApplicantUserAndGuestUser(applicantUser.getUsername(), applicantUser.getUsername()) : null;

        validate(applicantUser, guestUser, assessmentTemplate, concepts, oneAOneDate, oneAOne);

        var reunion = newReunion(concepts, oneAOneDate);

        oneAOne = oneAOne != null ? oneAOne : new OneAOne(applicantUser, guestUser);

        reunionService.save(reunion);
        oneAOne.getReunions().add(reunion);

        return repository.save(oneAOne);
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
                          List<Concept> concepts, Date oneAOneDate, OneAOne oneAOne) throws DataValidationException {

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
        }else if(oneAOneDate.after(new Date()) && oneAOneDate.before(Utils.tomorrow())){
            messages.add(messageSource.getMessage("oneaone.error.date.past", null, Locale.getDefault()));
        }

        if(oneAOne != null){
            var reunionsToday = oneAOne.getReunions().stream()
                    .filter( x -> x.getDate().after(new Date()) && x.getDate().before(Utils.tomorrow()))
                    .collect(Collectors.toList());

            if(applicantUser != null && guestUser != null && !reunionsToday.isEmpty()){
                messages.add(messageSource.getMessage("oneaone.error.duplicated", null, Locale.getDefault()));
            }
        }

        if(!messages.isEmpty()){
            throw new DataValidationException(messages);
        }

    }

    public OneAOneListDto findAllByUser(String username, int limit, int offset) {
        var resp = repository.findAllByUser(username);
        return new OneAOneListDto(resp, 0, 0, 0, resp.size(), resp.size(), 1);
    }

    public OneAOne find(String id, String username) throws DataValidationException {

        if(id == null)
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.id.mandatory", null, Locale.getDefault())));

        var op = repository.findById(id);

        if(op.isPresent() && !op.get().getApplicantUser().getUsername().equals(username) && !op.get().getGuestUser().getUsername().equals(username))
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("generic.permission.error", null, Locale.getDefault())));

        return op.orElse(null);
    }

    public OneAOne findByReunion(String id) {
        return repository.findByReunionId(id);
    }

    public OneAOne save(OneAOne oneAOne) {
        return repository.save(oneAOne);
    }

    private Reunion newReunion(List<Concept> concepts, Date oneAOneDate){
        var applicantUserAssessment = new Assessment(concepts);
        var guestUserAssessment = new Assessment(concepts);
        return new Reunion(oneAOneDate, applicantUserAssessment, guestUserAssessment);
    }
}
