package com.technopad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technopad.domain.Category;
import com.technopad.domain.User;
import com.technopad.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	public Category findById(Long id){
		Optional<Category> optCategory = categoryRepository.findById(id);
		return optCategory.get();
	}
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	public List<Category> findByUserOrderById(User user){
		return categoryRepository.findByUserOrderById(user);
	}
	public List<Category> findByUserInOrderById(User user){
		List<User> listUser = new ArrayList<>();
		User dUser = new User();
		dUser.setId(Long.valueOf(0));
		listUser.add(dUser);
		listUser.add(user);
		return categoryRepository.findByUserInOrderById(listUser);
	}
	public List<Category> findByUserAndNameEquals(User user, String name){
		return categoryRepository.findByUserAndNameEquals(user, name);
	}
	
	public Category create(Category category, User user){
		category.setUser(user);
		return categoryRepository.save(category);
	}
	public Category update(Category category, User user){
		category.setUser(user);
		category.setUpdtimestamp(new Date());
		return categoryRepository.save(category);
	}
	public Category delete(Category category, User user){
		category.setUser(user);
		category.setDelrecflag(1);
		category.setUpdtimestamp(new Date());
		return categoryRepository.save(category);
	}
}
