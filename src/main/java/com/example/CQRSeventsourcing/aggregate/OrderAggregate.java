package com.example.CQRSeventsourcing.aggregate;

import com.example.CQRSeventsourcing.command.ConfirmOrderCommand;
import com.example.CQRSeventsourcing.command.CreateOrderCommand;
import com.example.CQRSeventsourcing.command.ShipOrderCommand;
import com.example.CQRSeventsourcing.event.OrderConfirmedEvent;
import com.example.CQRSeventsourcing.event.OrderCreatedEvent;
import com.example.CQRSeventsourcing.event.OrderShippedEvent;
import com.example.CQRSeventsourcing.exception.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(), command.getProductId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (orderConfirmed) {
            return;
        }
        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws UnconfirmedOrderException {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException("Error occurred while confirming the oder!");
        }
        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

    protected OrderAggregate() {
    }
}
