package fr.gopartner.locationvoiture.domain.car;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.dto.CarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public CarDto createCar(CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        car = carRepository.save(car);
        log.info("The car is added successfully {}", carDto.getMark());
        return carMapper.toDto(car);
    }

    private Car rechercheCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new
                CarReservationCustomerException(Codes.ERR_CAR_NOT_FOUND));
    }

    public CarDto searchCarById(Long id) {
        Car car = rechercheCarById(id);
        CarDto carDto = carMapper.toDto(car);
        log.info("The car searched is {}", carDto.getMark());
        return carDto;
    }

    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        log.info("list {} Cars", cars.size());
        return carMapper.carDtoList(cars);
    }

    public void deleteCar(Long id) {
        Car car = rechercheCarById(id);
        carRepository.delete(car);
        log.info("The deletion of the Car Mark {} is successful", car.getMark());
    }

    public CarDto updateCar(Long id, CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        car.setId(id);
        car = carRepository.save(car);
        log.info("The Car with id {} has been successfully modified", car.getId());
        return carMapper.toDto(car);
    }
}
