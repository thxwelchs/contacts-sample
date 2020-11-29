package com.osci.contacts.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<String> entityNotFoundExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>("요청한 데이터의 리소스를 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> entityDuplicatedExceptionHandler(SQLException e) {
        StringBuilder sb = new StringBuilder();
        switch (e.getErrorCode()) {
            case 1062:
                sb.append("요청한 데이터가 이미 존재합니다.");
                break;
            case 1452:
                sb.append("요청한 데이터가 잘못되었습니다. ").append("[").append(e.getMessage()).append("]");
        }
        return new ResponseEntity<>(sb.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> entityDuplicatedExceptionHandler(DataIntegrityViolationException e) throws SQLIntegrityConstraintViolationException {
        if(e.getMostSpecificCause().getClass() == SQLIntegrityConstraintViolationException.class) {
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e.getMostSpecificCause();
            return entityDuplicatedExceptionHandler(sqlException);
        }
        return new ResponseEntity<>("요청한 데이터가 이미 존재합니다.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StringBuilder> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder("입력된 데이터의 매개변수가 잘못되었습니다. 잘못된 항목은 다음과 같습니다.\n");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("항목명: ");
            builder.append(fieldError.getField());
            builder.append("은(는) ");
            builder.append(fieldError.getDefaultMessage());
            builder.append("\n");
        }
        return ResponseEntity.badRequest().body(builder);
    }
}