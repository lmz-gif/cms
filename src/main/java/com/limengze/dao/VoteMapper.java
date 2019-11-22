package com.limengze.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.limengze.entity.Article;

/**
  * @author lmz
  * @date 2019年10月29日 下午7:09:34
  * 
  */

public interface VoteMapper {
	//发布投票
	@Insert("insert into cms_article(title,content,user_id,articleType) values(#{title},#{content},#{id},2)")
	int add(@Param("title")String title, @Param("content")String content, @Param("id")Integer id);
	
	//用户投票列表
	@Select("select title,id from cms_article where user_id=#{userId} and articleType=2")
	List<Article> getVotes(Integer userId);
	
}
