package com.tb.eatclean.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSender implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String content, String type) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(message,"utf-8");
            mimeMessageHelper.setText("" +
                    "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n" +
                    "        <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                    "          <div style=\"border-bottom:1px solid #eee\">\n" +
                    "            <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Team Tuyet Nguyen</a>\n" +
                    "          </div>\n" +
                    "          <p style=\"font-size:1.1em\">Hi,</p>\n" +
                    "          <p>Thank you for choosing out store. Please follow here to active account. <a href=\"" + content + "\">Active</a></p>\n" +
                    "          <p style=\"font-size:0.9em;\">Regards,<br />Team 69</p>\n" +
                    "          <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                    "          <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                    "            <p>Team Tuyet Bong Inc</p>\n" +
                    "            <p>Tran Duy Hung, Hanoi, Vietnam</p>\n" +
                    "            <p>Vietnam</p>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>",true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Your passowrd");
            mimeMessageHelper.setFrom("longthan366@gmail.com");
            mailSender.send(message);
        }catch(MessagingException e){
            throw new IllegalStateException("Fail to send email");
        }
    }
}
