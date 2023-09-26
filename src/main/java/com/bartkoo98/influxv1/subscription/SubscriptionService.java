package com.bartkoo98.influxv1.subscription;

import com.bartkoo98.influxv1.exception.APIException;
import com.bartkoo98.influxv1.user.User;
import com.bartkoo98.influxv1.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

//    public void createSubscription() {
//        Subscription subscription = new Subscription();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        subscription.setUser(userRepository.findByUsername(name).orElseThrow());
//        subscriptionRepository.save(subscription);
//    }

    public void createSubscription() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);

            if (user != null) {
                Subscription subscription = new Subscription();
                subscription.setUser(user);
                subscriptionRepository.save(subscription);
            } else {
                throw new APIException("Something went wrong! Username " + username + " not exist.");
            }
        }
    }

}

