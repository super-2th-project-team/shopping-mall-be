package com.be01.prj2.repository;

import com.be01.prj2.entity.Customer;
import com.be01.prj2.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndRole(String email, Role role);

    Optional<Customer> findAllByRole(Role role);

    Optional<Customer> findAllByEmailAndPasswordAndMobile(String email,String password,String mobile);

    Optional<Customer> findAllByEmailAndMobile(String email,String mobile);
    Optional<Customer> findByEmailAndMobile(String email,String mobile);


    Customer findCustomerByEmail(String email);


}
