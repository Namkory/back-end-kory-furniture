package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.OrderDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderDTO createNewOrder(OrderDTO request);

    Page<OrderDTO> getAllOrder(int page, int size);

    OrderDTO getOrderById(Integer orderId);
}
