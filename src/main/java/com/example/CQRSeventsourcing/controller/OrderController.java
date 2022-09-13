package com.example.CQRSeventsourcing.controller;

import com.example.CQRSeventsourcing.command.ConfirmOrderCommand;
import com.example.CQRSeventsourcing.command.CreateOrderCommand;
import com.example.CQRSeventsourcing.command.ShipOrderCommand;
import com.example.CQRSeventsourcing.querymodel.FindAllOrderedProductsQuery;
import com.example.CQRSeventsourcing.querymodel.Order;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        String orderId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(orderId, "Dell Laptop"))
                .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderId)))
                .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderId)));
    }

    @GetMapping("/all-orders")
    public CompletableFuture<List<Order>> findAllOrders() {
        return queryGateway.query(new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(Order.class));
    }
}
