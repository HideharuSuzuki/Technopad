package com.technopad.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_technique")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause="delrecflag = 0") // 論理削除フラグ
public class Technique {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid")
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動採番
	private Long id;
	
	private Date addtimestamp = new Date();
	private Date updtimestamp = new Date();
	private Integer delrecflag=0;
	private String title;
	private String topimgname;
	private String topimglocation;

	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryid")
	private Category category;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="publishid")
	private Publish publish;
	
	@Type(type = "com.technopad.GenericArrayUserType")
	private Long[] listkeywordid = {(long)0,(long)0,(long)0,(long)0,(long)0};
	@Transient // テーブルへのマッピング対象外
	private String[] listkeywordname = new String[5];
	
	private String explain;
	
}
