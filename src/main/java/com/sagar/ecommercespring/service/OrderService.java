package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.OrderException;
import com.sagar.ecommercespring.model.Address;
import com.sagar.ecommercespring.model.Order;
import com.sagar.ecommercespring.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAdress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId)throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancledOrder(Long orderId) throws OrderException;

    public List<Order>getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
