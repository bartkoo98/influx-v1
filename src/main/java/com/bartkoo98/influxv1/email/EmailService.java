package com.bartkoo98.influxv1.email;

import com.bartkoo98.influxv1.subscription.Subscription;
import com.bartkoo98.influxv1.subscription.SubscriptionRepository;
import com.bartkoo98.influxv1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private SubscriptionRepository subscriptionRepository;


// todo asynchroniczna obsluga wysylania maili
    public void sendNotificationAboutNewArticle(List<String> subscribersEmails, String articleTitle, String articleId) {
        try{
            for(String to : subscribersEmails) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setSubject("New article on influxWEB!");
                mailMessage.setFrom(fromEmail);
                mailMessage.setTo(to);
                mailMessage.setText(getNotificationEmailMessage(articleTitle, articleId));
                javaMailSender.send(mailMessage);
            }
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

    public List<String> getEmailsOfSubscribers() {
        List<Subscription> subscribers = subscriptionRepository.findAll();
        return subscribers.stream().map(subscription -> subscription.getUser().getEmail())
                .toList();
    }

}
