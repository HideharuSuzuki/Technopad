package com.technopad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technopad.domain.Category;
import com.technopad.domain.User;

public interface CategoryRepository  extends JpaRepository<Category, Long>{

	List<Category> findByUserInOrderById(List<User> user);
	
	List<Category> findByUserOrderById(User user);
	
	List<Category> findByUserAndNameEquals(User user, String name);
	
}
