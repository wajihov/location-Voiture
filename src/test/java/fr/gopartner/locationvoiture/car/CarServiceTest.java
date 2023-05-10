package fr.gopartner.locationvoiture.car;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.car.CarMapper;
import fr.gopartner.locationvoiture.domain.car.CarRepository;
import fr.gopartner.locationvoiture.domain.car.CarService;
import fr.gopartner.locationvoiture.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CarServiceTest {

    @MockBean
    private CarRepository carRepository;
    @MockBean
    private CarMapper carMapper;
    @Autowired
    private CarService carService;
    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;

    @Test
    void GIVEN_CarDto_WHEN_CreateCar_THEN_should_save_into_database() {
        //GIVEN
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

        Car car = new Car();
        car.setId(carDto.getId());
        car.setMark(carDto.getMark());
        car.setReference(carDto.getReference());
        car.setNumberOfDoors(carDto.getNumberOfDoors());
        car.setNumberOfPlace(carDto.getNumberOfPlace());
        car.setNumberOfHorses(carDto.getNumberOfHorses());
        car.setGearbox(carDto.getGearbox());
        car.setPrice(carDto.getPrice());
        car.setColor(carDto.getColor());
        car.setImage(carDto.getImage());
        car.setNumberOfAvailableCars(carDto.getNumberOfAvailableCars());

        Mockito.when(carMapper.toEntity(carDto)).thenReturn(car);
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Mockito.when(carMapper.toDto(car)).thenReturn(carDto);
        //WHEN
        CarDto carDtoCreated = carService.createCar(carDto);
        //THEN
        Mockito.verify(carRepository).save(carArgumentCaptor.capture());
        Car carSaved = carArgumentCaptor.getValue();
        assertEquals(carSaved.getId(), carDtoCreated.getId());
        assertEquals(carSaved.getMark(), carDtoCreated.getMark());
        assertEquals(carSaved.getNumberOfDoors(), carDtoCreated.getNumberOfDoors());
        assertEquals(carSaved.getNumberOfPlace(), carDtoCreated.getNumberOfPlace());
        assertEquals(carSaved.getNumberOfHorses(), carDtoCreated.getNumberOfHorses());
        assertEquals(carSaved.getGearbox(), carDtoCreated.getGearbox());
        assertEquals(carSaved.getPrice(), carDtoCreated.getPrice());
        assertEquals(carSaved.getColor(), carDtoCreated.getColor());
        assertEquals(carSaved.getImage(), carDtoCreated.getImage());
        assertEquals(carSaved.getNumberOfAvailableCars(), carDtoCreated.getNumberOfAvailableCars());
    }

    @Test
    void GIVEN_carId_WHEN_deleteCarById_THEN_should_delete_from_database() {
        //GIVEN
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

        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
        //WHEN
        Long carId = 1L;
        carService.deleteCar(carId);
        //THEN
        Mockito.verify(carRepository).delete(carArgumentCaptor.capture());
        Car carDeleted = carArgumentCaptor.getValue();
        assertEquals(carDeleted.getId(), car.getId());
        assertEquals(carDeleted.getMark(), car.getMark());
        assertEquals(carDeleted.getReference(), car.getReference());
        assertEquals(carDeleted.getNumberOfDoors(), car.getNumberOfDoors());
        assertEquals(carDeleted.getNumberOfPlace(), car.getNumberOfPlace());
        assertEquals(carDeleted.getNumberOfHorses(), car.getNumberOfHorses());
        assertEquals(carDeleted.getGearbox(), car.getGearbox());
        assertEquals(carDeleted.getPrice(), car.getPrice());
        assertEquals(carDeleted.getColor(), car.getColor());
        assertEquals(carDeleted.getImage(), car.getImage());
        assertEquals(carDeleted.getNumberOfAvailableCars(), car.getNumberOfAvailableCars());

    }


    @Test
    void GIVEN_car_saved_WHEN_getAllCars_THEN_Should_return_that_2_Cars() {
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

        CarDto carDto1 = CarDto.builder()
                .mark(firstCar.getMark())
                .reference(firstCar.getReference())
                .numberOfDoors(firstCar.getNumberOfDoors())
                .numberOfPlace(firstCar.getNumberOfPlace())
                .numberOfHorses(firstCar.getNumberOfHorses())
                .gearbox(firstCar.getGearbox())
                .price(firstCar.getPrice())
                .color(firstCar.getColor())
                .image(firstCar.getImage())
                .numberOfAvailableCars(firstCar.getNumberOfAvailableCars())
                .build();

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

        CarDto carDto2 = CarDto.builder()
                .mark(secondCar.getMark())
                .reference(secondCar.getReference())
                .numberOfDoors(secondCar.getNumberOfDoors())
                .numberOfPlace(secondCar.getNumberOfPlace())
                .numberOfHorses(secondCar.getNumberOfHorses())
                .gearbox(secondCar.getGearbox())
                .price(secondCar.getPrice())
                .color(secondCar.getColor())
                .image(secondCar.getImage())
                .numberOfAvailableCars(secondCar.getNumberOfAvailableCars())
                .build();
        List<Car> cars = new ArrayList<>();
        cars.add(firstCar);
        cars.add(secondCar);
        List<CarDto> carDtoList = new ArrayList<>();
        carDtoList.add(carDto1);
        carDtoList.add(carDto2);
        Mockito.when(carRepository.findAll()).thenReturn(cars);
        Mockito.when(carMapper.carDtoList(cars)).thenReturn(carDtoList);
        // WHEN
        List<CarDto> dtoList = carService.getAllCars();
        // THEN
        assertEquals(2, dtoList.size());
        assertEquals(carDtoList.size(), dtoList.size());
        for (int i = 0; i < carDtoList.size(); i++) {
            assertEquals(carDtoList.get(i).getId(), dtoList.get(i).getId());
            assertEquals(carDtoList.get(i).getMark(), dtoList.get(i).getMark());
            assertEquals(carDtoList.get(i).getReference(), dtoList.get(i).getReference());
            assertEquals(carDtoList.get(i).getNumberOfDoors(), dtoList.get(i).getNumberOfDoors());
            assertEquals(carDtoList.get(i).getNumberOfPlace(), dtoList.get(i).getNumberOfPlace());
            assertEquals(carDtoList.get(i).getNumberOfHorses(), dtoList.get(i).getNumberOfHorses());
            assertEquals(carDtoList.get(i).getGearbox(), dtoList.get(i).getGearbox());
            assertEquals(carDtoList.get(i).getPrice(), dtoList.get(i).getPrice());
            assertEquals(carDtoList.get(i).getColor(), dtoList.get(i).getColor());
            assertEquals(carDtoList.get(i).getImage(), dtoList.get(i).getImage());
            assertEquals(carDtoList.get(i).getNumberOfAvailableCars(), dtoList.get(i).getNumberOfAvailableCars());
        }
    }

    @Test
    void GIVEN_carId_WHEN_findCarById_THEN_SHOULD_findCar_from_database() {
        //GIVEN
        Long carId = 5L;
        Car car = new Car();
        car.setId(carId);
        car.setMark("Toyota Corolla");
        car.setReference("XYZ789");
        car.setNumberOfDoors(5);
        car.setNumberOfPlace(5);
        car.setNumberOfHorses(120);
        car.setGearbox("Automatic");
        car.setPrice(25000L);
        car.setColor("Blue");
        car.setImage("car_image2.jpg");
        car.setNumberOfAvailableCars(2);
        CarDto carDto = CarDto.builder()
                .id(car.getId())
                .mark(car.getMark())
                .reference(car.getReference())
                .numberOfDoors(car.getNumberOfDoors())
                .numberOfPlace(car.getNumberOfPlace())
                .numberOfHorses(car.getNumberOfHorses())
                .gearbox(car.getGearbox())
                .price(car.getPrice())
                .color(car.getColor())
                .image(car.getImage())
                .numberOfAvailableCars(car.getNumberOfAvailableCars())
                .build();
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
        Mockito.when(carMapper.toDto(car)).thenReturn(carDto);
        // WHEN
        CarDto carDtoService = carService.searchCarById(carId);
        //THEN
        assertEquals(carDtoService.getId(), carDto.getId());
        assertEquals(carDtoService.getMark(), carDto.getMark());
        assertEquals(carDtoService.getReference(), carDto.getReference());
        assertEquals(carDtoService.getNumberOfDoors(), carDto.getNumberOfDoors());
        assertEquals(carDtoService.getNumberOfPlace(), carDto.getNumberOfPlace());
        assertEquals(carDtoService.getNumberOfHorses(), carDto.getNumberOfHorses());
        assertEquals(carDtoService.getGearbox(), carDto.getGearbox());
        assertEquals(carDtoService.getPrice(), carDto.getPrice());
        assertEquals(carDtoService.getColor(), carDto.getColor());
        assertEquals(carDtoService.getImage(), carDto.getImage());
        assertEquals(carDtoService.getNumberOfAvailableCars(), carDto.getNumberOfAvailableCars());
    }

    @Test
    void GIVEN_Car_WHEN_updateCar_THEN_should_update_that_Car_into_Database() {
        //GIVEN
        Long carId = 3L;
        CarDto updatedCarDto = CarDto.builder()
                .id(carId)
                .mark("Updated Mark")
                .reference("Updated Reference")
                .numberOfDoors(4)
                .numberOfPlace(5)
                .numberOfHorses(100)
                .gearbox("Automatic")
                .price(50000L)
                .color("White")
                .image("car_image.jpg")
                .numberOfAvailableCars(3)
                .build();

        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setMark(updatedCarDto.getMark());
        updatedCar.setReference(updatedCarDto.getReference());
        updatedCar.setNumberOfDoors(updatedCarDto.getNumberOfDoors());
        updatedCar.setNumberOfPlace(updatedCarDto.getNumberOfPlace());
        updatedCar.setNumberOfHorses(updatedCarDto.getNumberOfHorses());
        updatedCar.setGearbox(updatedCarDto.getGearbox());
        updatedCar.setPrice(updatedCarDto.getPrice());
        updatedCar.setColor(updatedCarDto.getColor());
        updatedCar.setImage(updatedCarDto.getImage());
        updatedCar.setNumberOfAvailableCars(updatedCarDto.getNumberOfAvailableCars());

        Mockito.when(carMapper.toEntity(updatedCarDto)).thenReturn(updatedCar);
        Mockito.when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        Mockito.when(carMapper.toDto(updatedCar)).thenReturn(updatedCarDto);
        // WHEN
        CarDto result = carService.updateCar(carId, updatedCarDto);
        // THEN
        Mockito.verify(carRepository).save(updatedCar);
        assertEquals(updatedCarDto.getId(), result.getId());
        assertEquals(updatedCarDto.getMark(), result.getMark());
        assertEquals(updatedCarDto.getReference(), result.getReference());
        assertEquals(updatedCarDto.getNumberOfDoors(), result.getNumberOfDoors());
        assertEquals(updatedCarDto.getNumberOfPlace(), result.getNumberOfPlace());
        assertEquals(updatedCarDto.getNumberOfHorses(), result.getNumberOfHorses());
        assertEquals(updatedCarDto.getGearbox(), result.getGearbox());
        assertEquals(updatedCarDto.getPrice(), result.getPrice());
        assertEquals(updatedCarDto.getColor(), result.getColor());
        assertEquals(updatedCarDto.getImage(), result.getImage());
        assertEquals(updatedCarDto.getNumberOfAvailableCars(), result.getNumberOfAvailableCars());
    }

    @Test
    void GIVEN_car_WHEN_deleteCar_THEN_Should_Return_RuntimeException() {
        //GIVEN & WHEN
        Car car = new Car();
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            carService.deleteCar(null);
        });
        assertEquals("CAR NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_carId_WHEN_findCarById_THEN_Should_Return_RuntimeException() {
        //GIVEN & WHEN
        Car car = new Car();
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            carService.searchCarById(null);
        });
        assertEquals("CAR NOT FOUND", e.getMessage());
    }

}
