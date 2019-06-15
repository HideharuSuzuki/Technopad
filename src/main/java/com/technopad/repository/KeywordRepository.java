package com.technopad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technopad.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
		
	List<Keyword> findByNameEquals(String name);
}
