package come.example.bookstore.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final RoleSeeder roleSeeder;
    private final UserSeeder userSeeder;
    @Override
    public void run(String... args) throws Exception {
        roleSeeder.seeder();
        userSeeder.seeder();
    }
}
