package com.sagar.ecommercespring.repository;

import com.sagar.ecommercespring.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
