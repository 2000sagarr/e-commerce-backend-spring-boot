package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}

