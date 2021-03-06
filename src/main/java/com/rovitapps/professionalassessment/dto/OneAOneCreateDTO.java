package com.rovitapps.professionalassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneAOneCreateDTO {

    @NotNull(message = "creatorUsername is mandatory")
    String creatorUsername;

    @NotNull(message = "evaluatedUsername is mandatory")
    String evaluatedUsername;

    @NotNull(message = "assessmentTemplateId is mandatory")
    String assessmentTemplateId;

    @NotNull(message = "oneAOneDate is mandatory")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$",
            message="oneAOneDate format should be dd/mm/yyyy")
    String oneAOneDate;
}
