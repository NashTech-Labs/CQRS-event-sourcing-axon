package com.example.CQRSeventsourcing.event;

import lombok.Data;

@Data
public class OrderShippedEvent {

    private final String orderId;
}
