package fr.gopartner.locationvoiture.car;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.car.CarMapper;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import fr.gopartner.locationvoiture.domain.reservation.ReservationMapper;
import fr.gopartner.locationvoiture.dto.CarDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CarMapperTest {

    @MockBean
    private ReservationMapper reservationMapper;

    @Autowired
    private CarMapper carMapper;

    @Test
    void GIVEN_Car_WHEN_ToDto_THEN_should_return_CarDto() {
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
        // WHEN
        CarDto carDto = carMapper.toDto(car);
        // THEN
        assertEquals(carDto.getId(), car.getId());
        assertEquals(carDto.getMark(), car.getMark());
        assertEquals(carDto.getReference(), car.getReference());
        assertEquals(carDto.getNumberOfDoors(), car.getNumberOfDoors());
        assertEquals(carDto.getNumberOfPlace(), car.getNumberOfPlace());
        assertEquals(carDto.getNumberOfHorses(), car.getNumberOfHorses());
        assertEquals(carDto.getGearbox(), car.getGearbox());
        assertEquals(carDto.getPrice(), car.getPrice());
        assertEquals(carDto.getColor(), car.getColor());
        assertEquals(carDto.getImage(), car.getImage());
        assertEquals(carDto.getNumberOfAvailableCars(), car.getNumberOfAvailableCars());
    }

    @Test
    void GIVEN_CarDto_WHEN_ToEntity_THEN_should_return_Car() {
        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .careAndSupport("Fantastic Car")
                .discount("3%")
                .dateReservation(LocalDate.of(2023, 1, 4))
                .endDate(LocalDate.of(2023, 1, 15))
                .startDate(LocalDate.of(2023, 1, 5))
                .build();
        List<ReservationDto> dtoList = new ArrayList<>();
        dtoList.add(reservationDto);

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
        carDto.setReservations(dtoList);
        // WHEN
        Car car = carMapper.toEntity(carDto);
        // THEN
        assertEquals(car.getId(), carDto.getId());
        assertEquals(car.getMark(), carDto.getMark());
        assertEquals(car.getReference(), carDto.getReference());
        assertEquals(car.getNumberOfDoors(), carDto.getNumberOfDoors());
        assertEquals(car.getNumberOfPlace(), carDto.getNumberOfPlace());
        assertEquals(car.getNumberOfHorses(), carDto.getNumberOfHorses());
        assertEquals(car.getGearbox(), carDto.getGearbox());
        assertEquals(car.getPrice(), carDto.getPrice());
        assertEquals(car.getColor(), carDto.getColor());
        assertEquals(car.getImage(), carDto.getImage());
        assertEquals(car.getNumberOfAvailableCars(), carDto.getNumberOfAvailableCars());
    }

    @Test
    void GIVEN_Cars_WHEN_CarDtoList_THEN_Return_ListOfCarDto() {
        // GIVEN
        Car firstCar = new Car();
        firstCar.setId(1L);
        firstCar.setMark("Hyundai i10");
        firstCar.setReference("ABC123");
        firstCar.setNumberOfDoors(4);
        firstCar.setNumberOfPlace(5);
        firstCar.setNumberOfHorses(100);
        firstCar.setGearbox("Manual");
        firstCar.setPrice(20000L);
        firstCar.setColor("Red");
        firstCar.setImage("car_image.jpg");
        firstCar.setNumberOfAvailableCars(3);

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

        List<Reservation> reservationList1 = new ArrayList<>();
        reservationList1.add(reservation1);

        List<Reservation> reservationList2 = new ArrayList<>();
        reservationList2.add(reservation2);
        firstCar.setReservations(reservationList1);
        secondCar.setReservations(reservationList2);

        //WHEN
        List<Car> carList = new ArrayList<>();
        carList.add(firstCar);
        carList.add(secondCar);
        List<CarDto> carDtoList = carMapper.carDtoList(carList);

        //THEN
        assertEquals(carDtoList.size(), carList.size());
        for (int i = 0; i < carDtoList.size(); i++) {
            Car car = carList.get(i);
            CarDto carDto = carDtoList.get(i);

            assertEquals(carDto.getId(), car.getId());
            assertEquals(carDto.getMark(), car.getMark());
            assertEquals(carDto.getReference(), car.getReference());
            assertEquals(carDto.getNumberOfDoors(), car.getNumberOfDoors());
            assertEquals(carDto.getNumberOfPlace(), car.getNumberOfPlace());
            assertEquals(carDto.getNumberOfHorses(), car.getNumberOfHorses());
            assertEquals(carDto.getGearbox(), car.getGearbox());
            assertEquals(carDto.getPrice(), car.getPrice());
            assertEquals(carDto.getColor(), car.getColor());
            assertEquals(carDto.getImage(), car.getImage());
            assertEquals(carDto.getNumberOfAvailableCars(), car.getNumberOfAvailableCars());
            for (int j = 0; j < carDto.getReservations().size(); j++) {
                assertEquals(carDto.getReservations().get(j).getId(), car.getReservations().get(j).getId());
                assertEquals(carDto.getReservations().get(j).getCareAndSupport(), car.getReservations().get(j).getCareAndSupport());
                assertEquals(carDto.getReservations().get(j).getDiscount(), car.getReservations().get(j).getDiscount());
                assertEquals(carDto.getReservations().get(j).getStartDate(), car.getReservations().get(j).getStartDate());
                assertEquals(carDto.getReservations().get(j).getEndDate(), car.getReservations().get(j).getEndDate());
                assertEquals(carDto.getReservations().get(j).getStatus(), car.getReservations().get(j).getStatus());
                assertEquals(carDto.getReservations().get(j).getDateReservation(), car.getReservations().get(j).getDateReservation());
            }
        }
    }

    @Test
    void GIVEN_Car_WHEN_toCarDto_THEN_should_return_Exception() {
        // GIVEN
        Car car = null;
        // WHEN & THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            carMapper.toDto(car);
        });
        assertEquals("CAR NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_CarDto_WHEN_toCar_THEN_should_return_Exception() {
        // GIVEN
        CarDto carDto = null;
        // WHEN & THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            carMapper.toEntity(carDto);
        });
        assertEquals("CAR NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_Cars_WHEN_toListCarsDto_THEN_should_return_Exception() {
        //GIVEN
        List<Car> carList = null;
        //WHEN && THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            carMapper.carDtoList(carList);
        });
        assertEquals("CARS NOT FOUND", e.getMessage());
    }

}
