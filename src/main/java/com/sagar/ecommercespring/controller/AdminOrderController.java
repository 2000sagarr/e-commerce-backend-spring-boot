package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.OrderException;
import com.sagar.ecommercespring.model.Order;
import com.sagar.ecommercespring.response.ApiResponse;
import com.sagar.ecommercespring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService=orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders=orderService.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order=orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId,
                                                      @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.cancledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}
