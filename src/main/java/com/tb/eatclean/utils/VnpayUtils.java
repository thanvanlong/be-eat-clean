package com.tb.eatclean.utils;

import com.tb.eatclean.dto.VnpayCreatePaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpayUtils {
    @Value("${VNP_TMNCODE}")
    private String VNP_TMNCODE;

    @Value("${VNP_HASH_SECRET}")
    private String VNP_HASH_SECRET;

    @Value("${VNP_URL}")
    private String VNP_URL;

    @Value("${VNP_RETURN_URL}")
    private String VNP_RETURN_URL;

    public VnpayUtils() {
    }


    public String createPayment(VnpayCreatePaymentDto createPaymentDto) throws Exception{
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = createPaymentDto.getOrderId();
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNP_TMNCODE;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version",vnp_Version);
        vnp_Params.put("vnp_Command",vnp_Command);
        vnp_Params.put("vnp_TmnCode",vnp_TmnCode);
        vnp_Params.put("vnp_Amount",String.valueOf(createPaymentDto.getAmount() * 100));
        vnp_Params.put("vnp_CurrCode","VND");
        if(createPaymentDto.getBankCode() !=null && !createPaymentDto.getBankCode().isEmpty()) {
            vnp_Params.put("vnp_BankCode", createPaymentDto.getBankCode());
        }

        vnp_Params.put("vnp_TxnRef",vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + createPaymentDto.getOrderId());
        vnp_Params.put("vnp_OrderType", "other");

        String locale = createPaymentDto.getLanguage() != null && !createPaymentDto.getLanguage().isEmpty() ? createPaymentDto.getLanguage() : "vn";

        vnp_Params.put("vnp_Locale",locale);
        vnp_Params.put("vnp_ReturnUrl",VNP_RETURN_URL); // Replace with your actual value.
        vnp_Params.put("vnp_IpAddr",vnp_IpAddr);

        // Calendar and Date formatting
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate",vnp_CreateDate);
        cld.add(Calendar.MINUTE,15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate",vnp_ExpireDate);

        vnp_Params = sortMap(vnp_Params);

        String signData = joinMapToString(vnp_Params, "&");
        String vnp_SecureHash = hmacSHA512(VNP_HASH_SECRET, signData);
        vnp_Params.put("vnp_SecureHash",vnp_SecureHash);

        String queryUrl = joinMapToString(vnp_Params, "&");
        String paymentUrl = VNP_URL + "?" + queryUrl;

        return paymentUrl;
    }

    private Map<String, String> sortMap(Map<String, String> map) {
        TreeMap<String, String> sortedMap = new TreeMap<>(map);
        return sortedMap;
    }


    private String joinMapToString(Map<String, String> map, String delimiter) throws UnsupportedEncodingException {
        List<String> keyValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            keyValuePairs.add(key + "=" + URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
        }
        return String.join(delimiter, keyValuePairs);
    }


    public String encodeValue(String value) {
        return value.replace(" ", "+");
    }

    public String signData(String data, String secretKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest((data + secretKey).getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xFF & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception or throw it as needed
            return null;
        }
    }

    public String hmacSHA512(String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

}
