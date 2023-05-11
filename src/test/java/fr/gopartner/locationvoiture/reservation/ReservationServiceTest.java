package fr.gopartner.locationvoiture.reservation;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.car.CarMapper;
import fr.gopartner.locationvoiture.domain.car.CarService;
import fr.gopartner.locationvoiture.domain.customer.Customer;
import fr.gopartner.locationvoiture.domain.customer.CustomerMapper;
import fr.gopartner.locationvoiture.domain.customer.CustomerService;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import fr.gopartner.locationvoiture.domain.reservation.ReservationMapper;
import fr.gopartner.locationvoiture.domain.reservation.ReservationRepository;
import fr.gopartner.locationvoiture.domain.reservation.ReservationService;
import fr.gopartner.locationvoiture.dto.CarDto;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationServiceTest {

    @MockBean
    private ReservationRepository reservationRepository;
    @MockBean
    private ReservationMapper reservationMapper;
    @MockBean
    private CarService carService;
    @MockBean
    private CarMapper carMapper;
    @MockBean
    private CustomerMapper customerMapper;
    @MockBean
    private CustomerService customerService;

    @Autowired
    private ReservationService reservationService;

    @Captor
    private ArgumentCaptor<Reservation> reservationArgumentCaptor;

    @Test
    void GIVEN_ReservationDto_WHEN_createReservation_THEN_should_save_into_database() {
        // GIVEN
        CarDto carDto = CarDto.builder()
                .id(1L)
                .mark("hyundai i10")
                .reference("ABC123")
                .numberOfDoors(4)
                .numberOfPlace(5)
                .numberOfHorses(100)
                .gearbox("Automatic")
                .price(50000L)
                .color("White")
                .image("car_image.jpg")
                .numberOfAvailableCars(3)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .privilege("admin")
                .build();

        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .careAndSupport("Full care and support")
                .discount("10% discount")
                .startDate(LocalDate.of(2023, 5, 1))
                .endDate(LocalDate.of(2023, 5, 5))
                .status("Active")
                .dateReservation(LocalDate.now())
                .carId(carDto.getId())
                .customerId(customerDto.getId())
                .build();

        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setCareAndSupport(reservationDto.getCareAndSupport());
        reservation.setDiscount(reservationDto.getDiscount());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setDateReservation(reservationDto.getDateReservation());
        reservation.setCar(carMapper.toEntity(carDto));
        reservation.setCustomer(customerMapper.toEntity(customerDto));

        Mockito.when(carService.searchCarById(reservationDto.getCarId())).thenReturn(carDto);
        Mockito.when(customerService.searchCustomerById(reservationDto.getCustomerId())).thenReturn(customerDto);
        Mockito.when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(reservationMapper.toDto(reservation)).thenReturn(reservationDto);
        // WHEN
        ReservationDto reservationDtoCreated = reservationService.createReservation(reservationDto);
        // THEN
        Mockito.verify(reservationRepository).save(reservationArgumentCaptor.capture());
        Reservation reservationSaved = reservationArgumentCaptor.getValue();

        assertEquals(reservationSaved.getId(), reservationDtoCreated.getId());
        assertEquals(reservationSaved.getCareAndSupport(), reservationDtoCreated.getCareAndSupport());
        assertEquals(reservationSaved.getDiscount(), reservationDtoCreated.getDiscount());
        assertEquals(reservationSaved.getStartDate(), reservationDtoCreated.getStartDate());
        assertEquals(reservationSaved.getEndDate(), reservationDtoCreated.getEndDate());
        assertEquals(reservationSaved.getStatus(), reservationDtoCreated.getStatus());
        assertEquals(reservationSaved.getDateReservation(), reservationDtoCreated.getDateReservation());
    }

    @Test
    void GIVEN_ReservationIdAndReservationDto_WHEN_updateReservation_THEN_should_update_in_database() {
        // GIVEN
        Long reservationId = 1L;
        CarDto carDto = CarDto.builder()
                .id(1L)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .build();

        ReservationDto reservationDto = ReservationDto.builder()
                .id(reservationId)
                .careAndSupport("Full care and support")
                .discount("10% discount")
                .startDate(LocalDate.of(2023, 5, 1))
                .endDate(LocalDate.of(2023, 5, 5))
                .status("Active")
                .dateReservation(LocalDate.now())
                .carId(carDto.getId())
                .customerId(customerDto.getId())
                .build();

        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setCareAndSupport(reservationDto.getCareAndSupport());
        reservation.setDiscount(reservationDto.getDiscount());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setDateReservation(reservationDto.getDateReservation());
        reservation.setCar(carMapper.toEntity(carDto));
        reservation.setCustomer(customerMapper.toEntity(customerDto));

        Mockito.when(carService.searchCarById(reservationDto.getCarId())).thenReturn(carDto);
        Mockito.when(customerService.searchCustomerById(reservationDto.getCustomerId())).thenReturn(customerDto);
        Mockito.when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(reservationMapper.toDto(reservation)).thenReturn(reservationDto);
        // WHEN
        ReservationDto updatedReservationDto = reservationService.updateReservation(reservationId, reservationDto);
        // THEN
        Mockito.verify(reservationRepository).save(reservationArgumentCaptor.capture());
        Reservation reservationSaved = reservationArgumentCaptor.getValue();

        assertEquals(reservationSaved.getId(), updatedReservationDto.getId());
        assertEquals(reservationSaved.getCareAndSupport(), updatedReservationDto.getCareAndSupport());
        assertEquals(reservationSaved.getDiscount(), updatedReservationDto.getDiscount());
        assertEquals(reservationSaved.getStartDate(), updatedReservationDto.getStartDate());
        assertEquals(reservationSaved.getEndDate(), updatedReservationDto.getEndDate());
        assertEquals(reservationSaved.getStatus(), updatedReservationDto.getStatus());
        assertEquals(reservationSaved.getDateReservation(), updatedReservationDto.getDateReservation());
    }

    @Test
    void GIVEN_ReservationId_WHEN_getReservationById_THEN_should_return_ReservationDto() {
        // GIVEN
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setCareAndSupport("Full care and support");
        reservation.setDiscount("10% discount");
        reservation.setStartDate(LocalDate.of(2023, 5, 1));
        reservation.setEndDate(LocalDate.of(2023, 5, 5));
        reservation.setStatus("Active");
        reservation.setDateReservation(LocalDate.now());

        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationMapper.toDto(reservation)).thenReturn(
                ReservationDto.builder()
                        .id(reservation.getId())
                        .careAndSupport(reservation.getCareAndSupport())
                        .discount(reservation.getDiscount())
                        .startDate(reservation.getStartDate())
                        .endDate(reservation.getEndDate())
                        .status(reservation.getStatus())
                        .dateReservation(reservation.getDateReservation())
                        .carId(1L)
                        .customerId(1L)
                        .build()
        );
        // WHEN
        ReservationDto foundReservationDto = reservationService.getReservationById(reservationId);
        // THEN
        assertNotNull(foundReservationDto);
        assertEquals(reservation.getId(), foundReservationDto.getId());
        assertEquals(reservation.getCareAndSupport(), foundReservationDto.getCareAndSupport());
        assertEquals(reservation.getDiscount(), foundReservationDto.getDiscount());
        assertEquals(reservation.getStartDate(), foundReservationDto.getStartDate());
        assertEquals(reservation.getEndDate(), foundReservationDto.getEndDate());
        assertEquals(reservation.getStatus(), foundReservationDto.getStatus());
        assertEquals(reservation.getDateReservation(), foundReservationDto.getDateReservation());
    }

    @Test
    void GIVEN_ReservationDto_saved_WHEN_getAllReservations_THEN_should_return_listOfReservationDto() {
        // GIVEN
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setCareAndSupport("Care 1");
        reservation1.setDiscount("Discount 1");
        reservation1.setStartDate(LocalDate.of(2023, 5, 1));
        reservation1.setEndDate(LocalDate.of(2023, 5, 5));
        reservation1.setStatus("Status 1");
        reservation1.setDateReservation(LocalDate.now());
        Car car1 = new Car();
        car1.setId(1L);
        reservation1.setCar(car1);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        reservation1.setCustomer(customer1);
        reservations.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCareAndSupport("Care 2");
        reservation2.setDiscount("Discount 2");
        reservation2.setStartDate(LocalDate.of(2023, 6, 1));
        reservation2.setEndDate(LocalDate.of(2023, 6, 5));
        reservation2.setStatus("Status 2");
        reservation2.setDateReservation(LocalDate.now());
        Car car2 = new Car();
        car2.setId(2L);
        reservation2.setCar(car2);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        reservation2.setCustomer(customer2);
        reservations.add(reservation2);

        Mockito.when(reservationRepository.findAll()).thenReturn(reservations);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .id(reservation1.getId())
                .careAndSupport(reservation1.getCareAndSupport())
                .discount(reservation1.getDiscount())
                .startDate(reservation1.getStartDate())
                .endDate(reservation1.getEndDate())
                .status(reservation1.getStatus())
                .dateReservation(reservation1.getDateReservation())
                .customerId(reservation1.getCustomer().getId())
                .carId(reservation1.getCar().getId())
                .build();

        ReservationDto reservationDto2 = ReservationDto.builder()
                .id(reservation2.getId())
                .careAndSupport(reservation2.getCareAndSupport())
                .discount(reservation2.getDiscount())
                .startDate(reservation2.getStartDate())
                .endDate(reservation2.getEndDate())
                .status(reservation2.getStatus())
                .dateReservation(reservation2.getDateReservation())
                .carId(reservation2.getCar().getId())
                .customerId(reservation2.getCustomer().getId())
                .build();

        List<ReservationDto> reservationDtoList = new ArrayList<>() {
            {
                add(reservationDto1);
                add(reservationDto2);
            }
        };

        Mockito.when(reservationRepository.findAll()).thenReturn(reservations);
        Mockito.when(reservationMapper.reservationDtoList(reservations)).thenReturn(reservationDtoList);
        // WHEN
        List<ReservationDto> result = reservationService.getAllReservations();

        // Vérifier les assertions
        assertEquals(2, result.size());
        assertEquals(result.size(), reservationDtoList.size());
        for (int i = 0; i < reservationDtoList.size(); i++) {
            ReservationDto reservationDto = reservationDtoList.get(i);
            ReservationDto reservationResult = result.get(i);
            assertEquals(reservationDto.getId(), reservationResult.getId());
            assertEquals(reservationDto.getCareAndSupport(), reservationResult.getCareAndSupport());
            assertEquals(reservationDto.getDiscount(), reservationResult.getDiscount());
            assertEquals(reservationDto.getStartDate(), reservationResult.getStartDate());
            assertEquals(reservationDto.getEndDate(), reservationResult.getEndDate());
            assertEquals(reservationDto.getStatus(), reservationResult.getStatus());
            assertEquals(reservationDto.getDateReservation(), reservationResult.getDateReservation());
            assertEquals(reservationDto.getCarId(), reservationResult.getCarId());
            assertEquals(reservationDto.getCustomerId(), reservationResult.getCustomerId());
        }
    }

    @Test
    void GIVEN_ReservationId_WHEN_delete_reservation_THEN_reservationShouldBeDeleted() {
        // GIVEN
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setCareAndSupport("Care 2");
        reservation.setDiscount("Discount 2");
        reservation.setStartDate(LocalDate.of(2023, 6, 1));
        reservation.setEndDate(LocalDate.of(2023, 6, 5));
        reservation.setStatus("Status 2");
        reservation.setDateReservation(LocalDate.now());
        Car car = new Car();
        car.setId(2L);
        reservation.setCar(car);
        Customer customer = new Customer();
        customer.setId(2L);
        reservation.setCustomer(customer);

        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        // WHEN
        reservationService.deleteReservation(reservationId);
        // THEN
        Mockito.verify(reservationRepository).delete(reservationArgumentCaptor.capture());
        Reservation reservationDeleted = reservationArgumentCaptor.getValue();
        assertEquals(reservationDeleted.getId(), reservation.getId());
        assertEquals(reservationDeleted.getCareAndSupport(), reservation.getCareAndSupport());
        assertEquals(reservationDeleted.getDiscount(), reservation.getDiscount());
        assertEquals(reservationDeleted.getStartDate(), reservation.getStartDate());
        assertEquals(reservationDeleted.getEndDate(), reservation.getEndDate());
        assertEquals(reservationDeleted.getStatus(), reservation.getStatus());
        assertEquals(reservationDeleted.getDateReservation(), reservation.getDateReservation());
        assertEquals(reservationDeleted.getCustomer(), reservation.getCustomer());
        assertEquals(reservationDeleted.getCar(), reservation.getCar());
        Mockito.verify(reservationRepository, Mockito.times(1)).delete(reservation);
    }

    @Test
    void GIVEN_reservation_WHEN_deletereservation_THEN_should_return_RuntimeException() {
        //GIVEN & WHEN
        Reservation reservation = new Reservation();
        Mockito.when(reservationRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(reservation));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            reservationService.deleteReservation(null);
        });
        assertEquals("RESERVATION NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_reservationId_WHEN_findReservationById_THEN_should_return_RuntimeException() {
        //GIVEN & WHEN
        Reservation reservation = new Reservation();
        Mockito.when(reservationRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(reservation));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            reservationService.getReservationById(null);
        });
        assertEquals("RESERVATION NOT FOUND", e.getMessage());
    }
}
