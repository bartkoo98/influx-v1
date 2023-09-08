package com.bartkoo98.influxv1.subscription;

import com.bartkoo98.influxv1.user.User;
import com.bartkoo98.influxv1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.bartkoo98.influxv1.subscription.SubscriptionMapper.mapToDto;
import static com.bartkoo98.influxv1.subscription.SubscriptionMapper.mapToEntity;

@Service
class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public void createSubscription() {
        Subscription subscription = new Subscription();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = ((User) authentication.getPrincipal()).getId();
        subscription.setUserId(userId);
        subscriptionRepository.save(subscription);
    }


    private Long findUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getId();
    }
}

