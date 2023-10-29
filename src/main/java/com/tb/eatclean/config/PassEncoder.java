package com.tb.eatclean.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
//@Configuration
public class PassEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        System.out.println("sssdsdd" + charSequence);
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println("sssssss " + charSequence + " " + s);
        return charSequence.toString().equals(s);
    }

    private static final PassEncoder mInstance = new PassEncoder();

    public  static PassEncoder getInstance() {
        return mInstance;
    }

    public PassEncoder() {
    }
}