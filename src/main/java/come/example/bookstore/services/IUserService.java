package come.example.bookstore.services;

import come.example.bookstore.dto.requests.UserRequest;
import come.example.bookstore.dto.responses.AuthResponse;

public interface IUserService {
    AuthResponse register(UserRequest userRequest) throws Exception;
}
