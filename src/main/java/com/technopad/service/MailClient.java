package com.technopad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.technopad.Def;

@Service
public class MailClient {
    private JavaMailSender mailSender;
    
    @Autowired
    public MailClient(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    MailContentBuilder mailContentBuilder;
 
    public void prepareAndSend(String recipient, String... message) throws RuntimeException{
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(Def.MAIL_REGISTER_FROM);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(Def.MAIL_REGISTER_SUBJECT);
            
            String content = mailContentBuilder.build(message);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        }catch (MailException e) {
        	throw new RuntimeException("メール送信エラー : " + e.getMessage());
		}catch (Exception e) {
        	throw new RuntimeException("予期しないエラー : " + e.getMessage());
		}

    }

}
