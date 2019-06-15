package com.technopad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.technopad.Def;
import com.technopad.Util;
import com.technopad.domain.Mail;
import com.technopad.domain.User;
import com.technopad.form.MailForm;
import com.technopad.form.UserForm;
import com.technopad.service.AppProperty;
import com.technopad.service.MailService;
import com.technopad.service.UserService;


@Controller
@RequestMapping("identity")
public class IdentityController {
	
	@Autowired
	AppProperty appProperty;
	@Autowired
	MailService mailService;
	@Autowired
	UserService userService;
	@ModelAttribute // マッピングされたメソッドの実行前に実行
	MailForm setUpMailForm() {
		return new MailForm(); // Modelに追加
	}
	@ModelAttribute // マッピングされたメソッドの実行前に実行
	UserForm setUpUserForm() {
		return new UserForm(); // Modelに追加
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}
	
	@GetMapping(Def.kMappEmailRegistration) // ユーザー仮登録
	private String register_mail(Model model, HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathRegisterMail_SP : Def.kPathRegisterMail_PC;
	}
	
	@PostMapping(Def.kMappEmailRegistration) // ユーザー仮登録
	private String register_mail(@Validated MailForm form, 
			BindingResult result, 
			Model model,
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		 
		try {
			// バリデーション
			if(result.hasErrors() == true) 
				return register_mail(model,request);
			
			// 現在日時から1h引く
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.HOUR, -1);
			Date decre1hDate = calendar.getTime();
			
			// Mailテーブル重複確認
			List<Mail> listMail = mailService.findByMailAddress1h(form.getMailaddress(), nowDate, decre1hDate);
			if(listMail.size() > 0) {
				result.rejectValue("mailaddress", null, "このメールアドレスは1時間以内に登録されています");
				return register_mail(model,request);
			}
			
			// Userテーブル重複確認
			List<User> listUser = userService.findByMailaddress(form.getMailaddress());
			if(listUser.size() > 0){
				result.rejectValue("mailaddress", null, "このメールアドレスはすでにユーザー登録されています");
				return register_mail(model,request);
			}
			
			// Mailモデル作成
			Mail mail = new Mail();
			BeanUtils.copyProperties(form, mail);
			mail.setAddtimestamp(nowDate);
			mail.setUpdtimestamp(nowDate);
			UUID uuid = UUID.randomUUID();
			mail.setUuid(uuid.toString());
			
			// Mailモデル登録と登録メルアドにメール送信
			mailService.createAndSendMail(mail);
			
		} catch (Exception e) {			
			// エラー
			result.rejectValue("mailaddress", null, "予期せぬエラー");
			return register_mail(model,request);
		}
		
