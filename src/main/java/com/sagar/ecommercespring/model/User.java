package com.sagar.ecommercespring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    private String role;
    private String mobile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name="user_id"))
    private List<PaymentInformantion> paymentInformantions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> rating =  new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews =  new ArrayList<>();

    private LocalDateTime createdAt;


}
