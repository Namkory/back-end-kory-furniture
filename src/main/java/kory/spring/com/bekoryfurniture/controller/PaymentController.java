package kory.spring.com.bekoryfurniture.controller;

import com.stripe.exception.StripeException;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kory.spring.com.bekoryfurniture.config.VnPayConfig;
import kory.spring.com.bekoryfurniture.dto.request.StripeRequest;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.StripeResponse;
import kory.spring.com.bekoryfurniture.repository.OrderRepo;
import kory.spring.com.bekoryfurniture.service.impl.VnpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private VnpayService vnpayService;

    @PostMapping("/create-payment-intent")
    public ApiResponse<StripeResponse> createPaymentIntent(@RequestBody StripeRequest request) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount() * 100L)
                .putMetadata("orderId", String.valueOf(request.getOrderId()))
                .setCurrency("usd")
                .setReceiptEmail(request.getEmail())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams
                                .AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();
        PaymentIntent intent = PaymentIntent.create(params);

        StripeResponse stripeResponse = new StripeResponse();
        stripeResponse.setIntentId(intent.getId());
        stripeResponse.setClientSecret(intent.getClientSecret());

        ApiResponse<StripeResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Create payment intent successful");
        response.setResult(stripeResponse);

        return response;
    }

    @PostMapping("/create")
    public Map<String, String> createPayment(@RequestParam Long orderId, @RequestParam int amount, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String paymentUrl = vnpayService.createPaymentUrl(orderId, amount, ipAddress);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }

//    @GetMapping("/vnpay-callback")
//    public Map<String, Object> paymentCallback(HttpServletRequest request) {
////        boolean isValidSignature = vnpayService.validateVnpaySignature(request);
//        Map<String, String> receivedParams = vnpayService.getVnpayResponseParams(request);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("receivedParams", receivedParams);
//
//        return response;
//    }

@GetMapping("/vnpay-callback")
public ResponseEntity<Void> paymentCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Lấy danh sách tham số trả về từ VNPAY
    Map<String, String> receivedParams = vnpayService.getVnpayResponseParams(request);

    // Lấy mã phản hồi từ VNPAY
    String vnp_ResponseCode = receivedParams.get("vnp_ResponseCode");
    boolean isSuccess = "00".equals(vnp_ResponseCode);

    // Redirect về giao diện React với kết quả
    String redirectUrl = "http://localhost:3000/pay?status=" + (isSuccess ? "success" : "failed");
    response.sendRedirect(redirectUrl);

    return ResponseEntity.status(HttpStatus.FOUND).build(); // 302 Redirect
}
}
