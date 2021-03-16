package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competence {

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "description is mandatory")
    private String description;

    @NotNull(message = "type is mandatory")
    private CompetenceTypeEnum type;

}
