package com.aim.fictionalpubliclibrary.exceptions;

import com.aim.fictionalpubliclibrary.dtos.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(
            Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(500).body(errorDTO);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorDTO> handleInvalidInputException(
            InvalidInputException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(400).body(errorDTO);
    }

}
