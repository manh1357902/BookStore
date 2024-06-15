package com.example.bookstore.dto.responses;

import com.example.bookstore.models.enums.OrderStatusEnum;
import com.example.bookstore.models.enums.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private List<OrderDetailResponse> orderDetails;
    private String phoneNumber;
    private String shippingAddress;
    private PaymentMethodEnum paymentMethod;
    private OrderStatusEnum orderStatus;
    private Double totalPrice;
    private Date orderDate;
}
