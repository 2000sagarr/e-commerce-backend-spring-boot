package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.config.JwtTokenProvider;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,JwtTokenProvider jwtTokenProvider) {
        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        User user=userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllByOrderByCreatedAtDesc();
    }
}
