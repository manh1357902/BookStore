package com.example.bookstore.services;

import com.example.bookstore.dto.requests.OrderRequest;
import com.example.bookstore.dto.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(List<Long> bookIds, OrderRequest orderRequest) throws Exception;
}
