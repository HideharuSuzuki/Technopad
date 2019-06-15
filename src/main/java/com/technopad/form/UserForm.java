package com.technopad.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {
	@NotNull
	@Size(min=6,max=20,message="パスワードは{min}~{max}文字です")
	@Pattern(regexp = "^[0-9a-zA-Z]+$",message="パスワードは半角英数のみです")
	private String password;
	@NotNull
	@Min(value=1,message="年を選択してください")
	private Integer year_birth;
	@NotNull
	@Min(value=1,message="月を選択してください")
	private Integer month_birth;
	@NotNull
	@Min(value=1,message="日を選択してください")
	private Integer day_birth;
	@NotNull
	@Min(value=1,message="性別を選択してください")
	private Integer gender;
	
	// hidden要素
	@NotNull
	private Long mailid;
	@NotNull
	private String uuid;
}
