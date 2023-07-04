package com.example.AssTwo.controller;


import com.example.AssTwo.dto.ProductDto;
import com.example.AssTwo.exception.BadRequestException;
import com.example.AssTwo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductResource {
    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    //    @Autowired //@Autowired annotation is used for dependency injection.In spring boot application, all loaded beans are eligible for auto wiring to another bean. The annotation @Autowired in spring boot is used to auto-wire a bean into another bean.
    private ProductService ProductService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public ProductResource(ProductService ProductService) {
        this.ProductService = ProductService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(ProductService.getAllProducts()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(ProductService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct
    (@Valid @RequestBody ProductDto ProductDto) {
        if (ProductDto.getId() != null) {
            log.error("Cannot have an ID {}", ProductDto);
            throw new BadRequestException(ProductResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity(ProductService.createProduct(ProductDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct
            (@Valid @RequestBody ProductDto ProductDto
                    , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(ProductService.updateProduct(ProductDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long id) {
        ProductService.deleteProductById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
