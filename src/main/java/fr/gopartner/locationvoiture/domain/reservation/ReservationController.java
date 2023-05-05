package fr.gopartner.locationvoiture.domain.reservation;

import fr.gopartner.locationvoiture.ReservationsApiDelegate;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController implements ReservationsApiDelegate {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public ResponseEntity<ReservationDto> createReservation(ReservationDto reservationDto) {
        ReservationDto dto = reservationService.createReservation(reservationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteReservationById(Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReservationDto> getReservationById(Long id) {
        ReservationDto reservationDto = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationDto);
    }

    @Override
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> reservationDtoList = reservationService.getAllReservations();
        return ResponseEntity.ok(reservationDtoList);
    }

    @Override
    public ResponseEntity<ReservationDto> updateReservationById(Long id, ReservationDto reservationDto) {
        ReservationDto dto = reservationService.updateReservation(id, reservationDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}