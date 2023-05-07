package fr.gopartner.locationvoiture.domain.reservation;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.core.utils.CollectionUtils;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationMapper {

    public Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            throw new CarReservationCustomerException(Codes.ERR_RESERVATION_NOT_FOUND);
        }
        return Reservation.builder()
                .id(reservationDto.getId())
                .careAndSupport(reservationDto.getCareAndSupport())
                .discount(reservationDto.getDiscount())
                .intakeOf(reservationDto.getIntakeOf())
                .socketTo(reservationDto.getSocketTo())
                .restitutionOf(reservationDto.getRestitutionOf())
                .restitutionTo(reservationDto.getRestitutionTo())
                .status(reservationDto.getStatus())
                .dateReservation(reservationDto.getDateReservation())
                .build();
    }

    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            throw new CarReservationCustomerException(Codes.ERR_RESERVATION_NOT_FOUND);
        }
        return ReservationDto.builder()
                .id(reservation.getId())
                .careAndSupport(reservation.getCareAndSupport())
                .discount(reservation.getDiscount())
                .intakeOf(reservation.getIntakeOf())
                .socketTo(reservation.getSocketTo())
                .restitutionOf(reservation.getRestitutionOf())
                .restitutionTo(reservation.getRestitutionTo())
                .status(reservation.getStatus())
                .dateReservation(reservation.getDateReservation())
                .carId(reservation.getCar().getId())
                .customerId(reservation.getCustomer().getId())
                .build();
    }

    public List<ReservationDto> reservationDtoList(List<Reservation> reservations) {
        if (CollectionUtils.isNullOrEmpty(reservations)) {
            throw new CarReservationCustomerException(Codes.ERR_RESERVATIONS_NOT_FOUND);
        }
        return reservations.stream().map(this::toDto).collect(Collectors.toList());
    }
}
