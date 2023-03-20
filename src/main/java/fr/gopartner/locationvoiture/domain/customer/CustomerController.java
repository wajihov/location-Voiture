package fr.gopartner.locationvoiture.domain.customer;

import fr.gopartner.locationvoiture.CustomersApiDelegate;
import fr.gopartner.locationvoiture.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController implements CustomersApiDelegate {

    @Override
    public ResponseEntity<Void> createCustomer(CustomerDto customerDto) {
        return CustomersApiDelegate.super.createCustomer(customerDto);
    }

    @Override
    public ResponseEntity<Void> deleteCustomerById(Long id) {
        return CustomersApiDelegate.super.deleteCustomerById(id);
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        return CustomersApiDelegate.super.getCustomerById(id);
    }

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return CustomersApiDelegate.super.getCustomers();
    }

    @Override
    public ResponseEntity<Void> updateCustomerById(Long id, CustomerDto customerDto) {
        return CustomersApiDelegate.super.updateCustomerById(id, customerDto);
    }
}
