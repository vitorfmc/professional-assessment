package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date updateDate;

    private List<Concept> concepts;

    @NotNull(message = "owner is mandatory")
    private User owner;

    @NotNull(message = "status is mandatory")
    private AssessmentStatusEnum status;

    public Assessment(List<Concept> concepts, User owner, AssessmentStatusEnum status) {
        this.concepts = concepts;
        this.owner = owner;
        this.status = status;
    }
}
