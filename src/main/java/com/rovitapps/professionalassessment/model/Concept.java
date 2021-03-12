package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concept {

    @NotNull(message = "competence is mandatory")
    private Competence competence;

    @NotNull(message = "type is mandatory")
    private CompetenceTypeEnum type;

    @NotNull(message = "grade is mandatory")
    private Grade grade;

}
