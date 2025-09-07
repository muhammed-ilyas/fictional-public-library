package com.aim.fictionalpubliclibrary.exceptions;

import com.aim.fictionalpubliclibrary.dtos.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a 404 response.
     * @param ex ResourceNotFoundException
     * @param request web request
     * @return ResponseEntity with ErrorDTO and 404 status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(404).body(errorDTO);
    }

    /**
     * Handles generic exceptions and returns a 500 response.
     * @param ex Exception
     * @param request web request
     * @return ResponseEntity with ErrorDTO and 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(
            Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(500).body(errorDTO);
    }

    /**
     * Handles InvalidInputException and returns a 400 response.
     * @param ex InvalidInputException
     * @param request web request
     * @return ResponseEntity with ErrorDTO and 400 status
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorDTO> handleInvalidInputException(
            InvalidInputException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDetails(request.getDescription(false));
        return ResponseEntity.status(400).body(errorDTO);
    }

}
