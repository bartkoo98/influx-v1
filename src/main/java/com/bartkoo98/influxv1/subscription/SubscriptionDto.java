package com.bartkoo98.influxv1.subscription;

import com.bartkoo98.influxv1.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class SubscriptionDto {
    private Long id;
    private Long userId;
}
