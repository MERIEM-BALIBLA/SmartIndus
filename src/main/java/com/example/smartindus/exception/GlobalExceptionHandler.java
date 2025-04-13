//package com.example.smartindus.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getCode(),
//                ex.getMessage(),
//                request.getDescription(false).replace("uri=", "")
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                "VALIDATION_ERROR",
//                ex.getMessage(),
//                request.getDescription(false).replace("uri=", "")
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                "INTERNAL_ERROR",
//                ex.getMessage(),
//                request.getDescription(false).replace("uri=", "")
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}