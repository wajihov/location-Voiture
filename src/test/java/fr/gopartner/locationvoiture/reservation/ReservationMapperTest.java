package fr.gopartner.locationvoiture.reservation;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.customer.Customer;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import fr.gopartner.locationvoiture.domain.reservation.ReservationMapper;
import fr.gopartner.locationvoiture.dto.CarDto;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReservationMapperTest {

    @Autowired
    private ReservationMapper reservationMapper;

    @Test
    void GIVEN_Reservation_WHEN_toDto_THEN_should_return_ReservationDto() {
        //GIVEN
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCareAndSupport("Full service");
        reservation.setDiscount("10%");
        reservation.setStartDate(LocalDate.of(2023, 5, 15));
        reservation.setEndDate(LocalDate.of(2023, 5, 20));
        reservation.setStatus("Confirmed");
        reservation.setDateReservation(LocalDate.now());

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password");
        customer.setPrivilege("admin");

        Car car = new Car();
        car.setId(1L);
        car.setMark("hyundai i10");
        car.setReference("ABC123");
        car.setNumberOfDoors(4);
        car.setNumberOfPlace(5);
        car.setNumberOfHorses(100);
        car.setGearbox("Automatic");
        car.setPrice(50000L);
        car.setColor("White");
        car.setImage("car_image.jpg");
        car.setNumberOfAvailableCars(3);

        reservation.setCustomer(customer);
        reservation.setCar(car);
        // WHEN
        ReservationDto reservationDto = reservationMapper.toDto(reservation);
        // THEN
        assertEquals(reservationDto.getId(), reservation.getId());
        assertEquals(reservationDto.getCareAndSupport(), reservation.getCareAndSupport());
        assertEquals(reservationDto.getDiscount(), reservation.getDiscount());
        assertEquals(reservationDto.getStartDate(), reservation.getStartDate());
        assertEquals(reservationDto.getEndDate(), reservation.getEndDate());
        assertEquals(reservationDto.getStatus(), reservation.getStatus());
        assertEquals(reservationDto.getDateReservation(), reservation.getDateReservation());
        assertEquals(reservationDto.getCarId(), reservation.getCar().getId());
        assertEquals(reservationDto.getCustomerId(), reservation.getCustomer().getId());
    }

    @Test
    void GIVEN_ReservationDto_WHEN_toEntity_THEN_should_return_Reservation() {
        //GIVEN
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCareAndSupport("Full service");
        reservationDto.setDiscount("10%");
        reservationDto.setStartDate(LocalDate.of(2023, 5, 15));
        reservationDto.setEndDate(LocalDate.of(2023, 5, 20));
        reservationDto.setStatus("Confirmed");
        reservationDto.setDateReservation(LocalDate.now());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPassword("password");
        customerDto.setPrivilege("admin");

        CarDto carDto = new CarDto();
        carDto.setId(1L);
        carDto.setMark("hyundai i10");
        carDto.setReference("ABC123");
        carDto.setNumberOfDoors(4);
        carDto.setNumberOfPlace(5);
        carDto.setNumberOfHorses(100);
        carDto.setGearbox("Automatic");
        carDto.setPrice(50000L);
        carDto.setColor("White");
        carDto.setImage("car_image.jpg");
        carDto.setNumberOfAvailableCars(3);

        reservationDto.setCustomerId(customerDto.getId());
        reservationDto.setCarId(carDto.getId());
        // WHEN
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        // THEN
        assertEquals(reservationDto.getId(), reservation.getId());
        assertEquals(reservationDto.getCareAndSupport(), reservation.getCareAndSupport());
        assertEquals(reservationDto.getDiscount(), reservation.getDiscount());
        assertEquals(reservationDto.getStartDate(), reservation.getStartDate());
        assertEquals(reservationDto.getEndDate(), reservation.getEndDate());
        assertEquals(reservationDto.getStatus(), reservation.getStatus());
        assertEquals(reservationDto.getDateReservation(), reservation.getDateReservation());
    }

    @Test
    void GIVEN_ReservationEntities_WHEN_toReservationList_THEN_should_return_ReservationDtoList() {
        //GIVEN
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setCareAndSupport("Full");
        reservation1.setDiscount("10%");
        reservation1.setStartDate(LocalDate.of(2023, 5, 15));
        reservation1.setEndDate(LocalDate.of(2023, 5, 20));
        reservation1.setStatus("Active");
        reservation1.setDateReservation(LocalDate.now());

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCareAndSupport("Basic");
        reservation2.setDiscount("5%");
        reservation2.setStartDate(LocalDate.of(2023, 6, 1));
        reservation2.setEndDate(LocalDate.of(2023, 6, 7));
        reservation2.setStatus("Pending");
        reservation2.setDateReservation(LocalDate.now());


        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password");
        customer.setPrivilege("admin");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setPassword("password123");
        customer2.setPrivilege("user");

        Car car = new Car();
        car.setId(1L);
        car.setMark("hyundai i10");
        car.setReference("ABC123");
        car.setNumberOfDoors(4);
        car.setNumberOfPlace(5);
        car.setNumberOfHorses(100);
        car.setGearbox("Automatic");
        car.setPrice(50000L);
        car.setColor("White");
        car.setImage("car_image.jpg");
        car.setNumberOfAvailableCars(3);

        Car secondCar = new Car();
        secondCar.setId(2L);
        secondCar.setMark("Toyota Corolla");
        secondCar.setReference("XYZ789");
        secondCar.setNumberOfDoors(5);
        secondCar.setNumberOfPlace(5);
        secondCar.setNumberOfHorses(120);
        secondCar.setGearbox("Automatic");
        secondCar.setPrice(25000L);
        secondCar.setColor("Blue");
        secondCar.setImage("car_image2.jpg");
        secondCar.setNumberOfAvailableCars(2);

        reservation1.setCustomer(customer);
        reservation1.setCar(car);
        reservation2.setCustomer(customer2);
        reservation2.setCar(secondCar);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        // WHEN
        List<ReservationDto> reservationDtoList = reservationMapper.reservationDtoList(reservations);
        // THEN
        for (int i = 0; i < reservationDtoList.size(); i++) {
            ReservationDto reservationDto = reservationDtoList.get(i);
            Reservation reservation = reservations.get(i);

            assertEquals(reservationDto.getId(), reservation.getId());
            assertEquals(reservationDto.getCareAndSupport(), reservation.getCareAndSupport());
            assertEquals(reservationDto.getDiscount(), reservation.getDiscount());
            assertEquals(reservationDto.getStartDate(), reservation.getStartDate());
            assertEquals(reservationDto.getEndDate(), reservation.getEndDate());
            assertEquals(reservationDto.getStatus(), reservation.getStatus());
            assertEquals(reservationDto.getDateReservation(), reservation.getDateReservation());
            assertEquals(reservationDto.getCarId(), reservation.getCar().getId());
            assertEquals(reservationDto.getCustomerId(), reservation.getCustomer().getId());
        }
    }

    @Test
    void GIVEN_Reservation_WHEN_toDto_THEN_sould_return_Exception() {
        //GIVEN
        Reservation reservation = null;
        //WHEN && THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            reservationMapper.toDto(reservation);
        });
        assertEquals("RESERVATION NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_ReservationDto_WHEN_toEntity_THEN_sould_return_Exception() {
        //GIVEN
        ReservationDto reservationDto = null;
        //WHEN && THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            reservationMapper.toEntity(reservationDto);
        });
        assertEquals("RESERVATION NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_Reservations_WHEN_toListReservationDto_THEN_should_return_Exception() {
        //GIVEN
        List<Reservation> reservations = null;
        //WHEN && THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            reservationMapper.reservationDtoList(reservations);
        });
        assertEquals("RESERVATIONS NOT FOUND", e.getMessage());
    }

}