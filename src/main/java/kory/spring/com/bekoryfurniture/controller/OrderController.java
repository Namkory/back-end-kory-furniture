package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.dto.OrderDto;
import kory.spring.com.bekoryfurniture.dto.OrderRequest;
import kory.spring.com.bekoryfurniture.dto.OrderResponse;
import kory.spring.com.bekoryfurniture.entity.Orders;
import kory.spring.com.bekoryfurniture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){
        OrderResponse response = orderService.createNewOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrders(){
        List<OrderDto> response = orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }
}
