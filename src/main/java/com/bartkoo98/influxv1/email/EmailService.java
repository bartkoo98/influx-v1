package com.bartkoo98.influxv1.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.bartkoo98.influxv1.email.EmailUtils.getNotificationEmailMessage;
import static com.bartkoo98.influxv1.email.EmailUtils.getRegistrationEmailMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;


    public void sendNotificationAboutNewArticle(String to, String articleTitle, String articleId) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("New article on influxWEB!");
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText(getNotificationEmailMessage(articleTitle, articleId));
            javaMailSender.send(mailMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendRegisterVerificationMessage(String name, String to, String token) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("New article on influxWEB!");
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText(getRegistrationEmailMessage(name, host, token));
            javaMailSender.send(mailMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
