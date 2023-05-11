package fr.gopartner.locationvoiture.customer;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.customer.Customer;
import fr.gopartner.locationvoiture.domain.customer.CustomerMapper;
import fr.gopartner.locationvoiture.domain.customer.CustomerRepository;
import fr.gopartner.locationvoiture.domain.customer.CustomerService;
import fr.gopartner.locationvoiture.dto.CustomerDto;
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
public class CustomerServiceTest {

    @MockBean
    private CustomerMapper customerMapper;
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Test
    void GIVEN_CustomerDto_WHEN_toCreateCustomer_THEN_should_save_into_Database() {
        //GIVEN
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .privilege("admin")
                .build();

        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        customer.setPrivilege(customerDto.getPrivilege());

        Mockito.when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerDto);
        // WHEN
        CustomerDto createdCustomerDto = customerService.createCustomer(customerDto);
        // THEN
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer customerSaved = customerArgumentCaptor.getValue();
        assertEquals(customerSaved.getId(), createdCustomerDto.getId());
        assertEquals(customerSaved.getName(), createdCustomerDto.getName());
        assertEquals(customerSaved.getEmail(), createdCustomerDto.getEmail());
        assertEquals(customerSaved.getPassword(), createdCustomerDto.getPassword());
        assertEquals(customerSaved.getPrivilege(), createdCustomerDto.getPrivilege());
    }

    @Test
    void GIVEN_customerId_WHEN_getCustomerById_THEN_should_return_CustomerDto_from_database() {
        //GIVEN
        Long customerId = 4L;
        Customer customer1 = new Customer();
        customer1.setId(customerId);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPassword("password");
        customer1.setPrivilege("admin");

        CustomerDto customerDto = CustomerDto.builder()
                .id(customer1.getId())
                .name(customer1.getName())
                .email(customer1.getEmail())
                .password(customer1.getPassword())
                .privilege(customer1.getPrivilege())
                .build();

        Mockito.when(customerMapper.toEntity(customerDto)).thenReturn(customer1);
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer1));
        Mockito.when(customerMapper.toDto(customer1)).thenReturn(customerDto);
        //WHEN
        CustomerDto customerDtoFind = customerService.searchCustomerById(customerId);
        //THEN
        assertEquals(customerDtoFind.getId(), customerDto.getId());
        assertEquals(customerDtoFind.getName(), customerDto.getName());
        assertEquals(customerDtoFind.getEmail(), customerDto.getEmail());
        assertEquals(customerDtoFind.getPassword(), customerDto.getPassword());
        assertEquals(customerDtoFind.getPrivilege(), customerDto.getPrivilege());
    }

    @Test
    void GIVEN_CustomerDtoList_saved_WHEN_getAllCustomers_THEN_Should_return_ListOfCustomerDto() {
        // GIVEN
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPassword("password");
        customer1.setPrivilege("admin");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setPassword("password123");
        customer2.setPrivilege("user");

        CustomerDto customerDto1 = CustomerDto.builder()
                .id(customer1.getId())
                .name(customer1.getName())
                .email(customer1.getEmail())
                .password(customer1.getPassword())
                .privilege(customer1.getPrivilege())
                .build();
        CustomerDto customerDto2 = CustomerDto.builder()
                .id(customer2.getId())
                .name(customer2.getName())
                .email(customer2.getEmail())
                .password(customer2.getPassword())
                .privilege(customer2.getPrivilege())
                .build();

        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(customerDto1);
        customerDtoList.add(customerDto2);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerMapper.customerDtoList(customers)).thenReturn(customerDtoList);
        //WHEN
        List<CustomerDto> dtoListFind = customerService.getAllCustomers();
        //THEN
        assertEquals(2, dtoListFind.size());
        assertEquals(dtoListFind.size(), customerDtoList.size());
        for (int i = 0; i < dtoListFind.size(); i++) {
            assertEquals(dtoListFind.get(i).getId(), customerDtoList.get(i).getId());
            assertEquals(dtoListFind.get(i).getName(), customerDtoList.get(i).getName());
            assertEquals(dtoListFind.get(i).getEmail(), customerDtoList.get(i).getEmail());
            assertEquals(dtoListFind.get(i).getPassword(), customerDtoList.get(i).getPassword());
            assertEquals(dtoListFind.get(i).getPrivilege(), customerDtoList.get(i).getPrivilege());
        }
    }

    @Test
    void GIVEN_customerId_WHEN_deleteCustomer_THEN_should_delete_from_database() {
        //GIVEN
        Long customerId = 6L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jane Smith");
        customer.setEmail("jane.smith@example.com");
        customer.setPassword("password123");
        customer.setPrivilege("user");

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer));
        //WHEN
        customerRepository.delete(customer);
        //THEN
        Mockito.verify(customerRepository).delete(customerArgumentCaptor.capture());
        Customer customerDeleted = customerArgumentCaptor.getValue();
        assertEquals(customerDeleted.getId(), customer.getId());
        assertEquals(customerDeleted.getName(), customer.getName());
        assertEquals(customerDeleted.getEmail(), customer.getEmail());
        assertEquals(customerDeleted.getPassword(), customer.getPassword());
        assertEquals(customerDeleted.getPrivilege(), customer.getPrivilege());
    }

    @Test
    void GIVEN_customer_WHEN_updateCustomer_THEN_should_return_cutomserDto() {
        //GIVEN
        Long customerId = 2L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("David Beckham");
        customer.setEmail("david.Beckham.outlook.fr");
        customer.setPassword("password");
        customer.setPrivilege("collaborator");

        CustomerDto customerDto = CustomerDto.builder()
                .id(customerId)
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .privilege(customer.getPrivilege())
                .build();

        Mockito.when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerDto);
        //WHEN
        CustomerDto reseultat = customerService.updateCustomer(customerId, customerDto);
        //THEN
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer customerVerifiyed = customerArgumentCaptor.getValue();

        assertEquals(customerVerifiyed.getId(), reseultat.getId());
        assertEquals(customerVerifiyed.getName(), reseultat.getName());
        assertEquals(customerVerifiyed.getEmail(), reseultat.getEmail());
        assertEquals(customerVerifiyed.getPassword(), reseultat.getPassword());
        assertEquals(customerVerifiyed.getPrivilege(), reseultat.getPrivilege());
    }

    @Test
    void GIVEN_customer_WHEN_deleteCar_THEN_Should_Return_RuntimeException() {
        //GIVEN & WHEN
        Customer customer = new Customer();
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            customerService.deleteCustomer(null);
        });
        assertEquals("CUSTOMER NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_customerId_WHEN_findCustomerById_THEN_Should_Return_RuntimeException() {
        //GIVEN & WHEN
        Customer customer = new Customer();
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer));
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            customerService.searchCustomerById(null);
        });
        assertEquals("CUSTOMER NOT FOUND", e.getMessage());
    }
}
