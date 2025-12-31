//package com.java25.java25.springboot4.oracle.config;
//
//import java.security.SignatureException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, IllegalArgumentException.class})
//    public ResponseEntity<String> handleMalformedToken(Exception e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or malformed token!");
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<String> handleExpiredToken(ExpiredJwtException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
//    }
//}
