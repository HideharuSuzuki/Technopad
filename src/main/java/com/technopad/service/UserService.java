package com.technopad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technopad.domain.Counter;
import com.technopad.domain.User;
import com.technopad.repository.CounterRepository;
import com.technopad.repository.UserRepository;


@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	CounterRepository counterRepository;

	public User findOne(Integer id){
		Optional<User> optUser = userRepository.findById(id);
		return optUser.get();
	}
	
	public List<User> findByMailaddress(String mailAddress){
		List<User> listUser = userRepository.findByMailaddress(mailAddress);
		return listUser;
	}
	public List<User> findByUsername(String username){
		List<User> listUser = userRepository.findByUsername(username);
		return listUser;
	}
	@Transactional
	public void create(User user) throws RuntimeException{
		try {
			// カウンター取得
			Counter counter = counterRepository.findByShikibetsuname("USER").get(0);
			counter.setCounter(counter.getCounter()+1); // カウンターインクリメント
			user.setId(counter.getCounter());
			
			// ユーザー登録
			userRepository.save(user);
			
			// カウンター更新
			counterRepository.updateCounter(counter.getCounter(), "USER");

		}catch (RuntimeException e) {
			throw new RuntimeException();
		}catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
