package com.technopad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technopad.domain.Keyword;
import com.technopad.domain.User;
import com.technopad.repository.KeywordRepository;

@Service
@Transactional
public class KeywordService {
	@Autowired
	KeywordRepository keywordRepository;

	public Keyword findOne(Long id){
		Optional<Keyword> optKeyword = keywordRepository.findById(id);
		return optKeyword.get();
	}
	public List<Keyword> findByNameEquals(String str) {
		return keywordRepository.findByNameEquals(str);
	}
	public Keyword create(Keyword keyword, User user){
		return keywordRepository.save(keyword);
	}
}
