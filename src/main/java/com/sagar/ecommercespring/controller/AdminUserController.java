package com.sagar.ecommercespring.controller;


import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt) {

        List<User> user=userService.findAllUsers();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


}