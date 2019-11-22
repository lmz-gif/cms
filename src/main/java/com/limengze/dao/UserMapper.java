package com.limengze.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.limengze.entity.Article;
import com.limengze.entity.Tag;
import com.limengze.entity.User;

/**
 * @author lmz
 * @Date 2019年10月16日
 * 用户登录注册
 */

public interface UserMapper {
	
	// 注册
	@Insert("insert into cms_user (username,password,gender,create_time,update_time) values "
			+ "(#{username},#{password},#{gender},now(),now())")
	int register(User user);
	
	// 通过用户名查找
	@Select("select id,username,password,role,locked from cms_user where username = #{value}")
	User findByName(String username);
	
	// 通过用户id查找
	User findUserById(Insert id);
	
	// 用户发布文章
	int publish(Article article);

	// 用户查询文章列表
	List<Article> myArticles(@Param("userId")Integer userId,@Param("titles") String titles,@Param("categoryId") Integer categoryId);
	
	// 用户删除文章(逻辑删除)
	@Update("UPDATE cms_article SET deleted = 1 WHERE id = #{value}")
	int delArticle(Integer id);
	
	// 用户修改文章
	int updateArt(Article article);
	
}

