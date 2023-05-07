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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void GIVEN_Car_WHEN_CreateCar_THEN_SHOUD_save_on_database() throws Exception {
        //GIVEN
        CarDto carDto = new CarDto();
        carDto.setId(1L);
        carDto.setColor("Blue");
        carDto.setMark("hyundai");
        carDto.setReference("RE-TYU");

        Mockito.when(carService.createCar(Mockito.any())).thenReturn(carDto);
        //WHEN && THEN
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(carDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(JsonUtils.asJsonString(carDto)))
                .andExpect(jsonPath("$.color").value("Blue"))
                .andExpect(jsonPath("$.mark").value("hyundai"));


    }


}
