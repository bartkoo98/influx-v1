package com.bartkoo98.influxv1.subscription;

import com.bartkoo98.influxv1.user.User;
import lombok.Data;

@Data
class SubscriptionDto {
    private Long id;
    private Long userId;
}
