package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "value is mandatory")
    private int value;

}
