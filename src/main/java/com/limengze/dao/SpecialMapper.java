package com.limengze.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.limengze.entity.Special;

/**
 * @author lmz
 * @Date 2019年10月25日
 * 
 */

public interface SpecialMapper {
	
	// 获取专题的列表
	@Select("SELECT * FROM cms_special ORDER BY id DESC")
	@ResultMap("specialMap")
	public List<Special> specialList();
	
	// 添加专题
	@Insert("INSERT INTO cms_special (title,digest,created) VALUES (#{title},#{digest},now())")
	public int addSpecial(Special special);
	
	// 根据ID获取专题
	@Select("SELECT * FROM cms_special WHERE id = #{value}")
	@ResultMap("specialMap")
	public Special getSpeById(Integer id);
	
	// 添加中间表数据
	@Insert("INSERT INTO cms_special_article VALUES (#{sid},#{aid})")
	public int addArticleToSpecial(@Param("aid")Integer aid,@Param("sid")Integer sid);
	
	// 删除中间表数据
	@Delete("DELETE FROM cms_special_article WHERE sid = #{sid} AND aid = #{aid}")
	public int delArticleFromSpecial(@Param("sid")Integer sid, @Param("aid")Integer aid);


}

