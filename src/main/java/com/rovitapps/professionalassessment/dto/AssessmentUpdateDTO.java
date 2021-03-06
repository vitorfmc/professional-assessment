package com.rovitapps.professionalassessment.dto;

import com.rovitapps.professionalassessment.model.Concept;
import com.rovitapps.professionalassessment.model.OneAOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentUpdateDTO {

    List<Concept> concepts;

}
