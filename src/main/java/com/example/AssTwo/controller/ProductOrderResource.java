package com.example.AssTwo.controller;


import com.example.AssTwo.dto.ProductOrderDto;
import com.example.AssTwo.exception.BadRequestException;
import com.example.AssTwo.service.OrderService;
import com.example.AssTwo.service.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/productOrder")
public class ProductOrderResource {
    private final Logger log = LoggerFactory.getLogger(ProductOrderResource.class);

    //    @Autowired //@Autowired annotation is used for dependency injection.In spring boot application, all loaded beans are eligible for auto wiring to another bean. The annotation @Autowired in spring boot is used to auto-wire a bean into another bean.
    private ProductOrderService ProductOrderService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public ProductOrderResource(ProductOrderService ProductOrderService) {
        this.ProductOrderService = ProductOrderService;
    }

    @GetMapping
    public ResponseEntity<List<ProductOrderDto>> getAllProductOrders() {
        return ResponseEntity.ok().body(ProductOrderService.getAllProductOrders()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrderDto> getProductOrderById(
            @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(ProductOrderService.getProductOrderById(id));
    }

    @PostMapping
    public ResponseEntity<ProductOrderDto> createProductOrder
            (@Valid @RequestBody ProductOrderDto ProductOrderDto) {
        if (ProductOrderDto.getId() != null) {
            log.error("Cannot have an ID {}", ProductOrderDto);
            throw new BadRequestException(ProductOrderResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity(ProductOrderService.createProductOrder(ProductOrderDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrderDto> updateProductOrder
            (@Valid @RequestBody ProductOrderDto ProductOrderDto
                    , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(ProductOrderService.updateProductOrder(ProductOrderDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductOrder(@PathVariable(name = "id") long id) {
        ProductOrderService.deleteProductOrderById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
