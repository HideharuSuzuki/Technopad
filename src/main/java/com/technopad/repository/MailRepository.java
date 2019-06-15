package com.technopad.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technopad.domain.Mail;

public interface MailRepository extends JpaRepository<Mail, Integer> {
	
	@Query("SELECT x FROM Mail x WHERE x.delrecflag=0 AND x.mailaddress=:mailaddress AND x.addtimestamp >= :decre1hDate AND x.addtimestamp < :nowDate")
	List<Mail> findByMailAddress1h(@Param("mailaddress") String mailaddress, @Param("nowDate") Date nowDate, @Param("decre1hDate") Date decre1hDate);
	
	// 1h以内に登録されたMail情報を取得
	@Query("SELECT x FROM Mail x WHERE x.delrecflag=0 AND x.id=:id AND x.uuid=:uuid AND x.addtimestamp >= :decre1hDate AND x.addtimestamp < :nowDate")
	List<Mail> findMailByWithin1h(@Param("id") Long mailid, @Param("uuid") String uuid, @Param("nowDate") Date nowDate, @Param("decre1hDate") Date decre1hDate);
	
}
