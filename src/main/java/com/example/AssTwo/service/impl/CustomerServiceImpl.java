package com.example.AssTwo.service.impl;


import com.example.AssTwo.dto.CustomerDto;
import com.example.AssTwo.entity.Customer;
import com.example.AssTwo.exception.ResourceNotFoundException;
import com.example.AssTwo.service.CustomerService;
import com.example.AssTwo.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * It is used to mark the class as a service provider.
 * So overall @Service annotation is used with classes that provide some business functionalities.
 * Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used

 */
@Service //To enable this class for component scanning
public class CustomerServiceImpl implements CustomerService {

    private com.example.AssTwo.repository.CustomerRepository CustomerRepository;

    public CustomerServiceImpl(com.example.AssTwo.repository.CustomerRepository CustomerRepository) {
        this.CustomerRepository = CustomerRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto CustomerDto) {
        // convert DTO to entity
        Customer Customer = mapToEntity(CustomerDto);
        Customer newCustomer = CustomerRepository.save(Customer);

        // convert entity to DTO
        CustomerDto CustomerResponse = mapToDTO(newCustomer);
        return CustomerResponse;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> Customers = CustomerRepository.findAll();
        return Customers.stream().map(Customer -> mapToDTO(Customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(long id) {
        Customer Customer = CustomerRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return mapToDTO(Customer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto CustomerDto, long id) {
        // get Customer by id from the database
        Customer Customer = CustomerRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        Customer.setId(CustomerDto.getId());
        Customer.setBirthDate(CustomerDto.getBirthDate());
        Customer.setFirstName(CustomerDto.getFirstName());
        Customer.setLastName(CustomerDto.getLastName());

        Customer updatedCustomer = CustomerRepository.save(Customer);
        return mapToDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomerById(long id) {
        // get Customer by id from the database
        Customer Customer = CustomerRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        CustomerRepository.delete(Customer);
    }

    // convert Entity into DTO
    private CustomerDto mapToDTO(Customer Customer) {
        CustomerDto CustomerDto = new CustomerDto();
        CustomerDto.setId(Customer.getId());
        CustomerDto.setBirthDate(Customer.getBirthDate());
        CustomerDto.setFirstName(Customer.getFirstName());
        CustomerDto.setLastName(Customer.getLastName());

        return CustomerDto;
    }

    // convert DTO to entity
    private Customer mapToEntity(CustomerDto CustomerDto) {
        Customer Customer = new Customer();
        Customer.setId(CustomerDto.getId());
        Customer.setBirthDate(CustomerDto.getBirthDate());
        Customer.setFirstName(CustomerDto.getFirstName());
        Customer.setLastName(CustomerDto.getLastName());

        return Customer;
    }
}
