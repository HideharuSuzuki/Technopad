package com.technopad.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_mail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
	@Id
	private Long id;
	private Date addtimestamp;
	private Date updtimestamp;
	private Integer delrecflag=0;
	private String mailaddress;
	private String uuid;
}
