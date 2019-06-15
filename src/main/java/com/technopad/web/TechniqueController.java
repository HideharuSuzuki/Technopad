package com.technopad.web;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.technopad.Def;
import com.technopad.Util;
import com.technopad.domain.Category;
import com.technopad.domain.Keyword;
import com.technopad.domain.Publish;
import com.technopad.domain.Technique;
import com.technopad.form.TechniqueForm;
import com.technopad.repository.KeywordRepository;
import com.technopad.repository.PublishRepository;
import com.technopad.service.AppProperty;
import com.technopad.service.CategoryService;
import com.technopad.service.LoginUserDetails;
import com.technopad.service.TechniqueService;
import com.technopad.service.UserService;

@Controller
@RequestMapping(Def.kMappUserTech)
public class TechniqueController {
	
	@Autowired
	AppProperty appProperty;
	@Autowired
	UserService userService;
	@Autowired
	TechniqueService techniqueService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	PublishRepository publishRepository;
	@Autowired
	KeywordRepository keywordRepository;
	@Bean
	PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}
	
	@ModelAttribute // マッピングされたメソッドの前に実行
	TechniqueForm setUpTechniqueForm() {
		return new TechniqueForm(); // Modelに追加
	}
	
	@GetMapping(Def.kMappUserTechList) // テクニックリスト(Pageable)
	String displayListTech(Model model, 
			@PageableDefault(size=Def.kDefPageSizeTech) 
			@SortDefault.SortDefaults({
                @SortDefault(sort = "id", 
                		direction = Sort.Direction.ASC)
            }) Pageable pageable,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
				
		// テクニック取得(Pageable)
		Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()-1, Sort.by("id"));
		Page<Technique> pageTechnique = techniqueService.findPageByUser(userDetails.getUser(), pageable);
		
		// Techniqueデータの加工
		List<Technique> listTechnique = pageTechnique.getContent();
		for(int i=0; i < listTechnique.size(); i++) {
			Technique technique = listTechnique.get(i);
			
			// TOP画像の存在確認
			if(technique.getTopimgname() != null && technique.getTopimgname().length() > 0) {
				// TOP画像あり
				try {
					File fPathTopIMG = null;
					if(Util.isTest() == true) {
						// テスト
						fPathTopIMG = new File(Util.getApplicationPath(TechniqueController.class).toString() + Def.kSpa + "static/upDir" + Def.kSpa + technique.getTopimgname());
					}else {
						// 本番
						fPathTopIMG = new File("/var/www/html/upDir/" + technique.getUser().getId() + Def.kSpa + technique.getTopimgname());
					}
					if(Util.checkIsFile(fPathTopIMG) == false) {
						// File存在しない
						technique.setTopimglocation(null);
						technique.setTopimgname(null);
					}
				} catch (Exception e) {
					// File取得エラー
					technique.setTopimglocation(null);
					technique.setTopimgname(null);
				}
			}
			
			// 説明欄の文字列を切り取り(2行まで表示させる)
			int indexFirst = technique.getExplain().indexOf(Def.kNewLineCode);
			if (indexFirst != -1) {
				// 一つめの改行コードあり
				int indexNext = technique.getExplain().indexOf(Def.kNewLineCode, indexFirst + Def.kNewLineCode.length());
				if(indexNext != -1) {
					String explanReplace = technique.getExplain().substring(0, indexNext); // 先頭から2つめの改行コード前まで切り取り
					technique.setExplain(explanReplace);
				}
			}
			
			// キーワード
			Long[] listKeywordid = technique.getListkeywordid();
			for(int j=0; j<listKeywordid.length; j++) {
				if(listKeywordid[j] != null && listKeywordid[j] != 0) {
					// キーワードあり
					Optional<Keyword> optKeyword = keywordRepository.findById(listKeywordid[j]);
					if(optKeyword != null) {
						technique.getListkeywordname()[j] = optKeyword.get().getName();
					}
				}
			}// end for
		}// end for
		
		if(Util.isTest()==true) {
			// テスト環境(画像の取得先)
			model.addAttribute("executionEnv", 1);
		}else {
			// 本番環境(画像の取得先)
			model.addAttribute("executionEnv", 2);
		}
		model.addAttribute("pageTechnique", pageTechnique);
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserTechList_SP : Def.kPathUserTechList_PC;
	}
	
	// テクニック新規作成GET
	@GetMapping(Def.kMappUserTechCreate)
	String displayCreateTech(Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
		
		List<Category> listCategory = categoryService.findByUserInOrderById(userDetails.getUser());
		model.addAttribute("listCategory", listCategory);
		List<Publish> listPublish = publishRepository.findAll(Sort.by("dorder"));
		model.addAttribute("listPublish", listPublish);
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserTechCreate_SP : Def.kPathUserTechCreate_PC;
	}
	
	// テクニック新規作成POST
	@RequestMapping(value = Def.kMappUserTechCreate, method = RequestMethod.POST, consumes = "multipart/form-data")
	private String displayCreateTech(
			@Validated TechniqueForm form, 
			BindingResult result,
			Model model, 
			MultipartRequest multipartRequest,
			RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request) {
		
		try {
			if(result.hasErrors()) {
				return displayCreateTech(model,userDetails,request);
			}

			// テクニック作成
			Technique technique = new Technique();
			Date nowDate = new Date();
			
			// TOP画像があればセット
			List<MultipartFile> listFileTopIMG = multipartRequest.getFiles("topimg");
			if(listFileTopIMG != null && listFileTopIMG.size() == 1) {
				MultipartFile mFileTopIMG = listFileTopIMG.get(0);
				if(mFileTopIMG != null && mFileTopIMG.isEmpty() == false) {
					// TOP画像あり
					
					// TOP画像エラーチェック
					if(mFileTopIMG.getSize() == 0) {
						// エラー : ファイルが空(0バイト)
						result.rejectValue("topimg", null, "TOP画像のサイズが0です");
						return displayCreateTech(model,userDetails,request);
					}
					String oFilename = mFileTopIMG.getOriginalFilename();
					int index = -1;
					String wExtension = ""; // 書込拡張子
					if((index = oFilename.lastIndexOf(".")) == -1) {
						// 「.」がない
						result.rejectValue("topimg", null, "TOP画像の拡張子がありません");
						return displayCreateTech(model,userDetails,request);
					}else {
						// 「.」がある
						String iExtension = oFilename.substring(index+1);
						if(iExtension != null && iExtension.length() != 0) {
							// アップロードファイルに拡張子あり
							String upperIExtension = iExtension.toUpperCase();
							if(upperIExtension.equals("JPG") || upperIExtension.equals("JPEG")) {
								wExtension = "JPEG";
							}else if(upperIExtension.equals("PNG")) {
								wExtension = "PNG";
							}else if(upperIExtension.equals("GIF")) {
								wExtension = "GIF";
							}else {
								// 予期しない拡張子
								result.rejectValue("topimg", null, "TOP画像の拡張子は「JPEG(JPG)」「PNG」「GIF」のみです");
								return displayCreateTech(model,userDetails,request);
							}
						}
					}
					
					// TOP画像の読込
					byte[] bytesTopIMG = mFileTopIMG.getBytes();
					InputStream in = new ByteArrayInputStream(bytesTopIMG);
					BufferedImage bImageFromConvert = ImageIO.read(in);
					int nOriginalWidth  = bImageFromConvert.getWidth(); // アップロード画像横幅
					int nOriginalHeight = bImageFromConvert.getHeight(); // アップロード画像高さ
					
					int nDestWidth  = 480; // 出力画像横幅
					int nDestHeight = 360; // 出力画像高さ
					
					// Scale算出
			        double widthScale = (double) nDestWidth / (double) nOriginalWidth;
			        double heightScale = (double) nDestHeight / (double) nOriginalHeight;
			        double scale = widthScale < heightScale ? widthScale : heightScale;

					// 画像の拡大・縮小
			        ImageFilter filter = new AreaAveragingScaleFilter((int) (nOriginalWidth * scale), (int) (nOriginalHeight * scale));
			        ImageProducer p = new FilteredImageSource(bImageFromConvert.getSource(), filter);
				    java.awt.Image dstImage = Toolkit.getDefaultToolkit().createImage(p);
				    BufferedImage dst = new BufferedImage(dstImage.getWidth(null), dstImage.getHeight(null), BufferedImage.TYPE_INT_ARGB); // TYPE_INT_ARGB:透過情報を含む
					Graphics2D g = dst.createGraphics();
					g.drawImage(dstImage, 0, 0, null);
					g.dispose();

					// 白画像用意(TOP画像の縦横2倍)
					BufferedImage bImg = new BufferedImage(nDestWidth, nDestHeight, BufferedImage.TYPE_3BYTE_BGR);
		            Graphics g2 = bImg.getGraphics();
					g2.setColor(Color.WHITE);
					g2.fillRect(0, 0, nDestWidth, nDestHeight); // 背景描画

					// 画像合成
					g2.drawImage(dst, (int)((nDestWidth-(nOriginalWidth * scale)) / 2), (int)((nDestHeight-(nOriginalHeight * scale)) / 2), null);
					g2.dispose();
					
//					// トリミング
//					if(form.getCrop_data_topimg() != null && form.getCrop_data_topimg().length() > 0) {
//						String[] arrayCrop = form.getCrop_data_topimg().split("/");
//						Float fTrimX = Float.parseFloat(arrayCrop[0].split(":")[1]);
//						Float fTrimY = Float.parseFloat(arrayCrop[1].split(":")[1]);
//						Float fTrimW = Float.parseFloat(arrayCrop[2].split(":")[1]);
//						Float fTrimH = Float.parseFloat(arrayCrop[3].split(":")[1]);
//						int nTrimX = Math.round(fTrimX);
//						int nTrimY = Math.round(fTrimY);
//						int nTrimW = Math.round(fTrimW);
//						int nTrimH = Math.round(fTrimH);
//						int nTrimXNew = nTrimX + (nOriginalWidth / 2);
//						int nTrimYNew = nTrimY + (nOriginalHeight / 2);
////						BufferedImage trimImage = bImg.getSubimage(nTrimXNew,nTrimYNew,nTrimW,nTrimH);
//						
//						bImg = bImg.getSubimage(nTrimXNew,nTrimYNew,nTrimW,nTrimH);
//					}
					
					// 画像の拡大・縮小
//					ImageFilter filter = new AreaAveragingScaleFilter(Def.kTopIMGWidth, Def.kTopIMGHeight);
//					ImageProducer p = new FilteredImageSource(trimImage.getSource(), filter);
//					ImageProducer p = new FilteredImageSource(bImg.getSource(), filter);

					
					// 書込みディレクトリ・ロケーションのセット
					File fPathTopIMG = null;
					if(Util.isTest() == true) {
						// ローカル環境
						technique.setTopimglocation("localhost");
						fPathTopIMG = new File(Util.getApplicationPath(TechniqueController.class).toString() + Def.kSpa + "static/upDir");
					}else {
						// 本番環境
						technique.setTopimglocation("techno-pad.com");
						fPathTopIMG = new File("/var/www/html/upDir/" + userDetails.getUser().getId());
					}
					
					// ディレクトリ存在確認
					if(Util.checkBeforeWriteDirectory(fPathTopIMG) == false) {
						// 親ディレクトリ込みでディレクトリを作成
						if(fPathTopIMG.mkdirs() == false) {
							// ディレクトリ作成失敗
						}
					}
					
					// ファイルの書き込み
					String encode = passwordEncoder().encode(Def.kSdf2.format(nowDate) + userDetails.getUser().getId());
					String fileNameTopIMG = encode + "." + wExtension.toLowerCase();
					File outputfile = new File(fPathTopIMG.getPath() + Def.kSpa + fileNameTopIMG);
					ImageIO.write(bImg, wExtension, outputfile);
					technique.setTopimgname(fileNameTopIMG);
					
				}else {
					// TOP画像なし
					technique.setTopimgname(null);
					technique.setTopimglocation(null);
				}
			}else {
				// TOP画像なし
				technique.setTopimgname(null);
				technique.setTopimglocation(null);
			}// end topimg
			
			BeanUtils.copyProperties(form, technique);
			Category category = categoryService.findById(form.getCategoryid());
			technique.setCategory(category);
			Optional<Publish> optPublish = publishRepository.findById(form.getPublishid());
			technique.setPublish(optPublish.get());

			// テクニック新規作成
			techniqueService.create(technique, userDetails.getUser());

			// 作成完了・リダイレクト
			redirectAttributes.addFlashAttribute("username", userDetails.getUser().getUsername());
			redirectAttributes.addFlashAttribute("returncode", 1);
			return Def.kRedirectUserTechDone;
			
		}catch (Exception e) {
			// エラー
			result.rejectValue("err", null, "予期しないエラー");
			return displayCreateTech(model,userDetails,request);
		}
	}
		
	// テクニック新規作成・削除・修正完了
	@GetMapping(Def.kMappUserTechDone) 
	private String displayDoneTech(
			Model model,
			HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserTechDone_SP : Def.kPathUserTechDone_PC;
	}
	
	// テクニック修正GET
	@GetMapping(Def.kMappUserTechEdit)
	String displayEditTech(Model model, 
			TechniqueForm form,
			HttpServletRequest request,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			@PathVariable Long id) {
		
		Technique technique = techniqueService.findOne(id);
		BeanUtils.copyProperties(technique,form);
		
		form.setCategoryid(technique.getCategory().getHid());
		
		List<Category> listBunrui = bunruiRepository.findAll(Sort.by("dorder"));
		model.addAttribute("listBunrui", listBunrui);
		model.addAttribute("editTechnique", technique);
		model.addAttribute("username", userDetails.getUser().getUsername());
		
		String userAgent = request.getHeader("user-agent");
		return (Util.isMobile(userAgent) == true) ? Def.kPathUserTechEdit_SP : Def.kPathUserTechEdit_PC;
	}

	// テクニック編集POST
	@PostMapping(Def.kMappUserTechEdit) 
	private String displayEditTech(
			@Validated TechniqueForm form,
			BindingResult result,
			Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@PathVariable Long id) {
		
		try {
			if(result.hasErrors()) {
				return displayEditTech(model,form,request,userDetails,id);
			}
			
			// 元のテクニックデータを取得・上書き
			Technique technique = techniqueService.findOne(id);
			BeanUtils.copyProperties(form, technique);
			Date nowDate =new Date();
			technique.setUpdtimestamp(nowDate);
			
			Optional<Category> optCategory = bunruiRepository.findById(form.getCategoryid());
			technique.setCategory(optCategory.get());

			techniqueService.update(technique, userDetails.getUser());

			
			String userAgent = request.getHeader("user-agent");
			return (Util.isMobile(userAgent) == true) ? Def.kPathUserTechDone_SP : Def.kPathUserTechDone_PC;

		} catch (Exception e) {
			// エラー
			return displayEditTech(model,form,request,userDetails,id);
		}
		
	}
	

	
}
