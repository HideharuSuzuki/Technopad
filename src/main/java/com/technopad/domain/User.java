package com.technopad.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="listTechnique") // ToStringメソッドでlistTechniqueを除く
public class User {
	@Id
	private Long id;
	private Date addtimestamp = new Date();
	private Date updtimestamp = new Date();
	private Integer delrecflag=0;
	private String mailaddress;
	private String username;
	private Integer status=1;
	@JsonIgnore
	private String encodedpassword;
	private Date birthday;
	private Integer gender=1;
	private String thumbhost;
	private String thumbname;
	private String headerimghost;
	private String headerimgname;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="user") // LAZY:遅延ロード
	private List<Technique> listTechnique;
}
