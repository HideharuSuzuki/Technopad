package com.technopad.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tv_publish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publish implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer hid;
	@NotNull
	private Integer dorder;
	@NotNull
	private String hname1;
}
