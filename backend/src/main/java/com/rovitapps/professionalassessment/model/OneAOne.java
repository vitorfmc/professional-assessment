package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneAOne {

    @Id
    private String id;

    @JsonFormat(pattern="dd/MM/yyyy")
    @NotNull(message = "date is mandatory")
    private Date date;

    private List<String> actions = new ArrayList<>();

    private List<String> comments = new ArrayList<>();

    @NotNull(message = "creatorAssessments is mandatory")
    private Assessment creatorAssessments;

    @NotNull(message = "evaluatedAssessments is mandatory")
    private Assessment evaluatedAssessments;


    public OneAOne(Date date, Assessment creatorAssessments, Assessment evaluatedAssessments) {
        this.date = date;
        this.creatorAssessments = creatorAssessments;
        this.evaluatedAssessments = evaluatedAssessments;
    }
}
