package com.example.bookstore.services.impl;

import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.CartItemRepository;
import com.example.bookstore.services.ICartService;
import com.example.bookstore.dto.requests.CartItemRequest;
import com.example.bookstore.dto.responses.CartItemResponse;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.CartItem;
import com.example.bookstore.models.entities.User;
import com.example.bookstore.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CartItemResponse> getCart() {
        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        return cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartItemResponse addToCart(CartItemRequest cartItemRequest) throws Exception {
        if(cartItemRequest.getBookId() == null){
            throw new Exception("Book is required.");
        }
        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Book book = bookRepository.findById(cartItemRequest.getBookId()).orElseThrow(() -> new Exception("Book not found."));
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        for(CartItem cartItem: cartItems){
            if(cartItem.getBook().equals(book)){
                cartItem.setQuantity(cartItem.getQuantity()+cartItemRequest.getQuantity());
                return modelMapper.map(cartItemRepository.save(cartItem), CartItemResponse.class);
            }
        }
        CartItem cartItem = CartItem.builder()
                .quantity(cartItemRequest.getQuantity())
                .book(book)
                .user(user)
                .build();
        return modelMapper.map(cartItemRepository.save(cartItem), CartItemResponse.class);
    }

    @Override
    @Transactional
    public CartItemResponse updateCartItem(long id, CartItemRequest cartItemRequest) throws Exception {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new Exception("CartItem not found."));
        cartItem.setQuantity(cartItemRequest.getQuantity());
        return modelMapper.map(cartItem, CartItemResponse.class);
    }

    @Override
    @Transactional
    public void deleteCartItem(long id) throws Exception {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(()-> new Exception("CartItem not found."));
        cartItemRepository.delete(cartItem);
    }
}
