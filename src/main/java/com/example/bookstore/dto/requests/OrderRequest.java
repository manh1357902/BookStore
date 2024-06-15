package com.example.bookstore.dto.requests;

import com.example.bookstore.models.enums.OrderStatusEnum;
import com.example.bookstore.models.enums.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String shippingAddress;
    private String phoneNumber;
    private List<Long> bookIds;
    private PaymentMethodEnum paymentMethod;
    private OrderStatusEnum orderStatus;
}
