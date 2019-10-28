package com.limengze.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.limengze.entity.Article;
import com.limengze.entity.Link;
import com.limengze.entity.Tag;

/**
 * @author lmz
 * @Date 2019年10月17日
 * 	文章类
 */

public interface ArticleMapper {
	
	// 根据频道ID和分类ID获取文章列表
	List<Article> getArtList(@Param("chnId")Integer chnId, @Param("catId")Integer catId);

	// 获取文章内容
	Article getDetail(Integer aId);
	
	// 获取热门文章
	List<Article> getHotArticle();

	// 获取最新文章
	List<Article> getNewArticle(int num);
	
	// 获取友情链接
	@Select("SELECT id, title, url FROM cms_link LIMIT #{value}")
	List<Link> getFriendLinks(int num);
	
	// 根据文章ID回显
	Article findArtById(Integer id);
	
	// 管理文章(管理员)
	List<Article> managerArts(@Param("status") Integer status);
	
	// 审核文章状态(管理员)
	@Update("UPDATE cms_article SET status = #{status}, updated = now() WHERE id = #{artId}")
	int auditStatus(@Param("status")Integer status, @Param("artId")Integer artId);
	
	// 修改文章热门状态(管理员)
	@Update("UPDATE cms_article SET hot = #{status}, updated = now() WHERE id = #{artId}")
	int setHot(@Param("status")Integer status, @Param("artId")Integer artId);
	
	// 查找标签(根据标签名称获取标签对象)
	@Select("SELECT * FROM cms_tag WHERE tagname = #{value} LIMIT 1")
	Tag getTag(String tag);
	
	
	// 创建标签
	int addTag(Tag resTag);
	
	// 添加文章和标签到中间表
	@Insert("INSERT INTO cms_article_tag_middle VALUES (#{artId},#{tagId})")
	void addArtsTag(@Param("artId")Integer artId, @Param("tagId")Integer tagId);
	
	// 修改文章删除中间表数据
	@Delete("DELETE FROM cms_article_tag_middle WHERE aid = #{value}")
	void delArtTags(Integer id);
	
	//查询全部文章
	@Select("Select id,title from cms_article where deleted = 0")
	List<Article> getArticles();
	
	
}

