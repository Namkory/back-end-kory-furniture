package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.OrderDTO;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/order")
@Tag(name = "Order Controller")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Add order", description = "API create new order")
    @PostMapping
    ApiResponse<OrderDTO> createNewOrder(@RequestBody @Valid OrderDTO request) throws MessagingException {
        log.info("Request add order, {}",
                request.toString());
        OrderDTO response = orderService.createNewOrder(request);
        ApiResponse<OrderDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get order list per page", description = "Return order by page and size")
    @GetMapping()
    public ResponseEntity<Page<OrderDTO>> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of orders");
        Page<OrderDTO> orderResponsessPage = orderService.getAllOrder(page, size);
        return new ResponseEntity<>(orderResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get order detail", description = "API get order detail")
    @GetMapping("/{id}")
    ApiResponse<OrderDTO> getOrderById(@PathVariable("id") Integer orderId) {
        log.info("Request get detail admin by orderId={}", orderId);
        OrderDTO response = orderService.getOrderById(orderId);
        ApiResponse<OrderDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }
}
