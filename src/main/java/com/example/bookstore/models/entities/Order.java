package com.example.bookstore.models.entities;

import com.example.bookstore.models.enums.OrderStatusEnum;
import com.example.bookstore.models.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
    private String phoneNumber;
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
    private Double totalPrice;
    private Date orderDate;
}
