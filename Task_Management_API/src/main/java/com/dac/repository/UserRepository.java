package com.dac.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dac.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUserName(String username);

	boolean existsByUserName(String userName);

}
