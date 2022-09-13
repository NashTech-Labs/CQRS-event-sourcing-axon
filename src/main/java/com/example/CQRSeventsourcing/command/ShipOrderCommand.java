package com.example.CQRSeventsourcing.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ShipOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
}
