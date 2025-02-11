package com.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring_security.model.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
}
