package com.technopad.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technopad.Def;
import com.technopad.Util;
import com.technopad.domain.Counter;
import com.technopad.domain.Mail;
import com.technopad.repository.CounterRepository;
import com.technopad.repository.MailRepository;


@Service
@Transactional
public class MailService {
	
	@Autowired
	MailClient mailClient;
	@Autowired
	CounterRepository counterRepository;
	@Autowired
	MailRepository mailRepository;

	public Mail findOne(Integer id){
		Optional<Mail> optmail = mailRepository.findById(id);
		return optmail.get();
	}
	// 1h以内に登録されたメールアドレスを取得
	public List<Mail> findByMailAddress1h(String mailAddress, Date nowDate, Date decre1hDate){
		List<Mail> listMail = mailRepository.findByMailAddress1h(mailAddress,nowDate,decre1hDate);
		return listMail;
	}
	// 1h以内に登録され、かつメールIDとUUIDが同じメール情報を取得
	public List<Mail> findMailByWithin1h(Long mailid, String uuid, Date nowDate, Date decre1hDate){
		List<Mail> listMail = mailRepository.findMailByWithin1h(mailid,uuid,nowDate,decre1hDate);
		return listMail;
	}
	public Mail create(Mail mail){
		return mailRepository.save(mail);
	}
	
	// Mailモデル登録と登録メルアドにメール送信
	@Transactional
	public void createAndSendMail(Mail mail) throws RuntimeException{
		try {
			// カウンター取得
			Counter counter = counterRepository.findByShikibetsuname("MAIL").get(0);
			counter.setCounter(counter.getCounter()+1); // カウンターインクリメント
			mail.setId(counter.getCounter());
			
			// メールアドレス登録
			mailRepository.save(mail);
			
			// メール送信(本番)
			if(Util.isTest() == false) {
				sendMailRegisterUser(mail);
			}
			
			// カウンター更新
			counterRepository.updateCounter(counter.getCounter(), "MAIL");
			
		}catch (RuntimeException e) {
			throw new RuntimeException("ユーザーの仮登録エラー : " + e.getMessage());
		}catch (Exception e) {
			throw new RuntimeException("ユーザーの仮登録エラー : 予期しないエラー");
		}
	}
	
	// 登録されたメルアドにユーザー登録するメールを送信
	// 返り値 True : メール送信成功 False : メール送信失敗
	public void sendMailRegisterUser(Mail mail) throws RuntimeException{
		try {
		    String[] str = new String[6];
		    str[0] = Def.MAIL_REGISTER_CONTENT_MESSE1;
		    str[1] = Def.MAIL_REGISTER_CONTENT_MESSE2;
		    str[2] = Def.kMail_Register_URL + mail.getId() + Def.kSpa + mail.getUuid();
		    str[3] = Def.MAIL_REGISTER_CONTENT_MESSE3;
		    str[4] = Def.MAIL_REGISTER_CONTENT_MESSE4;
		    str[5] = Def.MAIL_REGISTER_CONTENT_MESSE5;
		    
		    mailClient.prepareAndSend(mail.getMailaddress(), str);
		    
		}catch (RuntimeException e) {
        	throw new RuntimeException(e);
		}catch (Exception e) {
        	throw new RuntimeException(e);
		}
	}
}
