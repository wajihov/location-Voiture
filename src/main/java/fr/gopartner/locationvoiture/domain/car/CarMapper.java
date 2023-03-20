package fr.gopartner.locationvoiture.domain.car;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.core.utils.CollectionUtils;
import fr.gopartner.locationvoiture.dto.CarDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMapper {

    public Car toEntity(CarDto carDto) {
        if (carDto == null) {
            throw new CarReservationCustomerException(Codes.ERR_CAR_NOT_FOUND);
        }
        return Car.builder()
                .id(carDto.getId())
                .mark(carDto.getMark())
                .reference(carDto.getReference())
                .numberOfDoors(carDto.getNumberOfDoors())
                .numberOfPlace(carDto.getNumberOfPlace())
                .numberOfHorses(carDto.getNumberOfHorses())
                .gearbox(carDto.getGearbox())
                .price(carDto.getPrice())
                .color(carDto.getColor())
                .image(carDto.getImage())
                .numberOfAvailableCars(carDto.getNumberOfAvailableCars())
                .build();
    }

    public CarDto toDto(Car car) {
        if (car == null) {
            throw new CarReservationCustomerException(Codes.ERR_CAR_NOT_FOUND);
        }
        return CarDto.builder()
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
    }

    public List<CarDto> carDtoList(List<Car> cars) {
        if (CollectionUtils.isNullOrEmpty(cars)) {
            throw new CarReservationCustomerException(Codes.ERR_CARS_NOT_FOUND);
        }
        return cars.stream().map(this::toDto).collect(Collectors.toList());
    }
}
