package com.limengze.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.limengze.entity.Category;

/**
 * @author lmz
 * @Date 2019年10月17日
 * 
 */

public interface CategoryMapper {
	
	/**
	 * 根据频道ID获取分类
	 * @param id
	 * @return
	 */
	@Select("select id,name,channel_id channelId from cms_category where channel_id = #{value}")
	List<Category> getListByChnlId(Integer id);

}

