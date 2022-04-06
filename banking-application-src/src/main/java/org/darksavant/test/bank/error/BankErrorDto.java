package org.darksavant.test.bank.error;

import lombok.Data;

import java.util.Date;

/**
 * Unified error DTO to return errors into API
 */
@Data
public class BankErrorDto {

    /**
     * HTTP response status
     */
    private int status;

    /**
     * Error message
     */
    private String message;

    /**
     * Error timestamp
     */
    private Date timestamp;

    public BankErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
