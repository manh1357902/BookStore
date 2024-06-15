package com.example.bookstore.repositories;

import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByCategoriesAndIsActive(List<Category> categories, boolean isActive);
    List<Book> findByIsActive(boolean isActive);

}
