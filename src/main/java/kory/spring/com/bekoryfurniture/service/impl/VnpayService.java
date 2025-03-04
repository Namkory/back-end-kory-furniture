package kory.spring.com.bekoryfurniture.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kory.spring.com.bekoryfurniture.config.VnPayConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class VnpayService {

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Getter
    private String hashSecret = "1TEIMA5VO02KZ8AIWPQJMDIEFQXYTFB0";

    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;


    public String createPaymentUrl(Long orderId, int amount, String ipAddress) {
//        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_TxnRef = System.currentTimeMillis() + "_" + orderId;
        String vnp_Amount = String.valueOf(amount * 100);
        return VnPayConfig.generatePaymentUrl(vnp_TxnRef, vnp_Amount, ipAddress, returnUrl, tmnCode, hashSecret, payUrl);
    }

//    public boolean validateVnpaySignature(HttpServletRequest request) {
//        Map<String, String> params = new HashMap<>();
//        Enumeration<String> paramNames = request.getParameterNames();
//        while (paramNames.hasMoreElements()) {
//            String paramName = paramNames.nextElement();
//            params.put(paramName, request.getParameter(paramName));
//        }
//
//        String vnpSecureHash = params.remove("vnp_SecureHash"); // Lấy chữ ký gốc
//        params.remove("vnp_SecureHashType"); // Không cần dùng
//
//        // Sắp xếp tham số theo thứ tự từ điển
//        List<String> fieldNames = new ArrayList<>(params.keySet());
//        Collections.sort(fieldNames);
//
//        StringBuilder hashData = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String value = params.get(fieldName);
//            if (value != null) {
//                hashData.append(fieldName).append('=').append(URLDecoder.decode(value, StandardCharsets.UTF_8)).append('&');
//            }
//        }
//        hashData.setLength(hashData.length() - 1); // Xóa dấu '&' cuối
//
//        // Tạo chữ ký từ dữ liệu nhận được
//        String expectedSecureHash = VnPayConfig.hmacSHA512(hashSecret, hashData.toString());
//
//        return expectedSecureHash.equalsIgnoreCase(vnpSecureHash);
//    }

    /**
     * Lấy thông tin phản hồi từ VNPAY
     */
    public Map<String, String> getVnpayResponseParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            params.put(paramName, request.getParameter(paramName));
        }
        return params;
    }

}
