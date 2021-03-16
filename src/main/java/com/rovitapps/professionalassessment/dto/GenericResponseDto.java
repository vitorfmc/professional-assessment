package com.rovitapps.professionalassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.rovitapps.professionalassessment.util.Utils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDto {

    private String requestId;
    private int status;
    private List<String> messages;
    private Object data;

    public GenericResponseDto(int status, List<String> messages, Object data, String requestId) {
        this.requestId = requestId;
        this.status = status;
        this.messages = messages;
        this.data = data;
    }

    public GenericResponseDto(int status, List<String> messages, Object data) {
        requestId = Utils.getUuid();
        this.status = status;
        this.messages = messages;
        this.data = data;
    }
}