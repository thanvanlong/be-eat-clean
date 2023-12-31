package com.tb.eatclean.utils;


import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.carts.Cart;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@Service()
public class StringUtils {
    public static String slugify(String input) {
        // Bước 1: Loại bỏ dấu và chuyển thành chữ thường
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        // Bước 2: Loại bỏ các ký tự không phải chữ cái hoặc số, thay thế bằng dấu gạch ngang
        String slug = withoutDiacritics.replaceAll("[-+^]*", "").replaceAll("\\s+", "-");

        return slug;
    }

    public static String removeAccents(String input){
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    public static String uuidFileName(String input){
        String uuid = UUID.randomUUID().toString();
        return uuid + slugify(input);
    }

}
