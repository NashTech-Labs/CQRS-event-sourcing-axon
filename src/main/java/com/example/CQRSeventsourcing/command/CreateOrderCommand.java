package com.example.CQRSeventsourcing.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String productId;
}
