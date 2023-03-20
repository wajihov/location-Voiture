package fr.gopartner.locationvoiture.domain.customer;

import fr.gopartner.locationvoiture.core.exception.CarReservationCustomerException;
import fr.gopartner.locationvoiture.core.rest.Codes;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        log.info("The Customer is added successfully {}", customer.getName());
        return customerMapper.toDto(customer);
    }

    private Customer rechercheCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new CarReservationCustomerException(Codes.ERR_CAR_NOT_FOUND));
    }

    public CustomerDto searchCustomerById(Long id) {
        Customer customer = rechercheCustomerById(id);
        log.info("The customer searched is {}", customer.getName());
        return customerMapper.toDto(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info("List {} Customers", customers.size());
        return customerMapper.customerDtoList(customers);
    }

    public void deleteCustomer(Long id) {
        Customer customer = rechercheCustomerById(id);
        customerRepository.delete(customer);
        log.info("The deletion of the customer name {} is successful", customer.getName());
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer.setId(id);
        customer = customerRepository.save(customer);
        log.info("The customer with id {} has been successfully modified", customer.getId());
        return customerMapper.toDto(customer);
    }
}
