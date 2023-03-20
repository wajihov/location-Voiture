package fr.gopartner.locationvoiture.domain.reservation;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.domain.car.CarMapper;
import fr.gopartner.locationvoiture.domain.customer.CustomerMapper;
import fr.gopartner.locationvoiture.dto.CarDto;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;

    public ReservationService(ReservationMapper reservationMapper, ReservationRepository reservationRepository, CustomerMapper customerMapper, CarMapper carMapper) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.customerMapper = customerMapper;
        this.carMapper = carMapper;
    }

    public ReservationDto createReservation(ReservationDto reservationDto, CustomerDto customerDto, CarDto carDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        reservation.setCustomer(customerMapper.toEntity(customerDto));
        reservation.setCar(carMapper.toEntity(carDto));
        reservation = reservationRepository.save(reservation);
        log.info("The Reservation is added successfully {}", reservation.getId());
        return reservationMapper.toDto(reservation);
    }

    private Reservation rechercheReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() ->
                new CarReservationCustomerException(Codes.ERR_RESERVATION_NOT_FOUND));
    }

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = rechercheReservationById(id);
        log.info("The Reservation searched is {}", reservation.getId());
        return reservationMapper.toDto(reservation);
    }

    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        log.info("List {} Reservations", reservations.size());
        return reservationMapper.reservationDtoList(reservations);
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        reservation.setId(id);
        reservation = reservationRepository.save(reservation);
        log.info("The Reservation with id {} has been successfully modified", reservation.getId());
        return reservationMapper.toDto(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = rechercheReservationById(id);
        reservationRepository.delete(reservation);
        log.info("The deletion of the Reservation name {} is successful", reservation.getId());
    }
}
