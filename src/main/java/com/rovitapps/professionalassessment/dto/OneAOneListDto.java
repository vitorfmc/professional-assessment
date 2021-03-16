package com.rovitapps.professionalassessment.dto;

import com.rovitapps.professionalassessment.model.OneAOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneAOneListDto {

    private List<OneAOne> content;
    private int firstPage;
    private int lastPage;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;

}
