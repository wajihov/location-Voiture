package fr.gopartner.locationvoiture.customer;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.core.utils.JsonUtils;
import fr.gopartner.locationvoiture.domain.customer.CustomerService;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    void GIVEN_Customer_WHEN_create_THEN_should_return_customerDto_into_database() throws Exception {
        //GIVEN
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Francisco");
        customerDto.setEmail("test@hotmail.fr");
        customerDto.setPassword("test");
        customerDto.setPrivilege("test");

        CustomerDto customerDtoCreated = new CustomerDto();
        customerDtoCreated.setId(1L);
        customerDtoCreated.setName(customerDto.getName());
        customerDtoCreated.setEmail(customerDto.getEmail());
        customerDtoCreated.setPassword(customerDto.getPassword());
        customerDtoCreated.setPrivilege(customerDto.getPrivilege());

        Mockito.when(customerService.createCustomer(Mockito.any())).thenReturn(customerDtoCreated);
        //WHEN && THEN
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoCreated)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Francisco"))
                .andExpect(jsonPath("$.email").value("test@hotmail.fr"))
                .andExpect(jsonPath("$.password").value("test"))
                .andExpect(jsonPath("$.privilege").value("test"));
    }

    @Test
    void GIVEN_customerId_WHEN_delete_THEN_should_delete_customer_from_database() throws Exception {
        //GIVEN
        Mockito.doNothing().when(customerService).deleteCustomer(Mockito.anyLong());
        //WHEN && THEN
        mockMvc.perform(delete("/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    void GIVEN_customerId_WHEN_getCustomerById_THEN_should_return_customerDto_from_database() throws Exception {
        //GIVEN
        Long customerId = 5L;
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerId);
        customerDto.setName("John HANDSOME");
        customerDto.setEmail("john.test@gmail.com");
        customerDto.setPassword("test");
        customerDto.setPrivilege("test");

        Mockito.when(customerService.searchCustomerById(Mockito.anyLong())).thenReturn(customerDto);
        //WHEN && THEN
        mockMvc.perform(get("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("John HANDSOME"))
                .andExpect(jsonPath("$.email").value("john.test@gmail.com"))
                .andExpect(jsonPath("$.password").value("test"))
                .andExpect(jsonPath("$.privilege").value("test"));
    }

    @Test
    void GIVEN_all_customer_WHEN_getAllCustomer_THEN_should_return_listCustomerDto_from_database() throws Exception {
        //GIVEN
        CustomerDto firstCustomerDto = new CustomerDto();
        firstCustomerDto.setId(8L);
        firstCustomerDto.setName("John HANDSOME");
        firstCustomerDto.setEmail("john.handsome@gmail.com");
        firstCustomerDto.setPassword("test123");
        firstCustomerDto.setPrivilege("testPR3");

        CustomerDto secondCustomerDto = new CustomerDto();
        secondCustomerDto.setId(6L);
        secondCustomerDto.setName("Fransisco Alma");
        secondCustomerDto.setEmail("fransisco.alma@gmail.com");
        secondCustomerDto.setPassword("test456");
        secondCustomerDto.setPrivilege("testPR2");

        List<CustomerDto> customerDtoList = new ArrayList<>() {
            {
                add(firstCustomerDto);
                add(secondCustomerDto);
            }
        };

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDtoList);
        //WHEN && THEN
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(8L))
                .andExpect(jsonPath("$[0].name").value("John HANDSOME"))
                .andExpect(jsonPath("$[0].email").value("john.handsome@gmail.com"))
                .andExpect(jsonPath("$[0].password").value("test123"))
                .andExpect(jsonPath("$[0].privilege").value("testPR3"))
                .andExpect(jsonPath("$[1].id").value(6L))
                .andExpect(jsonPath("$[1].name").value("Fransisco Alma"))
                .andExpect(jsonPath("$[1].email").value("fransisco.alma@gmail.com"))
                .andExpect(jsonPath("$[1].password").value("test456"))
                .andExpect(jsonPath("$[1].privilege").value("testPR2"))
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoList)));
    }

    @Test
    void GIVEN_customerDto_WHEN_updateCustomer_THEN_should_return_customerDto_updated_into_database() throws Exception {
        //GIVEN
        Long customerId = 2L;
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Francisco");
        customerDto.setEmail("test@hotmail.fr");
        customerDto.setPassword("test");
        customerDto.setPrivilege("test");

        CustomerDto customerDtoUpdated = new CustomerDto();
        customerDtoUpdated.setId(customerId);
        customerDtoUpdated.setName("David Beckham");
        customerDtoUpdated.setEmail("david.Beckham.outlook.fr");
        customerDtoUpdated.setPassword("avert123");
        customerDtoUpdated.setPrivilege("david");

        Mockito.when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(customerDtoUpdated);
        //WHEN
        mockMvc.perform(put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("David Beckham"))
                .andExpect(jsonPath("$.email").value("david.Beckham.outlook.fr"))
                .andExpect(jsonPath("$.password").value("avert123"))
                .andExpect(jsonPath("$.privilege").value("david"))
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoUpdated)));
        //THEN
        verify(customerService, times(1)).updateCustomer(Mockito.anyLong(), Mockito.any());
    }

    @Test
    void GIVEN_NonExistingCustomerId_WHEN_getCustomerById_THEN_ReturnNotFound() throws Exception {
        // GIVEN
        Long nonExistingCustomerId = 999L;
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(nonExistingCustomerId);
        customerDto.setName("Roached Sassy");
        customerDto.setEmail("roached.sassy@yahoo.fr");
        customerDto.setPassword("password");

        Mockito.when(customerService.searchCustomerById(Mockito.anyLong())).thenThrow(new CarReservationCustomerException(Codes.ERR_CUSTOMER_NOT_FOUND));
        // WHEN
        mockMvc.perform(get("/customers/{id}", nonExistingCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(customerDto)))
                // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    void GIVEN_noCustomers_WHEN_getAllCustomers_THEN_returnEmptyList() throws Exception {
        // GIVEN
        Mockito.when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());
        // WHEN
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));
        // THEN
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

}