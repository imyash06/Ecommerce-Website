package com.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.UserDtls;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Integer> {

	Optional<UserDtls> findByEmailAndPassword(String email, String password); // optional - check no of users data

}
