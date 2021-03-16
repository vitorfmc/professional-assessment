package com.rovitapps.professionalassessment.exception;

import java.util.List;

public class DataValidationException extends Exception {

    private List<String> messages;

    public DataValidationException(List<String> messages){
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
