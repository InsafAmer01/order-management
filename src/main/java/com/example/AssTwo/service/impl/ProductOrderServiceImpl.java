package com.example.AssTwo.service.impl;


import com.example.AssTwo.dto.ProductOrderDto;
import com.example.AssTwo.entity.Order;
import com.example.AssTwo.entity.ProductOrder;
import com.example.AssTwo.exception.ResourceNotFoundException;
import com.example.AssTwo.service.OrderService;
import com.example.AssTwo.service.ProductOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * It is used to mark the class as a service provider.
 * So overall @Service annotation is used with classes that provide some business functionalities.
 * Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used

 */
@Service //To enable this class for component scanning
public class ProductOrderServiceImpl implements ProductOrderService {

    private com.example.AssTwo.repository.ProductOrderRepository ProductOrderRepository;

    public ProductOrderServiceImpl(com.example.AssTwo.repository.OrderRepository OrderRepository) {
        this.ProductOrderRepository = ProductOrderRepository;
    }

    @Override
    public ProductOrderDto createProductOrder(ProductOrderDto ProductOrderDto) {
        // convert DTO to entity
        ProductOrder ProductOrder = mapToEntity(ProductOrderDto);
        ProductOrder newProductOrder = ProductOrderRepository.save(ProductOrder);

        // convert entity to DTO
        ProductOrderDto ProductOrderResponse = mapToDTO(newProductOrder);
        return ProductOrderResponse;
    }

    @Override
    public List<ProductOrderDto> getAllProductOrders() {
        List<ProductOrder> ProductOrders = ProductOrderRepository.findAll();
        return ProductOrders.stream().map(ProductOrder -> mapToDTO(ProductOrder))
                .collect(Collectors.toList());
    }

    @Override
    public ProductOrderDto getProductOrderById(long id) {
        ProductOrder ProductOrder = ProductOrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("ProductOrder", "id", id));
        return mapToDTO(ProductOrder);
    }

    @Override
    public ProductOrderDto updateProductOrder(ProductOrderDto ProductOrderDto, long id) {
        // get Order by id from the database
        ProductOrder ProductOrder = ProductOrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("ProductOrder", "id", id));
        ProductOrder.setId(ProductOrderDto.getId());
        ProductOrder.setOrder(ProductOrderDto.getOrder());
        ProductOrder.setProduct(ProductOrderDto.getProduct());
        ProductOrder.setPrice(ProductOrderDto.getPrice());
        ProductOrder.setVat(ProductOrderDto.getVat());
        ProductOrder.setQuantity(ProductOrderDto.getQuantity());
        ProductOrder updatedProductOrder = ProductOrderRepository.save(ProductOrder);
        return mapToDTO(updatedProductOrder);
    }

    @Override
    public void deleteProductOrderById(long id) {
        // get Order by id from the database
        ProductOrder ProductOrder = ProductOrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("ProductOrder", "id", id));
        ProductOrderRepository.delete(ProductOrder);
    }

    // convert Entity into DTO
    private ProductOrderDto mapToDTO(ProductOrder ProductOrder) {
        ProductOrderDto ProductOrderDto = new ProductOrderDto();
        ProductOrderDto.setId(ProductOrder.getId());
        ProductOrderDto.setOrder(ProductOrder.getOrder());
        ProductOrderDto.setProduct(ProductOrder.getProduct());
        ProductOrderDto.setQuantity(ProductOrder.getQuantity());
        ProductOrderDto.setPrice(ProductOrder.getPrice());
        ProductOrderDto.setVat(ProductOrder.getVat());
        return ProductOrderDto;
    }

    // convert DTO to entity
    private ProductOrder mapToEntity(ProductOrderDto ProductOrderDto) {
        ProductOrder ProductOrder = new ProductOrder();
        ProductOrder.setId(ProductOrderDto.getId());
        ProductOrder.setId(ProductOrderDto.getId());
        ProductOrder.setOrder(ProductOrderDto.getOrder());
        ProductOrder.setProduct(ProductOrderDto.getProduct());
        ProductOrder.setPrice(ProductOrderDto.getPrice());
        ProductOrder.setVat(ProductOrderDto.getVat());
        ProductOrder.setQuantity(ProductOrderDto.getQuantity());
        return ProductOrder;
    }
}
