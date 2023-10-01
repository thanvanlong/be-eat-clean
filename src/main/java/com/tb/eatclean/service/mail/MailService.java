package com.tb.eatclean.service.mail;

public interface MailService {
    void send(String to, String content, String type);
}
