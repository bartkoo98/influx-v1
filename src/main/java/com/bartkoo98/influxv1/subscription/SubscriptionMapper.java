package com.bartkoo98.influxv1.subscription;

import org.modelmapper.ModelMapper;

class SubscriptionMapper {
    private static ModelMapper modelMapper;

    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static SubscriptionDto mapToDto(Subscription subscription) {
        return modelMapper.map(subscription, SubscriptionDto.class);
    }
    public static Subscription mapToEntity(SubscriptionDto subscriptionDto) {
        return modelMapper.map(subscriptionDto, Subscription.class);
    }
}
