package com.technopad.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.technopad.domain.Category;
import com.technopad.domain.Technique;
import com.technopad.form.CategoryForm;
import com.technopad.service.AppProperty;
import com.technopad.service.CategoryService;
import com.technopad.service.LoginUserDetails;
import com.technopad.service.TechniqueService;

@Controller
@RequestMapping(Def.kMappUserCategory)
public class CategoryController {
	
	@Autowired
	AppProperty appProperty;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TechniqueService techniqueService;

	@ModelAttribute // マッピングされたメソッドの前に実行
	CategoryForm setUpCategoryForm() {
		return new CategoryForm(); // Modelに追加
	}
	
	// カテゴリリスト()
	@GetMapping(Def.kMappUserCategoryList)
	String displayListTech(Model model, 
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
		
		List<Category> listCategory = categoryService.findByUserOrderById(userDetails.getUser());
		
		for(int i=0; i<listCategory.size(); i++) {
			Category category = listCategory.get(i);
			List<Technique> listTechnique = techniqueService.findByUserAndCategory(userDetails.getUser(), category);
			if(listTechnique != null && listTechnique.size() > 0)
				category.setCntTech(listTechnique.size());
		}
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserCategoryList_SP : Def.kPathUserCategoryList_PC;
	}

	
	// カテゴリ新規作成GET
	@GetMapping(Def.kMappUserCategoryCreate)
	String displayCreateCategory(
			Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
		
		model.addAttribute("username", userDetails.getUser().getUsername());
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserCategoryCreate_SP : Def.kPathUserCategoryCreate_PC;
	}
	
	// カテゴリ新規作成POST
	@PostMapping(Def.kMappUserCategoryCreate)
	private String displayCreateCategory(
			@Validated CategoryForm form, 
			BindingResult result, 
			Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		
		try {
			
			if(formValidation(result,form,userDetails,1) == false) {
				return displayCreateCategory(model,userDetails,request);
			}
			
			// カテゴリ新規作成
			Category category = new Category();
			BeanUtils.copyProperties(form, category);
			categoryService.create(category, userDetails.getUser());
			
			// 作成完了・リダイレクト
			redirectAttributes.addFlashAttribute("username", userDetails.getUser().getUsername());
			redirectAttributes.addFlashAttribute("returncode", 1);
			return Def.kRedirectUserCategoryDone;
			
		}catch (Exception e) {
			// エラー
			result.rejectValue("err", null, "予期しないエラー");
			return displayCreateCategory(model,userDetails,request);
		}
	}
	
	// カテゴリ作成・削除・修正完了
	@GetMapping(Def.kMappUserCategoryDone) 
	private String displayDoneCategory(
			Model model,
			HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserCategoryDone_SP : Def.kPathUserCategoryDone_PC;
	}
	
	// カテゴリ削除POST
	@PostMapping(Def.kMappUserCategoryDel)
	private String deleteCategory(Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			@PathVariable Long id) {
		
		Category category = categoryService.findById(id);
		if(category == null) {
			// 取得失敗
			return Def.kRedirectUserCategoryList;
		}
		categoryService.delete(category, userDetails.getUser());
		
		// 論理削除完了・リダイレクト
		redirectAttributes.addFlashAttribute("username", userDetails.getUser().getUsername());
		redirectAttributes.addFlashAttribute("returncode", 2);
		return Def.kRedirectUserCategoryDone;
	}
	
	// カテゴリ編集GET
	@GetMapping(Def.kMappUserCategoryEdit)
	String displayEditCategory(Model model, 
			CategoryForm form,
			HttpServletRequest request,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			@PathVariable Long id) {
		
		Category category = categoryService.findById(id);
		BeanUtils.copyProperties(category,form);
				
		model.addAttribute("editCategory", category);
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserCategoryEdit_SP : Def.kPathUserCategoryEdit_PC;
	}
	
	// カテゴリ編集POST
	@PostMapping(Def.kMappUserCategoryEdit) 
	private String displayEditTech(
			@Validated CategoryForm form,
			BindingResult result,
			Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@PathVariable Long id) {
		
		try {
			if(formValidation(result,form,userDetails,2) == false) {
				return displayEditCategory(model,form,request,userDetails,id);
			}

			// カテゴリ編集
			Category category = categoryService.findById(id);
			BeanUtils.copyProperties(form, category);
			categoryService.update(category, userDetails.getUser());
			
			// 作成完了・リダイレクト
			redirectAttributes.addFlashAttribute("username", userDetails.getUser().getUsername());
			redirectAttributes.addFlashAttribute("returncode", 3);
			return Def.kRedirectUserCategoryDone;

		}catch (Exception e) {
			// エラー
			result.rejectValue("err", null, "予期しないエラー");
			return displayEditCategory(model,form,request,userDetails,id);
		}
	}
	
	// フォームバリデーション
	// index 1:新規作成 2:編集
	private Boolean formValidation(BindingResult result, CategoryForm form, LoginUserDetails userDetails, int index) {
		
		if(result.hasErrors()) {
			return false;
		}
		// カテゴリ名スペースチェック
		if(form.getName() == null || form.getName().replaceAll(" ", "").replaceAll("　", "").length() == 0) {
			// エラー : カテゴリ名がスペースのみ
			result.rejectValue("name", null, "スペースのみでカテゴリは作成できません");
			return false;
		}
		// カテゴリ名重複チェック
		List<Category> listCategory1 = categoryService.findByUserAndNameEquals(userDetails.getUser(), form.getName());
		if(listCategory1.size() >= 1) {
			result.rejectValue("err", null, "入力されたカテゴリ名はすでに登録されています");
			return false;
		}
		
		// カテゴリ作成数チェック(新規作成のみ)
		if(index == 1) {
			List<Category> listCategory2 = categoryService.findByUserOrderById(userDetails.getUser());
			if(listCategory2.size() >= Def.kNumCategoryMAX) {
				result.rejectValue("err", null, "カテゴリの作成は "+Def.kNumCategoryMAX+" 個まで");
				return false;
			}
		}

		return true;
	}
}
