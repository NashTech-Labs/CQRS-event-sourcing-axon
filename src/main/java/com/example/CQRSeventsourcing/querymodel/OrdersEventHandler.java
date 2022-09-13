package com.example.CQRSeventsourcing.querymodel;

import com.example.CQRSeventsourcing.event.OrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrdersEventHandler {

    private final Map<String, Order> orders = new HashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        orders.put(orderId, new Order(orderId, event.getProductId()));
    }
}
