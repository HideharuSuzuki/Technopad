package com.technopad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technopad.domain.Category;
import com.technopad.domain.Keyword;
import com.technopad.domain.Technique;
import com.technopad.domain.User;
import com.technopad.repository.KeywordRepository;
import com.technopad.repository.TechniqueRepository;


@Service
@Transactional
public class TechniqueService {
	
	@Autowired
	TechniqueRepository techniqueRepository;
	@Autowired
	KeywordRepository keywordRepository;

	public Technique findOne(Long id){
		Optional<Technique> optTechnique = techniqueRepository.findById(id);
		return optTechnique.get();
	}
	public List<Technique> findAll(){
		return techniqueRepository.findAll();
	}
	public Page<Technique> findAll(Pageable pageable){
		return techniqueRepository.findAll(pageable);
	}
	public Page<Technique> findPageByUser(User user, Pageable pageable){
		return techniqueRepository.findPageByUser(user, pageable);
	}
	public List<Technique> findByUserAndCategory(User user, Category category){
		return techniqueRepository.findByUserAndCategory(user, category);
	}
	public List<Technique> findTop6ByAddtimestampGreaterThanEqualOrderByAddtimestampDesc(Date newDate){
		return techniqueRepository.findTop6ByAddtimestampGreaterThanEqualOrderByAddtimestampDesc(newDate);
	}
	public Technique update(Technique technique, User user){
		technique.setUser(user);
		return techniqueRepository.save(technique);
	}
	
	// テクニックとそれに関連するものを作成
	public void create(Technique technique, User user) throws RuntimeException{
		try {
			// キーワード登録
			for(int i=0; i<technique.getListkeywordname().length; i++) {
				List<Keyword> listKeyword = new ArrayList<>();
				String keywordname = technique.getListkeywordname()[i];
				if(keywordname != null && keywordname.length() != 0) {
					// キーワードあり
					listKeyword = keywordRepository.findByNameEquals(keywordname);
					if(listKeyword.size() == 0) {
						// 初めてのキーワード -> 登録後IDをセット
						Keyword keyword = new Keyword();
						keyword.setName(keywordname);
						Keyword rKeyword = keywordRepository.save(keyword);
						if(rKeyword != null) {
							// 登録完了
							technique.getListkeywordid()[i] = rKeyword.getId(); // キーワードIDをセット
						}
					}else if(listKeyword.size() == 1){
						// キーワードはすでに登録されている -> IDだけセット
						technique.getListkeywordid()[i] = listKeyword.get(0).getId();
					}else {
						// 予期しないエラー
						throw new RuntimeException("キーワード作成 エラー:予期しないエラー");
					}
				}
			}// end for
			
			// テクニック作成
			technique.setUser(user);
			Technique rTechnique = techniqueRepository.save(technique);
			if(rTechnique == null)
				throw new RuntimeException("テクニック作成 エラー:テクニック作成失敗");
			
		}catch (RuntimeException e) {
			throw new RuntimeException("テクニック作成 エラー:" + e.getMessage());
		}catch (Exception e) {
			throw new RuntimeException("テクニック作成 エラー:予期しないエラー");
		}
	}
}
