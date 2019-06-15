package com.technopad.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.technopad.Def;
import com.technopad.Util;

@Controller
public class LoginController {
	@GetMapping("/loginForm")
	String loginForm(HttpServletRequest request) {
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathLoginform_SP : Def.kPathLoginform_PC;
	}
}
