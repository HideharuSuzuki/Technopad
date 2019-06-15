package com.technopad.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_counter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counter {
	@Id
	private String shikibetsuname;
	private Date addtimestamp;
	private Date updtimestamp;
	private Integer delrecflag=0;
	private Long counter;
}
