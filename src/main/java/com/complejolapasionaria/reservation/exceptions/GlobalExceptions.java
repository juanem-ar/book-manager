package com.complejolapasionaria.reservation.exceptions;

import com.complejolapasionaria.reservation.exceptions.messageCostumerErrors.ErrorResponsesMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponsesMessages> processValidationError(MethodArgumentNotValidException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorsResponseMessage.setMessage(Arrays.toString(ex.getDetailMessageArguments()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsResponseMessage);
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponsesMessages> processValidationError(BadRequestException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsResponseMessage);
    }
}