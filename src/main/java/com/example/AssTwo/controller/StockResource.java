package com.example.AssTwo.controller;


import com.example.AssTwo.dto.ProductDto;
import com.example.AssTwo.dto.StockDto;
import com.example.AssTwo.exception.BadRequestException;
import com.example.AssTwo.service.ProductService;
import com.example.AssTwo.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/stock")
public class StockResource {
    private final Logger log = LoggerFactory.getLogger(StockResource.class);

    //    @Autowired //@Autowired annotation is used for dependency injection.In spring boot application, all loaded beans are eligible for auto wiring to another bean. The annotation @Autowired in spring boot is used to auto-wire a bean into another bean.
    private com.example.AssTwo.service.StockService StockService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public StockResource(StockService StockService) {
        this.StockService = StockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDto>> getAllStocks() {
        return ResponseEntity.ok().body(StockService.getAllStocks()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getStockById(
            @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(StockService.getStockById(id));
    }

    @PostMapping
    public ResponseEntity<StockDto> createStock
            (@Valid @RequestBody StockDto StockDto) {
        if (StockDto.getId() != null) {
            log.error("Cannot have an ID {}", StockDto);
            throw new BadRequestException(StockResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity(StockService.createStock(StockDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDto> updateStock
            (@Valid @RequestBody StockDto StockDto
                    , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(StockService.updateStock(StockDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable(name = "id") long id) {
        StockService.deleteStockById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
