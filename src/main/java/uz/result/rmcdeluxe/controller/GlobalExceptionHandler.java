package uz.result.rmcdeluxe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.IllegalPhotoTypeException;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalPhotoTypeException.class)
    public ResponseEntity<ApiResponse<?>> handlePhotoTypeException(IllegalPhotoTypeException e) {
        return ResponseEntity.status(400).body(new ApiResponse<>("Illegal photo: " + e.getMessage(), null));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(400).body(new ApiResponse<>(e.getMessage(), null));
    }

    @ExceptionHandler(LanguageNotSupported.class)
    public ResponseEntity<ApiResponse<?>> handleLanguageNotSupportedException(LanguageNotSupported e) {
        return ResponseEntity.status(400).body(new ApiResponse<>(e.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(400).body(new ApiResponse<>(e.getMessage(), null));
    }

}
