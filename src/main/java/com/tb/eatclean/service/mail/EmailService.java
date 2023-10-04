package com.tb.eatclean.service.mail;

public interface EmailService {
    void send(String to, String content, String type);
}
