package come.example.bookstore.seeders;

import come.example.bookstore.models.entities.Role;
import come.example.bookstore.models.entities.User;
import come.example.bookstore.models.enums.GenderEnum;
import come.example.bookstore.repositories.RoleRepository;
import come.example.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public void seeder(){
        Role role = roleRepository.findByName("ADMIN").orElse(null);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = User.builder()
                .id(1L)
                .username("manh12")
                .password(passwordEncoder.encode("123456"))
                .fullName("Manh")
                .phone("1234567890")
                .gender(GenderEnum.FEMALE)
                .dob(new Date())
                .address("Hà Nội")
                .roles(roles)
                .isActive(true)
                .build();
        userRepository.saveAll(Collections.singletonList(user));
    }
}
