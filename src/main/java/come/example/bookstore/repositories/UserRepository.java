package come.example.bookstore.repositories;

import come.example.bookstore.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsernameAndIsActive(String username, Boolean isActive);
}
