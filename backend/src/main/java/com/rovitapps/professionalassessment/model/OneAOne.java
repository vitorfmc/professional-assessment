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

    @NotNull(message = "creatorAssessments is mandatory")
    private Assessment creatorAssessments;

    @NotNull(message = "evaluatedAssessments is mandatory")
    private Assessment evaluatedAssessments;

    private boolean enabled = true;

    public OneAOne(Date date, Assessment creatorAssessments, Assessment evaluatedAssessments) {
        this.date = date;
        this.creatorAssessments = creatorAssessments;
        this.evaluatedAssessments = evaluatedAssessments;
    }

    public String getStatus(){
        if(enabled = false){
            return "Encerrado";
        }else if(!creatorAssessments.getStatus().equals(AssessmentStatusEnum.DONE)
                || (evaluatedAssessments != null && !creatorAssessments.getStatus().equals(AssessmentStatusEnum.DONE))){
            return "Formulários sendo preenchidos";
        }else{
            return "Formulários preenchidos";
        }
    }
}
