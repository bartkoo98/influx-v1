package com.bartkoo98.influxv1.subscription;

class SubscriptionMapper {

    public static SubscriptionDto mapToDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .build();
    }

}
