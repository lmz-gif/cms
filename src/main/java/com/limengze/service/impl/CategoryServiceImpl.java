package com.limengze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limengze.dao.CategoryMapper;
import com.limengze.entity.Category;
import com.limengze.service.CategoryService;

/**
 * @author lmz
 * @Date 2019年10月17日
 * 	分类服务实现层
 */

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryMapper categoryMapper;
	
	// 获取分类
	@Override
	public List<Category> getListByChnlId(Integer chnId) {
		List<Category> categories = categoryMapper.getListByChnlId(chnId);
		return categories;
	}

	@Override
	public List<Category> getList() {

		return categoryMapper.getList();
	}

}

