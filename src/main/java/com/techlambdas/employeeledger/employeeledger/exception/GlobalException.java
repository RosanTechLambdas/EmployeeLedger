package com.techlambdas.employeeledger.employeeledger.exception;


import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;



@ControllerAdvice
public class GlobalException {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e){
//        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
//        return AppResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST,errorResponse);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> alReadyExistsException(AlreadyExistsException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.NOT_FOUND, errorResponse);
    }

    @ExceptionHandler({BadCredentialsException.class, LockedException.class, DisabledException.class})
    public ResponseEntity<?> handleAuthException() {
        ErrorResponse errorResponse = new ErrorResponse("Invalid userName or password",HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

//    @ExceptionHandler(ServletException.class)
//    public ResponseEntity<?> handleServletException(ServletException e){
//        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
//        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
//    }
//    @ExceptionHandler(ServerException.class)
//    public ResponseEntity<?> handleServerException(ServerException e){
//        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
//        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
//    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> forbidden(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleInvalidSignature(SignatureException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(FileStorageException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultiPart(MultipartException ex){
        ErrorResponse errorResponse = new ErrorResponse("please upload the file",HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointException(NullPointerException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return AppResponse.errorResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

}