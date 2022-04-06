package org.darksavant.test.bank.error;

public class NotEnoughPermissionsException extends RuntimeException {
    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}

