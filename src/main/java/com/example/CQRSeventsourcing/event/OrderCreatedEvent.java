package com.example.CQRSeventsourcing.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private final String orderId;
    private final String productId;
}
