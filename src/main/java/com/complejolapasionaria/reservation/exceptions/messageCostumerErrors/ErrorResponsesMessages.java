package com.complejolapasionaria.reservation.exceptions.messageCostumerErrors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ErrorResponsesMessages {
    private HttpStatus httpStatus;
    private String message;
}
