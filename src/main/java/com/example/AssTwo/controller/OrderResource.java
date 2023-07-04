package com.example.AssTwo.controller;


import com.example.AssTwo.dto.OrderDto;
import com.example.AssTwo.exception.BadRequestException;
import com.example.AssTwo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderResource {
    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    //    @Autowired //@Autowired annotation is used for dependency injection.In spring boot application, all loaded beans are eligible for auto wiring to another bean. The annotation @Autowired in spring boot is used to auto-wire a bean into another bean.
    private OrderService OrderService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public OrderResource(OrderService OrderService) {
        this.OrderService = OrderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok().body(OrderService.getAllOrders()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(
            @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(OrderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder
            (@Valid @RequestBody OrderDto OrderDto) {
        if (OrderDto.getId() != null) {
            log.error("Cannot have an ID {}", OrderDto);
            throw new BadRequestException(OrderResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity(OrderService.createOrder(OrderDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder
            (@Valid @RequestBody OrderDto OrderDto
                    , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(OrderService.updateOrder(OrderDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable(name = "id") long id) {
        OrderService.deleteOrderById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
