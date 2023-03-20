package fr.gopartner.locationvoiture.domain.reservation;

import fr.gopartner.locationvoiture.ReservationsApiDelegate;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController implements ReservationsApiDelegate {

    @Override
    public ResponseEntity<ReservationDto> createReservation(ReservationDto reservationDto) {
        return ReservationsApiDelegate.super.createReservation(reservationDto);
    }

    @Override
    public ResponseEntity<Void> deleteReservationById(Long id) {
        return ReservationsApiDelegate.super.deleteReservationById(id);
    }

    @Override
    public ResponseEntity<ReservationDto> getReservationById(Long id) {
        return ReservationsApiDelegate.super.getReservationById(id);
    }

    @Override
    public ResponseEntity<List<ReservationDto>> getReservations() {
        return ReservationsApiDelegate.super.getReservations();
    }

    @Override
    public ResponseEntity<Void> updateReservationById(Long id, ReservationDto reservationDto) {
        return ReservationsApiDelegate.super.updateReservationById(id, reservationDto);
    }
}
