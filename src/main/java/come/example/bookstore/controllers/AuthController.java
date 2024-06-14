package come.example.bookstore.controllers;

import come.example.bookstore.dto.requests.LoginRequest;
import come.example.bookstore.dto.requests.UserRequest;
import come.example.bookstore.dto.responses.AuthResponse;
import come.example.bookstore.dto.responses.ExceptionResponse;
import come.example.bookstore.dto.responses.UserResponse;
import come.example.bookstore.models.entities.User;
import come.example.bookstore.security.JwtUtils;
import come.example.bookstore.security.UserPrincipal;
import come.example.bookstore.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest){
        try{
            AuthResponse authResponse = userService.register(userRequest);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(ExceptionResponse.builder().message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            User user =  ((UserPrincipal) authentication.getPrincipal()).getUser();
            String token  = jwtUtils.generateToken(user.getUsername(), new HashSet<>(user.getRoles()));
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ResponseEntity<>(AuthResponse.builder().token(token).user(userResponse).build(), HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
        }

    }
}
