package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reunion {

    @Id
    private String id;

    @JsonFormat(pattern="dd/MM/yyyy")
    @NotNull(message = "date is mandatory")
    private Date date;

    private String actions;

    private String comments;

    private Assessment applicantUserAssessment;

    private Assessment guestUserAssessment;

    private boolean enabled = true;

    public Reunion(Date date, Assessment applicantUserAssessment, Assessment guestUserAssessment) {
        this.date = date;
        this.applicantUserAssessment = applicantUserAssessment;
        this.guestUserAssessment = guestUserAssessment;
    }

    public String getStatus(){
        if(enabled = false){
            return "Encerrado";
        }else if((applicantUserAssessment != null && !applicantUserAssessment.getStatus().equals(AssessmentStatusEnum.DONE))
                || (guestUserAssessment != null && !guestUserAssessment.getStatus().equals(AssessmentStatusEnum.DONE))){
            return "Formulários sendo preenchidos";
        }else{
            return "Formulários preenchidos";
        }
    }

}
