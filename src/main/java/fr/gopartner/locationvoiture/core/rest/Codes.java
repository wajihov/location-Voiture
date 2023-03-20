package fr.gopartner.locationvoiture.core.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Codes {

    ERR_CAR_NOT_FOUND("CAR NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_CARS_NOT_FOUND("CARS NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_CARS_NOT_VAlID("CARS NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_CARS_NOT_EXIST("CARS NOT EXIST", HttpStatus.NOT_EXTENDED),

    ERR_CUSTOMER_NOT_FOUND("CUSTOMER NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_CUSTOMERS_NOT_FOUND("CUSTOMERS NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_CUSTOMERS_NOT_VAlID("CUSTOMERS NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_CUSTOMERS_NOT_EXIST("CUSTOMERS NOT EXIST", HttpStatus.NOT_EXTENDED),

    ERR_RESERVATION_NOT_FOUND("RESERVATION NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_RESERVATIONS_NOT_FOUND("RESERVATIONS NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_RESERVATIONS_NOT_VAlID("RESERVATIONS NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_RESERVATIONS_NOT_EXIST("RESERVATIONS NOT EXIST", HttpStatus.NOT_EXTENDED);

    private String message;
    private HttpStatus httpStatus;

    Codes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
