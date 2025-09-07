package com.aim.fictionalpubliclibrary.dtos;

import lombok.Data;

/**
 * Data Transfer Object for error responses.
 */
@Data
public class ErrorDTO {
    private String message;
    private String details;
}
