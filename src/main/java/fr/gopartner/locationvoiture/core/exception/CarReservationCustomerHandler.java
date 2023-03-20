package fr.gopartner.locationvoiture.core.exception;

import fr.gopartner.locationvoiture.core.rest.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CarReservationCustomerHandler {

    @ExceptionHandler(CarReservationCustomerException.class)
    public ResponseEntity<ServerResponse> handleEPException(CarReservationCustomerException ex) {
        ServerResponse serverResponse = ServerResponse
                .builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getCodes().getMessage())
                .build();
        return new ResponseEntity<>(serverResponse, ex.getCodes().getHttpStatus());
    }
}
