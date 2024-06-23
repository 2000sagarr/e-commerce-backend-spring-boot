package com.sagar.ecommercespring.repository;

import com.sagar.ecommercespring.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
