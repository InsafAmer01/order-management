package com.example.AssTwo.controller;


import com.example.AssTwo.dto.CustomerDto;
import com.example.AssTwo.exception.BadRequestException;
import com.example.AssTwo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    //    @Autowired //@Autowired annotation is used for dependency injection.In spring boot application, all loaded beans are eligible for auto wiring to another bean. The annotation @Autowired in spring boot is used to auto-wire a bean into another bean.
    private CustomerService CustomerService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public CustomerResource(CustomerService CustomerService) {
        this.CustomerService = CustomerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok().body(CustomerService.getAllCustomers()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(
            @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(CustomerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer
            (@Valid @RequestBody CustomerDto CustomerDto) {
        if (CustomerDto.getId() != null) {
            log.error("Cannot have an ID {}", CustomerDto);
            throw new BadRequestException(CustomerResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity(CustomerService.createCustomer(CustomerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer
            (@Valid @RequestBody CustomerDto CustomerDto
                    , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(CustomerService.updateCustomer(CustomerDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") long id) {
        CustomerService.deleteCustomerById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
