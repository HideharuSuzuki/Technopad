package com.technopad.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technopad.Def;
import com.technopad.Util;
import com.technopad.service.LoginUserDetails;
import com.technopad.service.TechniqueService;
import com.technopad.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
		
	@Autowired
	UserService userService;
	@Autowired
	TechniqueService techniqueService;

	@GetMapping("/top") // 管理画面TOP
	String displayTop(Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
		
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserTop_SP : Def.kPathUserTop_PC;
	}
	

}
