package com.rovitapps.professionalassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneAOne {

    @Id
    private String id;

    @NotNull(message = "applicantUser is mandatory")
    private User applicantUser;

    @NotNull(message = "guestUser is mandatory")
    private User guestUser;

    @DBRef
    List<Reunion> reunions;

    public OneAOne(User applicantUser, User guestUser) {
        this.applicantUser = applicantUser;
        this.guestUser = guestUser;
        reunions = new ArrayList<>();

    }
}
