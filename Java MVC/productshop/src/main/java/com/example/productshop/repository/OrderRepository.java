package com.example.productshop.repository;

import com.example.productshop.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
        List<Order> findAllByUser_Username(String name);
}
