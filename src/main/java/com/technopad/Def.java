package com.technopad;

import java.io.File;
import java.text.SimpleDateFormat;

public class Def {
	
	// 基本設定
	public final static String kSpa = File.separator; // セパレーター
	public final static String kNewLineCode = "\r\n"; // 改行コード
	
	// マッピング(トップ)
	public final static String kMappTop = "/";
	// マッピング(ユーザー登録)
	public final static String kMappEmailRegistration = "/email_registration";
	public final static String kMappEmailRegistrationDone = "/email_registration/done_mail";
	public final static String kMappUserRegistrationRest = "/user_registration/{mailid}/{uuid}";
	public final static String kMappUserRegistration = "/user_registration";
	public final static String kMappUserRegistrationDone = "/user_registration/done_user";
	// マッピング(ユーザーページテクニック)
	public final static String kMappUserTech = "user/technique";
	public final static String kMappUserTechList = "/list";
	public final static String kMappUserTechCreate = "/create";
	public final static String kMappUserTechDone = "/done";
	public final static String kMappUserTechEdit = "/edit";
	// マッピング(ユーザーページカテゴリ)
	public final static String kMappUserCategory = "user/category";
	public final static String kMappUserCategoryList = "/list";
	public final static String kMappUserCategoryCreate = "/create";
	public final static String kMappUserCategoryDone = "/done";
	public final static String kMappUserCategoryEdit = "/edit/{id}";
	public final static String kMappUserCategoryDel = "/delete/{id}";
	
	// 画面パス
	public final static String kPathTop_PC = "pc/pc_top"; // TOP
	public final static String kPathTop_SP = "sp/sp_top"; // TOP
	public final static String kPathLoginform_PC = "pc/pc_loginForm"; // LoginForm
	public final static String kPathLoginform_SP = "sp/sp_loginForm"; // LoginForm
	public final static String kPathErr = "/error"; // error
	
	// 画面パス(ユーザー登録)
	public final static String kPathRegisterMail_PC = "pc/identity/email_registration/pc_register_mail";
	public final static String kPathRegisterMailDone_PC = "pc/identity/email_registration/pc_done_mail";
	public final static String kPathRegisterMail_SP = "sp/identity/email_registration/sp_register_mail";
	public final static String kPathRegisterMailDone_SP = "sp/identity/email_registration/sp_done_mail";
	public final static String kPathRegisterUser_PC = "pc/identity/user_registration/pc_register_user";
	public final static String kPathRegisterUserDone_PC = "pc/identity/user_registration/pc_done_user";
	public final static String kPathRegisterUser_SP = "sp/identity/user_registration/sp_register_user";
	public final static String kPathRegisterUserDone_SP = "sp/identity/user_registration/sp_done_user";
	
	// 画面パス(ユーザーページTOP)
	public final static String kPathUserTop_PC = "pc/user/pc_top"; // ユーザーTOP
	public final static String kPathUserTop_SP = "sp/user/sp_top"; // ユーザーTOP

	// 画面パス(ユーザーページテクニック)	
	public final static String kPathUserTechList_PC = "pc/user/technique/pc_list";
	public final static String kPathUserTechCreate_PC = "pc/user/technique/pc_create";
	public final static String kPathUserTechDone_PC = "pc/user/technique/pc_done";
	public final static String kPathUserTechEdit_PC = "pc/user/technique/pc_edit";
	public final static String kPathUserTechList_SP = "sp/user/technique/sp_list";
	public final static String kPathUserTechCreate_SP = "sp/user/technique/sp_create";
	public final static String kPathUserTechDone_SP = "sp/user/technique/sp_done";
	public final static String kPathUserTechEdit_SP = "sp/user/technique/sp_edit";

	
	// 画面パス(ユーザーページカテゴリ)
	public final static String kPathUserCategoryList_PC = "pc/user/category/pc_list";
	public final static String kPathUserCategoryCreate_PC = "pc/user/category/pc_create";
	public final static String kPathUserCategoryDone_PC = "pc/user/category/pc_done";
	public final static String kPathUserCategoryEdit_PC = "pc/user/category/pc_edit";
	public final static String kPathUserCategoryList_SP = "sp/user/category/sp_list";
	public final static String kPathUserCategoryCreate_SP = "sp/user/category/sp_create";
	public final static String kPathUserCategoryDone_SP = "sp/user/category/sp_done";
	public final static String kPathUserCategoryEdit_SP = "sp/user/category/sp_edit";
	
	// リダイレクト(ユーザー登録)
	public final static String kRedirectTop = "redirect:/";
	public final static String kRedirectRegisterMailDone = "redirect:email_registration/done_mail";
	public final static String kRedirectRegisterUserDone = "redirect:user_registration/done_user";
	// リダイレクト(ユーザーページテクニック)
	public final static String kRedirectUserTechDone = "redirect:done";
	// リダイレクト(ユーザーページカテゴリ)
	public final static String kRedirectUserCategoryList = "redirect:/user/category/list";
	public final static String kRedirectUserCategoryDone = "redirect:/user/category/done";
	
	// 日付フォーマット
	public final static SimpleDateFormat kSdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	public final static SimpleDateFormat kSdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public final static SimpleDateFormat kSdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
	public final static SimpleDateFormat kSdf4 = new SimpleDateFormat("yyyy/MM/dd");
	
	// エラーログタイトル
	public final static String kErrTitleMail = "ユーザー仮登録エラー";
	public final static String kErrTitleUser = "ユーザー登録エラー";
	public final static String kErrTitleTechCreate = "テクニック作成エラー";
	public final static String kErrTitleTechEdit   = "テクニック修正エラー";
	// エラーログファイル名
	public final static String kErrLogFile = "errorlog.txt";

	
	// メール
	public final static String kMail_Register_URL = "https://techno-pad.com/identity/user_registration/";
	public final static String MAIL_REGISTER_FROM = "テクノパッド <noreplay@techno-pad.com>";
	public final static String MAIL_REGISTER_SUBJECT = "【テクノパッド本登録のご案内】";
	public final static String MAIL_REGISTER_CONTENT_MESSE1 = "テクノパッド本登録のご案内です。";
	public final static String MAIL_REGISTER_CONTENT_MESSE2 = "以下のリンクをクリックして、本登録の手続きを行ってください。";
	public final static String MAIL_REGISTER_CONTENT_MESSE3 = "※このリンクをクリックして手続きを行っていただかないと、テクノパッドの本登録が完了しません。";
	public final static String MAIL_REGISTER_CONTENT_MESSE4 = "※メールに記載されたリンクをクリックしないまま24時間が経過した場合、本登録ができなくなります。その場合はもう一度仮登録からやり直してください。";
	public final static String MAIL_REGISTER_CONTENT_MESSE5 = "※当メールに心当たりのない場合は、誠に恐れ入りますが破棄して頂けますよう、よろしくお願い致します。";
	
	// Category
	public final static int kNumCategoryMAX = 20; // 最大カテゴリ作成数
	
	// Technique
	public final static int kDefPageSizeTech = 5; // テクニックリスト取得数(デフォルト)
	
	public final static int kTopIMGWidth  = 600;
	public final static int kTopIMGHeight = 400;
}
