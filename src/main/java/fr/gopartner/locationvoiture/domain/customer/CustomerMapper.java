package fr.gopartner.locationvoiture.domain.customer;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.core.utils.CollectionUtils;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import fr.gopartner.locationvoiture.domain.reservation.ReservationMapper;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    private final ReservationMapper reservationMapper;

    public CustomerMapper(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    public CustomerDto toDto(Customer customer) {
        if (customer == null) {
            throw new CarReservationCustomerException(Codes.ERR_CUSTOMER_NOT_FOUND);
        }
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .privilege(customer.getPrivilege())
                .reservations(reservationMapper.reservationDtoList(customer.getReservations()))
                .build();
    }

    public Customer toEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            throw new CarReservationCustomerException(Codes.ERR_CUSTOMER_NOT_FOUND);
        }
        return Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .privilege(customerDto.getPrivilege())
                .reservations(reservationMapper.reservationList(customerDto.getReservations()))
                .build();
    }

    public List<CustomerDto> customerDtoList(List<Customer> customers) {
        if (CollectionUtils.isNullOrEmpty(customers)) {
            throw new CarReservationCustomerException(Codes.ERR_CUSTOMERS_NOT_FOUND);
        }
        return customers.stream().map(this::toDto).collect(Collectors.toList());
    }
}
