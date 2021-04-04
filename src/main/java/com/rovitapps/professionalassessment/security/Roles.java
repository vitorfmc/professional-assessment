package com.rovitapps.professionalassessment.security;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Roles {
    ROLE_USER,
    ONEAONE_LIST,
    ONEAONE_FIND_ONE,
    ONEAONE_CREATE,
    ONEAONE_UPDATE,
    ONEAONE_DELETE,
    USER_LIST,
    ASSESSMENT_TEMPLATE_LIST,
    ASSESSMENT_LIST ,
    ASSESSMENT_FIND_ONE;

    public static Stream<Roles> stream() {
        return Arrays.stream(Roles.values());
    }
}
