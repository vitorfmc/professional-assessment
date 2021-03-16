package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeType {

    @Id
    private String id;

    private CompetenceTypeEnum type;

    private List<Grade> grades;

    public GradeType(CompetenceTypeEnum type, List<Grade> grades) {
        this.type = type;
        this.grades = grades;
    }
}
