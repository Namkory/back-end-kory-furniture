package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.OrderDto;
import kory.spring.com.bekoryfurniture.dto.OrderRequest;
import kory.spring.com.bekoryfurniture.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    OrderResponse createNewOrder(OrderRequest request);
    List<OrderDto> getAllOrders();
}
