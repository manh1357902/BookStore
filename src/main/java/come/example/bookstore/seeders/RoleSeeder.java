package come.example.bookstore.seeders;

import come.example.bookstore.models.entities.Role;
import come.example.bookstore.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;
    public void seeder(){
        Role role1 = Role.builder().id(1L).name("USER").build();
        Role role2 = Role.builder().id(2L).name("ADMIN").build();
        roleRepository.saveAll(Arrays.asList(role1,role2));
    }
}
