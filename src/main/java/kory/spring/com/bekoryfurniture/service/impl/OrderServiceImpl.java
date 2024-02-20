package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.*;
import kory.spring.com.bekoryfurniture.entity.OrderDetail;
import kory.spring.com.bekoryfurniture.entity.Orders;
import kory.spring.com.bekoryfurniture.entity.Users;
import kory.spring.com.bekoryfurniture.repository.OrderDetailRepo;
import kory.spring.com.bekoryfurniture.repository.OrderRepo;
import kory.spring.com.bekoryfurniture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderResponse createNewOrder(OrderRequest request) {
        Orders order = new Orders();
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setGender(request.getGender());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        // Chuyen đổi từ String sang LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime orderDate = LocalDateTime.parse(request.getOrderDate(), formatter);
        order.setOrderDate(orderDate);

        order.setStatus(request.getStatus());
        order.setTotalMoney(request.getTotalMoney());
        orderRepo.save(order);

//        for (OrderProductsDto product : request.getListProducts()) {
//            System.out.println(product.getName());
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setName(product.getName());
//            orderDetail.setImage(product.getImage());
//            orderDetail.setPrice(product.getPrice());
////            orderDetail.setQuantity(Integer.parseInt(product.getQuantity()));
//            orderDetailRepo.save(orderDetail);
//        }

        OrderResponse response = new OrderResponse();
        response.setMessage("Create new Order successfully");

        return response;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> entityOrders = orderRepo.findAll();
        List<OrderDto> response = new ArrayList<>();
        for(int i = 0; i < entityOrders.size(); i++) {
            OrderDto orderDto = modelMapper.map(entityOrders.get(i), OrderDto.class);
            response.add(orderDto);
        }
        return response;
    }
}
