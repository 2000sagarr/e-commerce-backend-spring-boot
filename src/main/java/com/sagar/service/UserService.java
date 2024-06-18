package com.sagar.service;

import com.sagar.exception.UserException;
import com.sagar.model.User;

public interface  UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
