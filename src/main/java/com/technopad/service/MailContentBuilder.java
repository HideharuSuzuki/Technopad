package com.technopad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
 
    private TemplateEngine templateEngine;
 
    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String build(String... message) {
        Context context = new Context();
		for (int i = 0; i < message.length; i++) {
			String s = message[i];
			context.setVariable("message"+(i+1), s);
		}
        return templateEngine.process("mailTemplate", context);
    }
}

