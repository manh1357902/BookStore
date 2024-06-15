package com.example.bookstore.controllers;

import com.example.bookstore.dto.requests.CartItemRequest;
import com.example.bookstore.dto.responses.CartItemResponse;
import com.example.bookstore.dto.responses.ExceptionResponse;
import com.example.bookstore.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final ICartService cartService;
    @GetMapping
    public ResponseEntity<?> getCart(){
        List<CartItemResponse> cartItemResponses = cartService.getCart();
        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemRequest cartItemRequest){
        try{
            CartItemResponse cartItemResponse = cartService.addToCart(cartItemRequest);
            return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable("id") Long id,@RequestBody CartItemRequest cartItemRequest){
        try{
            CartItemResponse cartItemResponse = cartService.updateCartItem(id, cartItemRequest);
            return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long id){
        try{
            cartService.deleteCartItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
