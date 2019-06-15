package com.technopad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.technopad.domain.User;
import com.technopad.repository.UserRepository;

@Service

public class LoginUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String mailaddress) throws UsernameNotFoundException{
		List<User> listUser = userRepository.findByMailaddress(mailaddress);
		if(listUser==null || listUser.size() != 1) {
			throw new UsernameNotFoundException("The requested user is not found");
		}
		return new LoginUserDetails(listUser.get(0));
	}
	
}
