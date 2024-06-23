package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.CartRepository;
import com.sagar.ecommercespring.request.AddItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository,CartItemService cartItemService,
                                     ProductService productService) {
        this.cartRepository=cartRepository;
        this.productService=productService;
        this.cartItemService=cartItemService;
    }

    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart findUserCart(Long userId) {
        Cart cart =	cartRepository.findByUserId(userId);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;
        for(CartItem cartsItem : cart.getCartItems()) {
            totalPrice+=cartsItem.getPrice();
            totalDiscountedPrice+=cartsItem.getDiscountedPrice();
            totalItem+=cartsItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscounte(totalPrice-totalDiscountedPrice);
        cart.setTotalItem(totalItem);

        return cartRepository.save(cart);
    }

    @Override
    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());

        CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(),userId);

        if(isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);


            int price=req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
            return createdCartItem;
        }

        return isPresent;
    }

}

