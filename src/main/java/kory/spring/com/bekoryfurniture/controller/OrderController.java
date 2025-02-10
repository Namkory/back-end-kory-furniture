package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.OrderDTO;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    ApiResponse<OrderDTO> createNewOrder(@RequestBody @Valid OrderDTO request){
        OrderDTO response = orderService.createNewOrder(request);
        ApiResponse<OrderDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Page<OrderDTO>> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<OrderDTO> orderResponsessPage = orderService.getAllOrder(page, size);
        return new ResponseEntity<>(orderResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ApiResponse<OrderDTO> getOrderById(@PathVariable("id") Integer orderId) {
        OrderDTO response = orderService.getOrderById(orderId);
        ApiResponse<OrderDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }
}
