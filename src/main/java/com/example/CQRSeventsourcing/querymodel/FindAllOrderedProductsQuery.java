package com.example.CQRSeventsourcing.querymodel;

import org.axonframework.queryhandling.QueryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAllOrderedProductsQuery {

    private final Map<String, Order> orders = new HashMap<>();

    @QueryHandler
    public List<Order> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orders.values());
    }
}
