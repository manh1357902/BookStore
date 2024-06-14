package come.example.bookstore.repositories;

import come.example.bookstore.models.entities.Book;
import come.example.bookstore.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByCategories(List<Category> categories);
    Boolean existsByTitle(String title);
}
