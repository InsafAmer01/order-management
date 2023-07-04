package com.example.AssTwo.service.impl;


import com.example.AssTwo.dto.OrderDto;
import com.example.AssTwo.entity.Order;
import com.example.AssTwo.exception.ResourceNotFoundException;
import com.example.AssTwo.repository.OrderRepository;
import com.example.AssTwo.repository.ProductRepository;
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
public class OrderServiceImpl implements OrderService {

    private com.example.AssTwo.repository.OrderRepository OrderRepository;

    public OrderServiceImpl(com.example.AssTwo.repository.OrderRepository OrderRepository) {
        this.OrderRepository = OrderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto OrderDto) {
        // convert DTO to entity
        Order Order = mapToEntity(OrderDto);
        Order newOrder = OrderRepository.save(Order);

        // convert entity to DTO
        OrderDto OrderResponse = mapToDTO(newOrder);
        return OrderResponse;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> Orders = OrderRepository.findAll();
        return Orders.stream().map(Order -> mapToDTO(Order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(long id) {
        Order Order = OrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToDTO(Order);
    }

    @Override
    public OrderDto updateOrder(OrderDto OrderDto, long id) {
        // get Order by id from the database
        Order Order = OrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        Order.setOrderedAt(OrderDto.getOrderedAt());
        Order.setId(OrderDto.getId());
        Order.setCustomerId(OrderDto.getCustomerId());
        Order updatedOrder = OrderRepository.save(Order);
        return mapToDTO(updatedOrder);
    }

    @Override
    public void deleteOrderById(long id) {
        // get Order by id from the database
        Order Order = OrderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        OrderRepository.delete(Order);
    }

    // convert Entity into DTO
    private OrderDto mapToDTO(Order Order) {
        OrderDto OrderDto = new OrderDto();
        OrderDto.setId(Order.getId());
        OrderDto.setOrderedAt(Order.getOrderedAt());
        OrderDto.setCustomerId(Order.getCustomerId());
        return OrderDto;
    }

    // convert DTO to entity
    private Order mapToEntity(OrderDto OrderDto) {
        Order Order = new Order();
        Order.setId(OrderDto.getId());
        Order.setOrderedAt(OrderDto.getOrderedAt());
        Order.setCustomerId(OrderDto.getCustomerId());
        return Order;
    }
}
