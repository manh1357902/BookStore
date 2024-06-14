package come.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private String author;
    private String image;
    private Integer quantity;
    private String description;
    private String publisher;
    private Boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
