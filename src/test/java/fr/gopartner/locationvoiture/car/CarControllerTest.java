package fr.gopartner.locationvoiture.car;

import fr.gopartner.locationvoiture.core.utils.JsonUtils;
import fr.gopartner.locationvoiture.domain.car.CarService;
import fr.gopartner.locationvoiture.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class CarControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    void GIVEN_car_WHEN_create_THEN_should_return_created_carDto_into_database() throws Exception {
        //GIVEN
        CarDto carDto = new CarDto();
        carDto.setColor("Blue");
        carDto.setMark("hyundai");
        carDto.setReference("RE-TYU");

        CarDto carDtoCreated = new CarDto();
        carDtoCreated.setId(1L);
        carDtoCreated.setColor(carDto.getColor());
        carDtoCreated.setMark(carDto.getMark());
        carDtoCreated.setReference(carDto.getReference());

        Mockito.when(carService.createCar(Mockito.any())).thenReturn(carDtoCreated);
        //WHEN && THEN
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(carDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(JsonUtils.asJsonString(carDtoCreated)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.color").value("Blue"))
                .andExpect(jsonPath("$.mark").value("hyundai"))
                .andExpect(jsonPath("$.reference").value("RE-TYU"));
    }

    @Test
    void GIVEN_carId_WHEN_delete_car_THEN_should_delete_car_from_database() throws Exception {
        //GIVEN
        doNothing().when(carService).deleteCar(anyLong());
        //WHEN && THEN
        mockMvc.perform(delete("/cars/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCar(1L);
    }

    @Test
    void GIVEN_carId_WHEN_getCarById_THEN_should_return_carDto_from_database() throws Exception {
        //GIVEN
        Long carId = 1L;
        CarDto carDto = new CarDto();
        carDto.setId(carId);
        carDto.setColor("Red");
        carDto.setMark("Peugeot");
        carDto.setReference("KT-YH25");

        Mockito.when(carService.searchCarById(Mockito.anyLong())).thenReturn(carDto);
        //WHEN
        mockMvc.perform(get("/cars/" + carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.reference").value("KT-YH25"))
                .andExpect(jsonPath("$.mark").value("Peugeot"))
                .andExpect(content().string(JsonUtils.asJsonString(carDto)));
    }

    @Test
    void GIVEN_cars_WHEN_getAllCars_THEN_should_return_listCars_from_database() throws Exception {
        // GIVEN
        List<CarDto> carDtoList = new ArrayList<>();
        CarDto firstCarDto = new CarDto();
        firstCarDto.setId(1L);
        firstCarDto.setColor("Red");
        firstCarDto.setMark("Peugeot");
        firstCarDto.setReference("KT-YH25");
        carDtoList.add(firstCarDto);
        CarDto secondCarDto = new CarDto();
        secondCarDto.setId(2L);
        secondCarDto.setColor("Blue");
        secondCarDto.setMark("Renault");
        secondCarDto.setReference("AB-KL89");
        carDtoList.add(secondCarDto);

        Mockito.when(carService.getAllCars()).thenReturn(carDtoList);
        // WHEN && THEN
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].reference").value("KT-YH25"))
                .andExpect(jsonPath("$[0].mark").value("Peugeot"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].color").value("Blue"))
                .andExpect(jsonPath("$[1].reference").value("AB-KL89"))
                .andExpect(jsonPath("$[1].mark").value("Renault"))
                .andExpect(content().string(JsonUtils.asJsonString(carDtoList)));
    }


    @Test
    void GIVEN_carDto_WHEN_updateCar_THEN_should_return_updatedCar() throws Exception {
        // GIVEN
        Long carId = 1L;
        CarDto carDto = new CarDto();
        carDto.setId(carId);
        carDto.setColor("Blue");
        carDto.setMark("Toyota");
        carDto.setReference("ABC123");

        CarDto updatedCarDto = new CarDto();
        updatedCarDto.setId(carId);
        updatedCarDto.setColor("Red");
        updatedCarDto.setMark("Toyota");
        updatedCarDto.setReference("DEF456");

        Mockito.when(carService.updateCar(Mockito.anyLong(), Mockito.any())).thenReturn(updatedCarDto);
        // WHEN
        mockMvc.perform(put("/cars/" + carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(carDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.mark").value("Toyota"))
                .andExpect(jsonPath("$.reference").value("DEF456"))
                .andExpect(content().string(JsonUtils.asJsonString(updatedCarDto)));

        // THEN
        Mockito.verify(carService, Mockito.times(1)).updateCar(Mockito.anyLong(), Mockito.any());
    }
}
