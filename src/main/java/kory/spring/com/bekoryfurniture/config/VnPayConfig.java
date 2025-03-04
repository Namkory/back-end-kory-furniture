package kory.spring.com.bekoryfurniture.config;

import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Configuration
public class VnPayConfig {

    public static String generatePaymentUrl(String txnRef, String amount, String ipAddr, String returnUrl, String tmnCode, String hashSecret, String payUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", amount);
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Thanh toan don hang " + txnRef);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_IpAddr", ipAddr);
        params.put("vnp_CreateDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // Sắp xếp tham số theo thứ tự từ điển
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String value = params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append('&');
            }
        }

        hashData.setLength(hashData.length() - 1); // Xóa dấu '&' cuối
        query.setLength(query.length() - 1);

        // Tạo chữ ký
        String secureHash = hmacSHA512(hashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        return payUrl + "?" + query;
    }

    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC-SHA512", e);
        }
    }
}
