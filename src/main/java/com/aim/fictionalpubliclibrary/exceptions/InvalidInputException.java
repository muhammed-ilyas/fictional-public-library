package com.aim.fictionalpubliclibrary.exceptions;

/**
 * Custom exception to be thrown when input validation fails.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
