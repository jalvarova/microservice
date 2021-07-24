package org.hta.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
