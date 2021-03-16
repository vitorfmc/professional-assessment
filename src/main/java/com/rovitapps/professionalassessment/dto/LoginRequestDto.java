package com.rovitapps.professionalassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull(message = "O atributo username é obrigatório")
    private String username;

    @NotNull(message = "O atributo password é obrigatório")
    private String password;

}