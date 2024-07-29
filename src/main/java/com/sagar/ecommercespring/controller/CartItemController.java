package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.CartItemException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.response.ApiResponse;
import com.sagar.ecommercespring.service.CartItemService;
import com.sagar.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
//@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;

    @Autowired
    public CartItemController(CartItemService cartItemService,UserService userService) {
        this.cartItemService=cartItemService;
        this.userService=userService;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException {

        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res=new ApiResponse("Item Remove From Cart",true);

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{

        User user=userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
    }
}
