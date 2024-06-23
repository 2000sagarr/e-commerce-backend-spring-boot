package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.CartItemException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.CartItemRepository;
import com.sagar.ecommercespring.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartItemServiceImpl implements CartItemService{
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository,UserService userService) {
        this.cartItemRepository=cartItemRepository;
        this.userService=userService;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {

        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());


        if(user.getId().equals(userId)) {

            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());

            return cartItemRepository.save(item);


        }
        else {
            throw new CartItemException("You can't update  another users cart_item");
        }

    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        return cartItemRepository.isCartItemExist(cart, product, size, userId);
    }



    @Override
    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem=findCartItemById(cartItemId);

        User user=userService.findUserById(cartItem.getUserId());
        User reqUser=userService.findUserById(userId);

        if(user.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItem.getId());
        }
        else {
            throw new UserException("you can't remove anothor users item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id : "+cartItemId);
    }
}
