package kory.spring.com.bekoryfurniture.controller;

import com.stripe.exception.StripeException;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import kory.spring.com.bekoryfurniture.dto.request.StripeRequest;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.StripeResponse;
import kory.spring.com.bekoryfurniture.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private OrderRepo orderRepo;

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
}
