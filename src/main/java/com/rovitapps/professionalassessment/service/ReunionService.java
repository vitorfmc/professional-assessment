package com.rovitapps.professionalassessment.service;

import com.rovitapps.professionalassessment.exception.DataValidationException;
import com.rovitapps.professionalassessment.model.Concept;
import com.rovitapps.professionalassessment.model.Reunion;
import com.rovitapps.professionalassessment.repository.ReunionRepository;
import com.rovitapps.professionalassessment.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReunionService {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private ReunionRepository repository;

    @Autowired
    private OneAOneService oneAOneService;

    public Reunion save(Reunion reunion) {
       return repository.save(reunion);
    }

    public Reunion findOne(String id, String username) throws DataValidationException {

        var op = oneAOneService.findByReunion(id);
        if(op == null)
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("reunion.error.entity.not.found", null, Locale.getDefault())));

        var resp = repository.findById(id);
        if(!resp.isPresent())
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("oneaone.error.entity.not.found", null, Locale.getDefault())));

        if(!op.getApplicantUser().getUsername().equals(username) && !op.getGuestUser().getUsername().equals(username)){
            throw new DataValidationException(Arrays.asList(messageSource.getMessage("generic.permission.error", null, Locale.getDefault())));
        }

        return resp.orElse(null);
    }

    public Reunion updateReunionInfo(String id, Date date, String comments, String actions, String username) throws DataValidationException {

        List<String> errors = validateDate(date);
        if(!errors.isEmpty()) throw new DataValidationException(Arrays.asList());

        var reunion = findOne(id, username);

        reunion.setComments(comments);
        reunion.setActions(actions);
        reunion.setDate(date);

        return save(reunion);
    }

    public Reunion updateAssessment(String reunionId, String assessmentId, Date updateDate, List<Concept> concepts, String username)
            throws DataValidationException {

        List<String> errors = validateDate(updateDate);
        if(!errors.isEmpty()) throw new DataValidationException(Arrays.asList());

        var reunion = findOne(reunionId, username);

        if(reunion.getApplicantUserAssessment().getId().equals(assessmentId)){
            reunion.getApplicantUserAssessment().setConcepts(concepts);
        }else if(reunion.getGuestUserAssessment().getId().equals(assessmentId)){
            reunion.getGuestUserAssessment().setConcepts(concepts);
        }

        return save(reunion);
    }

    public Reunion close(String id, String username) throws DataValidationException {
        var reunion = findOne(id, username);
        reunion.setEnabled(false);
        return save(reunion);
    }

    public void delete(String id, String username) throws DataValidationException {
        repository.delete(findOne(id, username));
    }


    private List<String> validateDate(Date date) {
        List<String> errors = new ArrayList<>();
        if(date == null){
            errors.add(messageSource.getMessage("oneaone.error.date.null", null, Locale.getDefault()));

        }else if(date.after(new Date()) && date.before(Utils.tomorrow())){
            errors.add(messageSource.getMessage("oneaone.error.date.null", null, Locale.getDefault()));
        }

        return errors;
    }

}
