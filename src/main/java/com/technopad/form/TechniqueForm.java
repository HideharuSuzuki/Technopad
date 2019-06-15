package com.technopad.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TechniqueForm {
	
	private String err; // その他エラー用
	
	private MultipartFile topimg;
	
	@NotNull
	@Size(min=1,max=50,message="タイトルは{min}~{max}文字まで")
	private String title;
	@Size(min=0,max=1000,message="説明は{max}文字まで")
	private String explain;
	@NotNull
	@Min(value=1,message="カテゴリを選択してください")
	private Long categoryid;
	@NotNull
	@Min(value=1,message="公開設定を選択してください")
	private Integer publishid;
		
	private String[] listkeywordname = new String[5];
	
	private String crop_data_topimg;
	
	
//	List<MultipartFile> files;
}