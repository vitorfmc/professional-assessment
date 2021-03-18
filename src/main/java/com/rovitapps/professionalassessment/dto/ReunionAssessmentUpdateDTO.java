package com.rovitapps.professionalassessment.dto;

import com.rovitapps.professionalassessment.model.Concept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionAssessmentUpdateDTO {

    private String assessmentId;
    @NotNull(message = "oneAOneDate is mandatory")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$",
            message="oneAOneDate format should be dd/mm/yyyy")
    String updateDate;
    private List<Concept> concepts;

}
