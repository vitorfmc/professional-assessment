package com.rovitapps.professionalassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Assessment(List<Concept> concepts) {
        this.concepts = concepts;
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
