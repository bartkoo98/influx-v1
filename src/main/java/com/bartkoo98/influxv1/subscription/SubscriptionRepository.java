package com.bartkoo98.influxv1.subscription;

import com.bartkoo98.influxv1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUser(User user);
}
