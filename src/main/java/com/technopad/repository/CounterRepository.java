package com.technopad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technopad.domain.Counter;

public interface CounterRepository extends JpaRepository<Counter, String> {
	
	// カウンター取得(行ロック)
	@Query(value="SELECT * FROM tb_counter x WHERE x.delrecflag = 0 AND x.shikibetsuname=:shikibetsuname FOR UPDATE", nativeQuery = true)
	List<Counter> findByShikibetsuname(@Param("shikibetsuname") String shikibetsuname);
	
	// カウンター更新
	@Modifying
	@Query("UPDATE Counter x SET x.counter = :counter WHERE x.shikibetsuname = :shikibetsuname")
	Integer updateCounter(@Param("counter") Long counter, @Param("shikibetsuname") String shikibetsuname);

}
