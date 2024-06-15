package com.example.bookstore.repositories;

import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.CartItem;
import com.example.bookstore.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    List<CartItem> findByBook(Book book);
    CartItem findCartItemByBook(Book book);
    void deleteByBook(Book book);
}
