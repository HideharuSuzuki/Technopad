package com.technopad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.technopad.Util;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="app")
public class AppProperty {
	
	@Autowired
	TestProperty testProperty;
	
	private String logDir; // ログ出力先ディレクトリ
	
	public void setLogDir(String str){
		logDir = (Util.isTest()) ? logDir = testProperty.getLogDir() : str;		
	}
	
}










