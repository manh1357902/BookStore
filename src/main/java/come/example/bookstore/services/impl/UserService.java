package come.example.bookstore.services.impl;

import come.example.bookstore.dto.requests.UserRequest;
import come.example.bookstore.dto.responses.AuthResponse;
import come.example.bookstore.dto.responses.UserResponse;
import come.example.bookstore.models.entities.Role;
import come.example.bookstore.models.entities.User;
import come.example.bookstore.repositories.RoleRepository;
import come.example.bookstore.repositories.UserRepository;
import come.example.bookstore.security.JwtUtils;
import come.example.bookstore.security.UserPrincipal;
import come.example.bookstore.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(@Lazy PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found by username: "+ username));
        return new UserPrincipal(user);
    }
    public AuthResponse register(UserRequest userRequest) throws Exception {
        if(userRepository.existsByUsernameAndIsActive(userRequest.getUsername(), true)){
            throw new Exception("Username exists");
        }
        Role role = roleRepository.findByName("USER").orElseThrow(()-> new Exception("Role not found.") );
        if (userRepository.existsByUsernameAndIsActive(userRequest.getUsername(), false)){
            User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow(()->new Exception("User not found."));
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setFullName(userRequest.getFullName());
            user.setPhone(userRequest.getPhone());
            user.setGender(userRequest.getGender());
            user.setDob(userRequest.getDob());
            user.setAddress(userRequest.getAddress());
            user.setRoles(new ArrayList<>(Collections.singletonList(role)));
            user.setIsActive(true);
            user = userRepository.save(user);
            return AuthResponse.builder().token(jwtUtils.generateToken(user.getUsername(), new HashSet<>(user.getRoles()))).user(modelMapper.map(user, UserResponse.class)).build();
        }else{
            User user = User.builder()
                    .username(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .fullName(userRequest.getFullName())
                    .phone(userRequest.getPhone())
                    .gender(userRequest.getGender())
                    .dob(userRequest.getDob())
                    .address(userRequest.getAddress())
                    .roles(new ArrayList<>(Collections.singletonList(role)))
                    .isActive(true)
                    .build();
            user = userRepository.save(user);
            return AuthResponse.builder().token(jwtUtils.generateToken(user.getUsername(), new HashSet<>(user.getRoles()))).user(modelMapper.map(user, UserResponse.class)).build();
        }
    }
}
