package fr.gopartner.locationvoiture.domain.car;

import fr.gopartner.locationvoiture.CarsApiDelegate;
import fr.gopartner.locationvoiture.dto.CarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController implements CarsApiDelegate {

    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        return CarsApiDelegate.super.createCar(carDto);
    }

    @Override
    public ResponseEntity<CarDto> deleteCarById(Long id) {
        return CarsApiDelegate.super.deleteCarById(id);
    }

    @Override
    public ResponseEntity<CarDto> getCarById(Long id) {
        return CarsApiDelegate.super.getCarById(id);
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars() {
        return CarsApiDelegate.super.getCars();
    }

    @Override
    public ResponseEntity<Void> updateCarById(Long id, CarDto carDto) {
        return CarsApiDelegate.super.updateCarById(id, carDto);
    }
}
