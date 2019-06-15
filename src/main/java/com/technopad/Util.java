package com.technopad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.technopad.service.AppProperty;


public class Util {
	
	@Autowired
	AppProperty appProperty;

	// Applicationパス取得
	public static Path getApplicationPath(Class<?> cls) throws URISyntaxException {
		ProtectionDomain pd = cls.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();
		URL location = cs.getLocation();
		URI uri = location.toURI();
		Path path = Paths.get(uri);
		return path;
	}
	
	// テスト環境判定
	// 返り値 true : テスト環境 false : テスト環境でない
	public static Boolean isTest(){
		try {
			String hostaddress = InetAddress.getLocalHost().getHostAddress();
			String[] str = hostaddress.split("\\.");
			if(str[0].equals("192")==true && str[1].equals("168")==true) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			// ホスト取得失敗
			return false;
		}
	}
	
	// ユーザーエージェント判定
	// userAgent : ユーザーエージェント文字列
	// 返り値 true : モバイル false : PC
	public static Boolean isMobile(String userAgent) {
		if(userAgent.indexOf("Phone") == -1 && userAgent.indexOf("Android") == -1) {
			// モバイル関連文字列なし->PCとして処理
			return false;
		}else {
			// モバイル関連文字列あり
			return true;
		}
	}
	
	// ファイルの存在と書き込み可のチェック
	public static Boolean checkBeforeWriteFile(File file){
		if (file.exists()){
			if (file.isFile() && file.canWrite()){
				return true;
			}
		}
		return false;
	}
	
	// ファイルの存在のみチェック
	public static Boolean checkIsFile(File file){
		if (file.exists()){
			if (file.isFile()){
				return true;
			}
		}
		return false;
	}
	
	// ディレクトリの存在と書き込み可のチェック
	public static Boolean checkBeforeWriteDirectory(File file){
		if (file.exists()){
			if (file.isDirectory() && file.canWrite()){
				return true;
			}
		}
		return false;
	}
	
	// 数値チェック
	public static Boolean isCheckNum(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// エラーログを出力
	// index 1:ユーザー仮登録エラー 2:ユーザー登録エラー
	public static void ErorrlogException(String errTitle, String errMessage, String logDir, String logFile){
		PrintWriter pw = null;		
		try {
			
			File fLogDir = new File(logDir);
			if(checkBeforeWriteDirectory(fLogDir) == false) {
				// ログ出力先ディレクトリなし、または書込み不可 -> 作成
				fLogDir.mkdir();
			}
			
			File fLogFile = new File(logDir+ Def.kSpa + logFile);
			if(checkBeforeWriteFile(fLogFile) == false) {
				// ログファイルなし、または書込み不可 -> 作成
				fLogFile.createNewFile();
			}
			
			// 書込み(追記)
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fLogFile,true))); // true : 追記
			pw.println(Def.kSdf1.format(new Date()) + " " + errTitle + " : " + errMessage);
			pw.flush();

		}catch (Exception e2) {
			// 書込み失敗 -> スルー
		}finally{
			pw.close();
		}
	}
}
