package com.technopad.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryForm {
	
	private String err; // その他エラー用
	
	@NotNull
	@Size(min=1,max=30,message="カテゴリ名は{min}~{max}文字まで")
	private String name; // カテゴリ名
}
