package com.limengze.service;

import java.util.List;

import com.limengze.entity.Special;

/**
 * @author lmz
 * @Date 2019年10月25日
 * 	专题文章服务层
 */

public interface SpecialService {
	
	public List<Special> getSpecial();

	public int addSpecial(Special special);

	public Special getSpecialById(Integer id);

	public int addArticleToSpecial(Integer articleId, Integer specialId);

	public int delArticleFromSpecial(Integer sid, Integer aid);

	public int updateSpecial(Special special);

	public void delSpecial(Integer id);

}

