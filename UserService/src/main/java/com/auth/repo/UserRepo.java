package com.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.entity.User;


public interface UserRepo extends JpaRepository<User, Long>{
	
	@Query("select u from User u where u.userName = :username")
	User findByUsername(@Param("username") String username);
}
