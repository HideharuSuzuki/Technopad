package com.technopad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technopad.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT x FROM User x WHERE x.delrecflag=0 AND x.mailaddress=:mailaddress")
	List<User> findByMailaddress(@Param("mailaddress") String mailaddress);
	
	@Query("SELECT x FROM User x WHERE x.delrecflag=0 AND x.username=:username")
	List<User> findByUsername(@Param("username") String username);

}
