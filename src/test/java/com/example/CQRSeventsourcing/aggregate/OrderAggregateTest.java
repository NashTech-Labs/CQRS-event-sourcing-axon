package com.example.CQRSeventsourcing.aggregate;


import com.example.CQRSeventsourcing.command.CreateOrderCommand;
import com.example.CQRSeventsourcing.command.ShipOrderCommand;
import com.example.CQRSeventsourcing.event.OrderConfirmedEvent;
import com.example.CQRSeventsourcing.event.OrderCreatedEvent;
import com.example.CQRSeventsourcing.event.OrderShippedEvent;
import org.axonframework.messaging.annotation.MessageHandlerInvocationException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void OrderCreatedTest() {
        String orderId = UUID.randomUUID().toString();
        String productId = "Deluxe Chair";
        fixture.givenNoPriorActivity()
                .when(new CreateOrderCommand(orderId, productId))
                .expectEvents(new OrderCreatedEvent(orderId, productId));
    }

    @Test
    public void OrderShippedTest() {
        String orderId = UUID.randomUUID().toString();
        String productId = "Deluxe Chair";
        fixture.given(new OrderCreatedEvent(orderId, productId), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }

    @Test
    public void OrderShippedFailureTest() {
        String orderId = UUID.randomUUID().toString();
        String productId = "Deluxe Chair";
        fixture.given(new OrderCreatedEvent(orderId, productId))
                .when(new ShipOrderCommand(orderId))
                .expectException(MessageHandlerInvocationException.class);
    }
}
