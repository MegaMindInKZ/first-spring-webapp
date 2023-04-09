package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Order;

public interface OrderRepository {
    Order save(Order order);
}
