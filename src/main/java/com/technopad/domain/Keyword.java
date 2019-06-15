package com.technopad.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_keyword")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause="delrecflag = 0") // 論理削除フラグ
public class Keyword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動採番
	private Long id;
	private Date addtimestamp = new Date();
	private Date updtimestamp = new Date();
	private Integer delrecflag=0;
	private String  name;
}
