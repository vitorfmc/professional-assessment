package com.rovitapps.professionalassessment.model;

public enum CompetenceTypeEnum {

    TEHCNICAL("Técnica"),
    BEHAVIORAL("Comportamental");

    private String description;

    CompetenceTypeEnum(String description){
        this.description = description;
    }

    public String getDescription() { return this.description; }

}
