package com.aim.fictionalpubliclibrary.dtos;

import lombok.Data;

@Data
public class ErrorDTO {
    private String message;
    private String details;
}
