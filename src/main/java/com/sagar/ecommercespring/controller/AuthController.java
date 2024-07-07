package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.config.JwtTokenProvider;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.UserRepository;
import com.sagar.ecommercespring.request.LoginRequest;
import com.sagar.ecommercespring.response.AuthResponse;
import com.sagar.ecommercespring.service.CartService;
import com.sagar.ecommercespring.service.CustomUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImpl customUserDetails;
    private CartService cartService;

    @Autowired
    public AuthController(UserRepository userRepository, CustomUserServiceImpl customUserDetails, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, CartService cartService) {
        this.userRepository = userRepository;
        this.customUserDetails = customUserDetails;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cartService = cartService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        String role=user.getRole();

        User isEmailExist = userRepository.findByEmail(email);

        // Check if user with the given email already exists
        if (isEmailExist!=null) {

            throw new UserException("Email Is Already Used With Another Account");
        }

        // Create new user
        User createdUser= new User();
        createdUser.setEmail(email);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setRole(role);

        User savedUser= userRepository.save(createdUser);

        // create empty cart at start
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username +" ----- "+password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("sign in userDetails - "+userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
