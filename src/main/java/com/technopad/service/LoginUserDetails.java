package com.technopad.service;

import org.springframework.security.core.authority.AuthorityUtils;

import com.technopad.domain.User;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = 1L;
	private final User user;
	
	public LoginUserDetails(User user) {
		super(user.getMailaddress(),user.getEncodedpassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}
//    public User getUser() {
//        return user;
//    }
}
