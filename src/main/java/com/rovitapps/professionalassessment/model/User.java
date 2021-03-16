package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    private List<Role> roles;

    public User(String name, String username, String email, String password, List<Role> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getRolesFromProfiles() {

        String rolesFromProfiles = "";

        for (Role role : getRoles()) {
            rolesFromProfiles += role.getName() + ", ";
        }

        return rolesFromProfiles.substring(0, rolesFromProfiles.lastIndexOf(","));

    }
}
