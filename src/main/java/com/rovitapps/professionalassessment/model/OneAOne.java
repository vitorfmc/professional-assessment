package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneAOne {

    @Id
    private String id;

    @JsonFormat(pattern="dd/MM/yyyy")
    @NotNull(message = "date is mandatory")
    private Date date;

    private String actions;

    private String comments;

    @NotNull(message = "applicantUser is mandatory")
    private User applicantUser;

    private Assessment applicantUserAssessment;

    @NotNull(message = "guestUser is mandatory")
    private User guestUser;

    private Assessment guestUserAssessment;

    private boolean enabled = true;

    public OneAOne(Date date, User applicantUser, Assessment applicantUserAssessment, User guestUser, Assessment guestUserAssessment) {
        this.date = date;
        this.applicantUser = applicantUser;
        this.applicantUserAssessment = applicantUserAssessment;
        this.guestUser = guestUser;
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
