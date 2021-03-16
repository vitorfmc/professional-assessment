package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "username is mandatory")
    private String username;

    @NotNull(message = "email is mandatory")
    private String email;

    @NotNull(message = "password is mandatory")
    private String password;

    public User(@NotNull(message = "name is mandatory") String name, @NotNull(message = "username is mandatory") String username,
                @NotNull(message = "email is mandatory") String email, @NotNull(message = "password is mandatory") String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
