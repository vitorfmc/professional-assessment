package com.rovitapps.professionalassessment.model;

public enum AssessmentStatusEnum {

    TO_DO("A Fazer"),
    DOING("Fazendo"),
    DONE("Feito");

    private String description;

    AssessmentStatusEnum(String description){
        this.description = description;
    }

    public String getDescription() { return this.description; }

}
