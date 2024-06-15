package com.example.bookstore.services;

import com.example.bookstore.dto.requests.CartItemRequest;
import com.example.bookstore.dto.responses.CartItemResponse;

import java.util.List;

public interface ICartService {
    List<CartItemResponse> getCart();
    CartItemResponse addToCart(CartItemRequest cartItemRequest) throws Exception;
    CartItemResponse updateCartItem(long id, CartItemRequest cartItemRequest) throws Exception;
    void deleteCartItem(long id) throws Exception;
}
