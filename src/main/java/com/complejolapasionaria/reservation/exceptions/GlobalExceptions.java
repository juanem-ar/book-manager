package com.complejolapasionaria.reservation.exceptions;

import com.complejolapasionaria.reservation.exceptions.messageCostumerErrors.ErrorResponsesMessages;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
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

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponsesMessages> processValidationError(MethodArgumentTypeMismatchException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsResponseMessage);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponsesMessages> processAccessValidation(AccessDeniedException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.FORBIDDEN);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorsResponseMessage);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponsesMessages> jwtExpiredValidation(ExpiredJwtException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorsResponseMessage);
    }

    @ExceptionHandler({InvalidParameterException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponsesMessages> processParametersValidation(InvalidParameterException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorsResponseMessage);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponsesMessages> processUsernameValidation(UsernameNotFoundException ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorsResponseMessage.setMessage(ex.getMessage());
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

    @ExceptionHandler({ResourceNotFound.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponsesMessages> processValidationError(ResourceNotFound ex){
        ErrorResponsesMessages errorsResponseMessage = new ErrorResponsesMessages();
        errorsResponseMessage.setHttpStatus(HttpStatus.NOT_FOUND);
        errorsResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorsResponseMessage);
    }
}
