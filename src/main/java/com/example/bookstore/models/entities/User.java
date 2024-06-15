package com.example.bookstore.models.entities;

import com.example.bookstore.models.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    @Column(nullable = false)
    private Boolean isActive;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
