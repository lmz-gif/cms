package com.limengze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limengze.dao.SpecialMapper;
import com.limengze.entity.Special;
import com.limengze.service.SpecialService;

/**
 * @author lmz
 * @Date 2019年10月25日
 * 	专题文章服务实现层
 */

@Service
public class SpecialServiceImpl implements SpecialService {
	
	@Autowired
	SpecialMapper specialMapper;

	// 获取专题列表
	public List<Special> getSpecial() {
		List<Special> specialList = specialMapper.specialList();
		return specialList;
	}

	@Override
	//添加专题
	public int addSpecial(Special special) {
		return specialMapper.addSpecial(special);
	}

	@Override
	//专题详情查询
	public Special getSpecialById(Integer id) {
		return specialMapper.getSpeById(id);
	}

	@Override
	//添加文章到专题
	public int addArticleToSpecial(Integer articleId, Integer specialId) {
	
		return specialMapper.addArticleToSpecial(articleId, specialId);
	}

	@Override
	//删除文章
	public int delArticleFromSpecial(Integer sid, Integer aid) {
		return specialMapper.delArticleFromSpecial(sid, aid);
	}
	
	
	
	

}

