package com.technopad.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technopad.Def;
import com.technopad.Util;
import com.technopad.domain.Technique;
import com.technopad.service.TechniqueService;


@Controller
@RequestMapping("/")
public class TopController {

	@Autowired
	TechniqueService techniqueService;

	
	@GetMapping(Def.kMappTop)
	String display(
			Model model,
			HttpServletRequest request) {
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		Date newDate = calendar.getTime();
		
		List<Technique> listNewTechnique = techniqueService.findTop6ByAddtimestampGreaterThanEqualOrderByAddtimestampDesc(newDate);
		
		if(Util.isTest()==true) {
			// テスト環境(画像の取得先)
			model.addAttribute("executionEnv", 1);
		}else {
			// 本番環境(画像の取得先)
			model.addAttribute("executionEnv", 2);
		}

		model.addAttribute("listNewTechnique", listNewTechnique);
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathTop_SP : Def.kPathTop_PC;
	}
}






