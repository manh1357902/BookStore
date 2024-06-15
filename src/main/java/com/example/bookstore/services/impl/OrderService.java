package com.example.bookstore.services.impl;

import com.example.bookstore.models.entities.*;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.CartItemRepository;
import com.example.bookstore.repositories.OrderDetailRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.services.IOrderService;
import com.example.bookstore.dto.requests.OrderRequest;
import com.example.bookstore.dto.responses.OrderResponse;
import com.example.bookstore.models.enums.OrderStatusEnum;
import com.example.bookstore.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public OrderResponse createOrder(List<Long> bookIds, OrderRequest orderRequest) throws Exception {
        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Book> books = new ArrayList<>();
        for(Long id: bookIds){
            books.add(bookRepository.findById(id).orElseThrow(() -> new Exception("Book not found with id: "+id)));
        }
        Order order = Order.builder()
                .user(user)
                .shippingAddress(orderRequest.getShippingAddress())
                .phoneNumber(orderRequest.getPhoneNumber())
                .paymentMethod(orderRequest.getPaymentMethod())
                .orderStatus(OrderStatusEnum.PENDING)
                .orderDate(new Date())
                .build();
        order = orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalPrice = 0;
        for(Book book: books){
            CartItem cartItem = cartItemRepository.findCartItemByBook(book);
            OrderDetail orderDetail = OrderDetail.builder()
                    .book(book)
                    .price(book.getPrice())
                    .quantity(cartItem.getQuantity())
                    .order(order)
                    .build();
            orderDetails.add(orderDetailRepository.save(orderDetail));
            totalPrice += book.getPrice();
            cartItemRepository.delete(cartItem);
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }
}
