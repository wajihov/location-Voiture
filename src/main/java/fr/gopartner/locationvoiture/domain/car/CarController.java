package fr.gopartner.locationvoiture.domain.car;

import fr.gopartner.locationvoiture.CarsApiDelegate;
import fr.gopartner.locationvoiture.dto.CarDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController implements CarsApiDelegate {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        CarDto dto = carService.createCar(carDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCarById(Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CarDto> updateCarById(Long id, CarDto carDto) {
        CarDto dto = carService.updateCar(id, carDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarDto> getCarById(Long id) {
        CarDto carDto = carService.searchCarById(id);
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars() {
        List<CarDto> carDtoList = carService.getAllCars();
        return new ResponseEntity<>(carDtoList, HttpStatus.OK);
    }

}
