package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.User;

public interface  UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