		// 処理完了・リダイレクト(正常終了コードとメールアドレスを渡す)
		redirectAttributes.addFlashAttribute("returncode", 1);
		redirectAttributes.addFlashAttribute("mailaddress", form.getMailaddress());
		return Def.kRedirectRegisterMailDone;
	}
	
	@GetMapping(Def.kMappEmailRegistrationDone) // ユーザー仮登録処理完了
	private String register_mail_done(Model model, HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathRegisterMailDone_SP : Def.kPathRegisterMailDone_PC;
	}
	
	@GetMapping(Def.kMappUserRegistrationRest) // ユーザー本登録(Rest)
	private String register_user(Model model, HttpServletRequest request, @PathVariable Long mailid, @PathVariable String uuid) {
		
		// 現在日時から1h引く
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.HOUR, -1);
		Date decre1hDate = calendar.getTime();
		
		// 1h以内にユーザー仮登録があるか
		List<Mail> listMail = mailService.findMailByWithin1h(mailid,uuid,nowDate,decre1hDate);
		if(listMail == null || listMail.size() != 1)
			return Def.kRedirectTop; // 取得エラー : TOPへ
		
		// select要素の年生成・モデル追加
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy");
		Integer yyyy = 0; // 西暦
		if(Util.isCheckNum(sd.format(date))==true) {
			yyyy = Integer.parseInt(sd.format(date)); // 現在の西暦
		}else {
			return Def.kRedirectTop; // 取得エラー : TOPへ
		}
		List<Integer> listYYYY = new ArrayList<>(); // // 西暦リスト降順
		for(Integer i=0; i<80; i++) {
			listYYYY.add(yyyy - i);
		}
		
		// select要素の日生成・モデル追加
		UserForm uForm = (UserForm)model.asMap().get("userForm");
		List<Integer> listDD = new ArrayList<>(); // 日リスト
		if(uForm != null) {
			if(uForm.getYear_birth() != null && uForm.getMonth_birth() != null) {
				if(uForm.getYear_birth() != 0 && uForm.getMonth_birth() != 0) {
					Integer uFormYYYY = uForm.getYear_birth();
					Integer uFormMM = uForm.getMonth_birth();
			        Calendar cal = Calendar.getInstance();
			        cal.set(Calendar.YEAR, uFormYYYY);
			        cal.set(Calendar.MONTH, uFormMM - 1);
			        Integer lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 年月の最終日

					for(Integer i=0; i<lastDay; i++) {
						listDD.add(i+1);
					}
				}
			}
		}
		
		// 西暦 mailID uuIDモデル追加
		model.addAttribute("listYYYY", listYYYY);
		model.addAttribute("listDD", listDD);
		model.addAttribute("mailid", mailid);
		model.addAttribute("uuid", uuid);
				
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathRegisterUser_SP : Def.kPathRegisterUser_PC;
	}

	@PostMapping(Def.kMappUserRegistration) // ユーザー登録
	private String register_user(Model model, HttpServletRequest request, @Validated UserForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		
		String username = "";
		try {
			if(result.hasErrors()) {
				return register_user(model, request, form.getMailid(), form.getUuid());
			}

			// 現在日時から1h引く
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.HOUR, -1);
			Date decre1hDate = calendar.getTime();
			
			// 1h以内にユーザー仮登録があるか
			List<Mail> listMail = mailService.findMailByWithin1h(form.getMailid(), form.getUuid(),nowDate,decre1hDate);
			if(listMail == null || listMail.size() != 1)
				return Def.kRedirectTop; // ユーザー仮登録なし、TOPへ

			Mail mail = listMail.get(0);
			
			// Userテーブルメルアド重複確認
			List<User> listUser = userService.findByMailaddress(mail.getMailaddress());
			if(listUser == null || listUser.size() > 0)
				return Def.kRedirectTop; // ユーザー登録あり、TOPへ	
			
			// User作成
			User user = new User();
			user.setAddtimestamp(nowDate);
			user.setUpdtimestamp(nowDate);
			user.setDelrecflag(0);
			user.setMailaddress(mail.getMailaddress());

			// ユーザー名生成7文字(ランダム英数)
			Boolean flg = true;
			while(flg) {
				for(int i=0; i < 7; i++) {
					double rDouble = Math.random() * 10;
					int rNum = (int)Math.floor(rDouble);
					if(rNum >= 5) {
						username = username + RandomStringUtils.randomAlphabetic(1);
					}else {
						username = username + RandomStringUtils.randomNumeric(1);
					}
				}// end for
				
				// ユーザー名重複確認
				listUser = null;
				listUser = userService.findByUsername(username);
				if(listUser.size() == 0) {
					flg = false; // 重複していないので、falseでループを抜ける
				}
			}// end while
			
			user.setUsername(username);
			user.setEncodedpassword(passwordEncoder().encode(form.getPassword()));
			
			// 生年月日
			String strDate = form.getYear_birth()+"/"+form.getMonth_birth()+"/"+form.getDay_birth();
			Date birthday = Def.kSdf4.parse(strDate);
			user.setBirthday(birthday);

			// User登録
			userService.create(user);

		} catch (Exception e) {
			// エラー
			result.rejectValue("password", null, "予期せぬエラー");
			return register_user(model, request, form.getMailid(), form.getUuid());
		}
		
		redirectAttributes.addFlashAttribute("returncode", 1);
		redirectAttributes.addFlashAttribute("username", username);
		return Def.kRedirectRegisterUserDone;
	}
	
	@GetMapping(Def.kMappUserRegistrationDone) // ユーザー登録処理完了
	private String register_user_done(Model model, HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathRegisterUserDone_SP : Def.kPathRegisterUserDone_PC;
	}
}
