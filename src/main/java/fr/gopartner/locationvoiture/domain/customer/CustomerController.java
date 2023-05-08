package fr.gopartner.locationvoiture.domain.customer;

import fr.gopartner.locationvoiture.CustomersApiDelegate;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController implements CustomersApiDelegate {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        CustomerDto dto = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCustomerById(Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        CustomerDto customerDto = customerService.searchCustomerById(id);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customerDtoList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomerById(Long id, CustomerDto customerDto) {
        CustomerDto dto = customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
