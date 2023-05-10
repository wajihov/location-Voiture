package fr.gopartner.locationvoiture.customer;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.customer.Customer;
import fr.gopartner.locationvoiture.domain.customer.CustomerMapper;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import fr.gopartner.locationvoiture.domain.reservation.ReservationMapper;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import fr.gopartner.locationvoiture.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerMapperTest {

    @MockBean
    private ReservationMapper reservationMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void GIVEN_Customer_WHEN_ToDto_THEN_should_return_CustomerDto() {
        // GIVEN
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password");
        customer.setPrivilege("admin");

        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setCareAndSupport("Full");
        reservation1.setDiscount("10%");
        reservation1.setStartDate(LocalDate.of(2023, 5, 15));
        reservation1.setEndDate(LocalDate.of(2023, 5, 20));
        reservation1.setStatus("Active");
        reservation1.setDateReservation(LocalDate.now());
        List<Reservation> reservations = new ArrayList<>();

        customer.setReservations(reservations);
        // WHEN
        CustomerDto customerDto = customerMapper.toDto(customer);
        // THEN
        assertEquals(customer.getId(), customerDto.getId());
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
        assertEquals(customer.getPassword(), customerDto.getPassword());
        assertEquals(customer.getPrivilege(), customerDto.getPrivilege());
    }

    @Test
    void GIVEN_CustomerDto_WHEN_ToEntity_THEN_should_return_Customer() {
        // GIVEN
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPassword("password");
        customerDto.setPrivilege("admin");
        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .careAndSupport("Fantastic Car")
                .discount("3%")
                .dateReservation(LocalDate.of(2023, 1, 4))
                .endDate(LocalDate.of(2023, 1, 15))
                .startDate(LocalDate.of(2023, 1, 5))
                .build();
        List<ReservationDto> dtoList = new ArrayList<>();
        dtoList.add(reservationDto);

        customerDto.setReservations(dtoList);
        // WHEN
        Customer customer = customerMapper.toEntity(customerDto);
        // THEN
        assertEquals(customerDto.getId(), customer.getId());
        assertEquals(customerDto.getName(), customer.getName());
        assertEquals(customerDto.getEmail(), customer.getEmail());
        assertEquals(customerDto.getPassword(), customer.getPassword());
        assertEquals(customerDto.getPrivilege(), customer.getPrivilege());
    }

    @Test
    void GIVEN_customers_WHEN_customerDtoList_THEN_Return_ListOfCustomerDto() {
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

        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setCareAndSupport("Full");
        reservation1.setDiscount("10%");
        reservation1.setStartDate(LocalDate.of(2023, 5, 15));
        reservation1.setEndDate(LocalDate.of(2023, 5, 20));
        reservation1.setStatus("Active");
        reservation1.setDateReservation(LocalDate.now());

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCareAndSupport("Basic");
        reservation2.setDiscount("5%");
        reservation2.setStartDate(LocalDate.of(2023, 6, 1));
        reservation2.setEndDate(LocalDate.of(2023, 6, 7));
        reservation2.setStatus("Pending");
        reservation2.setDateReservation(LocalDate.now());

        List<Reservation> reservationList1 = new ArrayList<>();
        reservationList1.add(reservation1);

        List<Reservation> reservationList2 = new ArrayList<>();
        reservationList2.add(reservation2);
        customer1.setReservations(reservationList1);
        customer2.setReservations(reservationList2);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        // WHEN
        List<CustomerDto> customerDtoList = customerMapper.customerDtoList(customers);
        // THEN
        assertEquals(customers.size(), customerDtoList.size());
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            CustomerDto customerDto = customerDtoList.get(i);

            assertEquals(customer.getId(), customerDto.getId());
            assertEquals(customer.getName(), customerDto.getName());
            assertEquals(customer.getEmail(), customerDto.getEmail());
            assertEquals(customer.getPassword(), customerDto.getPassword());
            assertEquals(customer.getPrivilege(), customerDto.getPrivilege());
            for (int j = 0; j < customerDto.getReservations().size(); j++) {
                assertEquals(customer.getReservations().get(j).getId(), customerDto.getReservations().get(j).getId());
                assertEquals(customer.getReservations().get(j).getCareAndSupport(), customerDto.getReservations().get(j).getCareAndSupport());
                assertEquals(customer.getReservations().get(j).getDiscount(), customerDto.getReservations().get(j).getDiscount());
                assertEquals(customer.getReservations().get(j).getStartDate(), customerDto.getReservations().get(j).getStartDate());
                assertEquals(customer.getReservations().get(j).getEndDate(), customerDto.getReservations().get(j).getEndDate());
                assertEquals(customer.getReservations().get(j).getStatus(), customerDto.getReservations().get(j).getStatus());
                assertEquals(customer.getReservations().get(j).getDateReservation(), customerDto.getReservations().get(j).getDateReservation());
            }
        }
    }

    @Test
    void GIVEN_Customer_WHEN_toCustomerDto_THEN_should_return_Exception() {
        // GIVEN
        Customer customer = null;
        // WHEN & THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            customerMapper.toDto(customer);
        });
        assertEquals("CUSTOMER NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_CustomerDto_WHEN_toEntityCustomer_THEN_should_return_Exception() {
        // GIVEN
        CustomerDto customerDto = null;
        // WHEN & THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            customerMapper.toEntity(customerDto);
        });
        assertEquals("CUSTOMER NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_Customers_WHEN_toListCustomerDto_THEN_should_return_Exception() {
        //GIVEN
        List<Customer> customers = null;
        //WHEN && THEN
        RuntimeException e = assertThrows(CarReservationCustomerException.class, () -> {
            customerMapper.customerDtoList(customers);
        });
        assertEquals("CUSTOMERS NOT FOUND", e.getMessage());
    }

}
