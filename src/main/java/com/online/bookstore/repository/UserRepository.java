package com.online.bookstore.repository;

import com.online.bookstore.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
