package fr.gopartner.locationvoiture.core.exception;

import fr.gopartner.locationvoiture.core.rest.Codes;
import lombok.Getter;

@Getter
public class CarReservationCustomerException extends RuntimeException {

    private Codes codes;

    public CarReservationCustomerException(Codes codes) {
        super(codes.getMessage());
        this.codes = codes;
    }

}
