package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.CartItemException;
import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.request.AddItemRequest;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
