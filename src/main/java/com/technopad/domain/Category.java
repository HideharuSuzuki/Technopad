package com.technopad.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause="delrecflag = 0") // 論理削除フラグ
public class Category {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid")
	private User user;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動採番
	private Long id;
	
	private Date addtimestamp = new Date();
	private Date updtimestamp = new Date();
	private Integer delrecflag = 0;

	private String name; // カテゴリ名
	
	@Transient // テーブルへのマッピング対象外
	private Integer cntTech = 0; // カテゴリが同じテクニック数
}
