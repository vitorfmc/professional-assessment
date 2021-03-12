package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentTemplate {

    @Id
    private String id;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "type is mandatory")
    private List<Competence> competences;

    public AssessmentTemplate(@NotNull(message = "name is mandatory") String name, @NotNull(message = "type is mandatory") List<Competence> competences) {
        this.name = name;
        this.competences = competences;
    }

    public List<CompetenceTypeEnum> getDistinctCompetenceTypes(){
        return competences.stream()
                .map(x -> x.getType()).collect(Collectors.toList())
                .stream()
                .distinct().collect(Collectors.toList());
    }
}
