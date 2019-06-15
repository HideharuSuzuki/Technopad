package com.technopad.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technopad.domain.Category;
import com.technopad.domain.Technique;
import com.technopad.domain.User;

public interface TechniqueRepository extends JpaRepository<Technique, Long> {
	
	@Query("SELECT x FROM Technique x WHERE x.user = :user")
	Page<Technique> findPageByUser(@Param("user") User user, Pageable pageable);
	
	List<Technique> findByUserAndCategory(User user, Category category);
	
	List<Technique> findTop6ByAddtimestampGreaterThanEqualOrderByAddtimestampDesc(Date newDate);
}
