package org.hta.exceptions;

public enum ErrorType {

    BUSINESS_ERROR("Business Error"),
    TECHNICAL_ERROR("Technical Error");

    String value;

    ErrorType(String value) {
        this.value = value;
    }

}
