package com.example.AssTwo.service.impl;


import com.example.AssTwo.dto.StockDto;
import com.example.AssTwo.entity.Stock;
import com.example.AssTwo.exception.ResourceNotFoundException;
import com.example.AssTwo.service.StockService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It is used to mark the class as a service provider.
 * So overall @Service annotation is used with classes that provide some business functionalities.
 * Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used

 */
@Service //To enable this class for component scanning
public class StockServiceImpl implements StockService {

    private com.example.AssTwo.repository.StockRepository StockRepository;

    public StockServiceImpl(com.example.AssTwo.repository.StockRepository StockRepository) {
        this.StockRepository = StockRepository;
    }

    @Override
    public StockDto createStock(StockDto StockDto) {
        // convert DTO to entity
        Stock Stock = mapToEntity(StockDto);
        Stock newStock = StockRepository.save(Stock);

        // convert entity to DTO
        StockDto StockResponse = mapToDTO(newStock);
        return StockResponse;
    }

    @Override
    public List<StockDto> getAllStocks() {
        List<Stock> Stocks = StockRepository.findAll();
        return Stocks.stream().map(Stock -> mapToDTO(Stock))
                .collect(Collectors.toList());
    }

    @Override
    public StockDto getStockById(long id) {
        Stock Stock = StockRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Stock", "id", id));
        return mapToDTO(Stock);

    }

    private StockDto mapToDTO(Stock Stock) {
        StockDto StockDto = new StockDto();
        StockDto.setId(Stock.getId());
        StockDto.setProductId(Stock.getProductId());
        StockDto.setQuantity(Stock.getQuantity());
        StockDto.setUpdatedAt(Stock.getUpdatedAt());

        return StockDto;
    }

    @Override
    public StockDto updateStock(StockDto StockDto, long id) {
        // get Stock by id from the database
        Stock Stock = StockRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Stock", "id", id));
        Stock.setId(StockDto.getId());
        Stock.setUpdatedAt(StockDto.getUpdatedAt());
        Stock.setQuantity(StockDto.getQuantity());
        Stock.setProductId(StockDto.getProductId());

        Stock updatedStock = StockRepository.save(Stock);
        return mapToDTO(updatedStock);
    }

    @Override
    public void deleteStockById(long id) {
        // get Stock by id from the database
        Stock Stock = StockRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Stock", "id", id));
        StockRepository.delete(Stock);
    }

    private Stock mapToEntity(StockDto StockDto) {
        Stock Stock = new Stock();
        Stock.setId(StockDto.getId());
        Stock.setProductId(StockDto.getProductId());
        Stock.setQuantity(StockDto.getQuantity());
        Stock.setUpdatedAt(StockDto.getUpdatedAt());
        return Stock;
    }
}
