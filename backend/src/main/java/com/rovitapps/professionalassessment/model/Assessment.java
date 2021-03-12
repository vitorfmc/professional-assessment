package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date updateDate;

    private List<Concept> concepts;

    @NotNull(message = "owner is mandatory")
    private User owner;

    public Assessment(List<Concept> concepts, User owner) {
        this.concepts = concepts;
        this.owner = owner;
    }

    public AssessmentStatusEnum getStatus() {
        var done = concepts.stream().filter(x -> x.getGrade().getValue() > 0).collect(Collectors.toList());

        if(done.isEmpty())
            return AssessmentStatusEnum.TO_DO;
        else if(done.size() == concepts.size())
            return AssessmentStatusEnum.DONE;
        else
            return AssessmentStatusEnum.DOING;
    }
}
