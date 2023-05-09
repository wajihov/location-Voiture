package fr.gopartner.locationvoiture.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.core.utils.JsonUtils;
import fr.gopartner.locationvoiture.domain.reservation.ReservationService;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class ReservationControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void GIVEN_reservation_WHEN_Create_ReservationDto_THEN_return_reservationDto_into_database() throws Exception {
        //GIVEN
        ReservationDto reservationDto = new ReservationDto();
        log.info("Date : " + LocalDate.now());
        LocalDate str = LocalDate.of(2020, 8, 15);
        log.info("the date is " + str);

        reservationDto.setCareAndSupport("test support");
        reservationDto.setDiscount("test");
        reservationDto.setCarId(1L);
        reservationDto.setCustomerId(1L);
        reservationDto.setStatus("Good Car");
        //reservationDto.setDateReservation(LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String json = objectMapper.writeValueAsString(reservationDto);
            log.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ReservationDto reservationDtoCreated = new ReservationDto();
        reservationDtoCreated.setId(1L);
        reservationDtoCreated.setCareAndSupport(reservationDto.getCareAndSupport());
        reservationDtoCreated.setDiscount(reservationDto.getDiscount());
        reservationDtoCreated.setCarId(reservationDto.getCarId());
        reservationDtoCreated.setCustomerId(reservationDto.getCustomerId());
        reservationDtoCreated.setStatus(reservationDto.getStatus());
        //reservationDtoCreated.setDateReservation(reservationDto.getDateReservation());

        Mockito.when(reservationService.createReservation(Mockito.any())).thenReturn(reservationDtoCreated);
        //WHEN && THEN
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(reservationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(JsonUtils.asJsonString(reservationDtoCreated)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.careAndSupport").value("test support"))
                .andExpect(jsonPath("$.discount").value("test"))
                .andExpect(jsonPath("$.status").value("Good Car"))
                .andExpect(jsonPath("$.car_id").value(1L))
                .andExpect(jsonPath("$.customer_id").value(1L));
        //.andExpect(jsonPath("$.dateReservation").value(LocalDate.now()));
    }

    @Test
    void GIVEN_reservationId_WHEN_delete_THEN_should_delete_reservation_from_database() throws Exception {
        //GIVEN
        Mockito.doNothing().when(reservationService).deleteReservation(Mockito.anyLong());
        //WHEN && THEN
        mockMvc.perform(delete("/reservations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservation(1L);
    }

    @Test
    void GIVEN_reservationId_WHEN_getReservationById_THEN_should_return_reservationDto_from_database() throws Exception {
        //GIVEN
        Long reservationId = 2L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservationId);
        reservationDto.setCareAndSupport("test support");
        reservationDto.setDiscount("test");
        reservationDto.setCarId(1L);
        reservationDto.setCustomerId(1L);
        reservationDto.setStatus("Good Car");

        Mockito.when(reservationService.getReservationById(Mockito.anyLong())).thenReturn(reservationDto);
        //WHEN && THEN
        mockMvc.perform(get("/reservations/{id}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId))
                .andExpect(jsonPath("$.car_id").value(1L))
                .andExpect(jsonPath("$.customer_id").value(1L))
                .andExpect(jsonPath("$.careAndSupport").value("test support"))
                .andExpect(jsonPath("$.discount").value("test"))
                .andExpect(jsonPath("$.status").value("Good Car"));
    }

    @Test
    void GIVEN_reservationsList_WHEN_getAllReservations_THEN_should_return_allReservations_from_database() throws Exception {
        //GIVEN
        ReservationDto firstReservationDto = new ReservationDto();
        firstReservationDto.setId(1L);
        firstReservationDto.setCareAndSupport("In good condition");
        firstReservationDto.setDiscount("2%");
        firstReservationDto.setCarId(1L);
        firstReservationDto.setCustomerId(1L);
        firstReservationDto.setStatus("Good Car");

        ReservationDto secondReservationDto = new ReservationDto();
        secondReservationDto.setId(2L);
        secondReservationDto.setCareAndSupport("State of the art");
        secondReservationDto.setDiscount("5%");
        secondReservationDto.setCarId(2L);
        secondReservationDto.setCustomerId(2L);
        secondReservationDto.setStatus("Bad Car");

        List<ReservationDto> reservationDtoList = new ArrayList<>() {
            {
                add(firstReservationDto);
                add(secondReservationDto);
            }
        };

        Mockito.when(reservationService.getAllReservations()).thenReturn(reservationDtoList);
        mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].careAndSupport").value("In good condition"))
                .andExpect(jsonPath("$[0].discount").value("2%"))
                .andExpect(jsonPath("$[0].car_id").value(1L))
                .andExpect(jsonPath("$[0].customer_id").value(1L))
                .andExpect(jsonPath("$[0].status").value("Good Car"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].careAndSupport").value("State of the art"))
                .andExpect(jsonPath("$[1].discount").value("5%"))
                .andExpect(jsonPath("$[1].status").value("Bad Car"))
                .andExpect(jsonPath("$[1].customer_id").value(2L))
                .andExpect(jsonPath("$[1].car_id").value(2L))
                .andExpect(content().string(JsonUtils.asJsonString(reservationDtoList)));
    }

    @Test
    void GIVEN_reservationDto_WHEN_updateReservation_THEN_sould_return_reservationUpdated() throws Exception {
        Long reservationId = 3L;
        //GIVEN
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCareAndSupport("Test Case");
        reservationDto.setDiscount("3 %");
        reservationDto.setCarId(1L);
        reservationDto.setCustomerId(1L);
        reservationDto.setStatus("Good Car");

        ReservationDto reservationDtoUpdated = new ReservationDto();
        reservationDtoUpdated.setId(reservationId);
        reservationDtoUpdated.setCareAndSupport("State of the art");
        reservationDtoUpdated.setDiscount("5%");
        reservationDtoUpdated.setCarId(2L);
        reservationDtoUpdated.setCustomerId(1L);
        reservationDtoUpdated.setStatus("Bad Car");
        Mockito.when(reservationService.updateReservation(Mockito.anyLong(), Mockito.any())).thenReturn(reservationDtoUpdated);

        //WHEN
        mockMvc.perform(put("/reservations/{id}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(reservationDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(reservationId))
                .andExpect(jsonPath("$.car_id").value(2L))
                .andExpect(jsonPath("$.customer_id").value(1L))
                .andExpect(jsonPath("$.careAndSupport").value("State of the art"))
                .andExpect(jsonPath("$.discount").value("5%"))
                .andExpect(jsonPath("$.status").value("Bad Car"))
                .andExpect(content().string(JsonUtils.asJsonString(reservationDtoUpdated)));
        //THEN
        verify(reservationService, times(1)).updateReservation(Mockito.anyLong(), Mockito.any());
    }

    @Test
    void GIVEN_noReservations_WHEN_getAllReservations_THEN_returnEmptyList() throws Exception {
        // GIVEN
        Mockito.when(reservationService.getAllReservations()).thenReturn(Collections.emptyList());
        // WHEN
        mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));
        // THEN
        Mockito.verify(reservationService, Mockito.times(1)).getAllReservations();
    }

    @Test
    void GIVEN_NonExistingReservationId_WHEN_getreservationById_THEN_ReturnNotFound() throws Exception {
        // GIVEN
        Long nonExistingCustomerId = 999L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCareAndSupport("Test Case");
        reservationDto.setDiscount("3 %");
        reservationDto.setCarId(1L);
        reservationDto.setCustomerId(1L);
        reservationDto.setStatus("Good Car");

        Mockito.when(reservationService.getReservationById(Mockito.anyLong())).thenThrow(new CarReservationCustomerException(Codes.ERR_RESERVATION_NOT_FOUND));
        // WHEN
        mockMvc.perform(get("/reservations/{id}", nonExistingCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(reservationDto)))
                // THEN
                .andExpect(status().isNotFound());
    }

}