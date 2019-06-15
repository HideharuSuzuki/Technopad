package com.technopad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import com.technopad.service.LoginUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	LoginUserDetailsService userDetailsService;
    
	@Override
	public void configure(WebSecurity web) throws Exception{
		// spring-securityの制限を無視してほしい場所
		web.ignoring().antMatchers("/webjars/**", "/css/**","/js/**","/images/**");
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()
		.antMatchers("/","/loginForm","/identity/**") // ログインが必要ないURL
		.permitAll().anyRequest().authenticated()
		.and()
		.formLogin().loginProcessingUrl("/login").loginPage("/loginForm").failureUrl("/loginForm?error")
		.defaultSuccessUrl("/user/top", true).usernameParameter("mailaddress").passwordParameter("password")
		.and()
		.logout().logoutSuccessUrl("/loginForm").deleteCookies("JSESSIONID","remember-me")
		.and()
		.rememberMe().key("uniqueAndSecret").userDetailsService(userDetailsService)
        ;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

}
