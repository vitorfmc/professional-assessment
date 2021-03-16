package com.rovitapps.professionalassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneAOneListDto {

    String creatorUsername;
    String evaluatedUsername;
    String date;

}
