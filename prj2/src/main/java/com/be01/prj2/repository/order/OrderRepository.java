package com.be01.prj2.repository.order;


import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Customer customer);
}
