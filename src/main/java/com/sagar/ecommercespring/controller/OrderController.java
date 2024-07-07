package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.OrderException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Address;
import com.sagar.ecommercespring.model.Order;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.service.OrderService;
import com.sagar.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService,UserService userService) {
        this.orderService=orderService;
        this.userService=userService;
    }

    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
                                                    @RequestHeader("Authorization")String jwt) throws UserException {

        User user=userService.findUserProfileByJwt(jwt);
        Order order =orderService.createOrder(user, spippingAddress);
        System.out.println();
        return new ResponseEntity<Order>(order, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        Order orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

}
