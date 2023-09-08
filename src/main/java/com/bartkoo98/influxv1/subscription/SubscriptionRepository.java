package com.bartkoo98.influxv1.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserId(Long userId);
}
