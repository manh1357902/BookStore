package com.example.bookstore.services;

import com.example.bookstore.dto.requests.UserRequest;
import com.example.bookstore.dto.responses.AuthResponse;

public interface IUserService {
    AuthResponse register(UserRequest userRequest) throws Exception;
}
