package com.limengze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.limengze.dao.ArticleMapper;
import com.limengze.entity.Article;
import com.limengze.entity.Link;
import com.limengze.service.ArticleService;

import scala.remote;
import scala.concurrent.ops;

/**
 * @author lmz
 * @Date 2019年10月17日
 *  文章类服务实现层
 */

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	RedisTemplate<String, Article> redis;
	
	@Autowired
	ArticleMapper articleMapper;

	// 获取文章列表
	@Override
	public PageInfo<Article> getArtList(Integer chnId, Integer catId, Integer pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Article> list = articleMapper.getArtList(chnId, catId);
		return new PageInfo<>(list);
	}
	
	// 获取文章内容
	@Override
	public Article getDetail(Integer aId) {
		Article art = articleMapper.getDetail(aId);
		return art;
	}

	// 获取热门文章
	@Override
	public PageInfo<Article> getHotArticle(Integer pageNum) {
		ListOperations<String, Article> opsForList = redis.opsForList();
		PageInfo<Article> pageInfo = null;
		//redis内有数据
		if (redis.hasKey("hotArticle")) {
			List<Article> list = opsForList.range("hotArticle", (pageNum-1)*5, pageNum*5-1);
			pageInfo = new PageInfo<>(list);
			pageInfo.setPageNum(pageNum);
			double size = opsForList.size("hotArticle");
			pageInfo.setPages((int)Math.ceil(size/5));
		//第一次查询，存入redis
		}else {
			PageHelper.startPage(pageNum, 10);
			List<Article> hotArticle = articleMapper.getHotArticle();
			opsForList.rightPushAll("hotArticle", hotArticle);
			pageInfo= new PageInfo<>(hotArticle);
		}
		
		return pageInfo;
	}

	// 获取最新文章
	@Override
	public List<Article> getNewArticle(int num) {
		ListOperations<String, Article> opsForList = redis.opsForList();
		List<Article> newArticles = null;
		//redis内有数据
		if (redis.hasKey("newArticles")) {
			newArticles = opsForList.range("newArticles", 0, -1);
		//第一次查询，存入redis
		}else {
			newArticles = articleMapper.getNewArticle(num);
			opsForList.rightPushAll("newArticles",  newArticles);
		}
		return newArticles;
	}
	
	//友情链接
	@Override
	public List<Link> getFriendLinks(int num) {
		List<Link> friendLinks = articleMapper.getFriendLinks(num);
		return friendLinks;
	}

	
	// 管理文章(管理员)
	@Override
	public PageInfo<Article> managerArts(int pageNum, Integer status) {
		PageHelper.startPage(pageNum, 10);
		List<Article> artList = articleMapper.managerArts(status);
		ListOperations<String, Article> opsForList = redis.opsForList();
		//更新最新文章
		List<Article> newArticles = articleMapper.getNewArticle(5);
		redis.delete("newArticles");
		opsForList.rightPushAll("newArticles",  newArticles);
		//更新热门文章
		redis.delete("hotArticle");
		List<Article> hotArticle = articleMapper.getHotArticle();
		opsForList.rightPushAll("hotArticle", hotArticle);
		return new PageInfo<>(artList);
	}
	
	// 审核文章状态(管理员)
	@Override
	public int auditStatus(Integer status, Integer artId) {
		int res = articleMapper.auditStatus(status, artId);
		return res;
	}
	
	// 修改文章热门状态
	@Override
	public int setHot(Integer status, Integer artId) {
		int res = articleMapper.setHot(status, artId);
		return res;
	}
	
	//	全部文章
	@Override
	public List<Article> getArticles() {
		return articleMapper.getArticles();
	}

}

