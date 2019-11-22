package com.limengze.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.limengze.commons.PageUtils;
import com.limengze.entity.Article;
import com.limengze.entity.Category;
import com.limengze.entity.Channel;
import com.limengze.entity.Link;
import com.limengze.entity.Special;
import com.limengze.service.ArticleService;
import com.limengze.service.CategoryService;
import com.limengze.service.ChannelService;
import com.limengze.service.SpecialService;

/**
 * @author lmz
 * @Date 2019年10月17日
 *  主页控制层
 */

@Controller
public class IndexController {
	
	@Autowired
	ChannelService channelService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	SpecialService specialService;
	
	
	/**
	 * 前往主页
	 * @param requset
	 * @param chnId       频道id
	 * @param catId       分类id
	 * @param pageNum     页码
	 * @return
	 */
	@RequestMapping({"index","/"})
	public String index(HttpServletRequest requset,
			@RequestParam(defaultValue="0") Integer chnId,
			@RequestParam(defaultValue="0") Integer catId,
			@RequestParam(defaultValue="1") Integer pageNum) {
		
		
		requset.setAttribute("chnId", chnId);                          // 频道id回显
		requset.setAttribute("catId", catId);                          // 分类id回显
		
		// 获取所有的频道
		List<Channel> channels = channelService.getAllChannel();
		requset.setAttribute("channels", channels);
		
		
		
		//文章显示
		if (chnId != 0) { 
			// 如果频道的id不为0,即不为热门文章
			// 获取该频道下的分类列表，全部
			List<Category> categories = categoryService.getListByChnlId(chnId);   
			requset.setAttribute("categories", categories);
			// 获取分类下的文章列表
			PageInfo<Article> articles = articleService.getArtList(chnId, catId, pageNum);
			List<Article> list = articles.getList();
			requset.setAttribute("articles", articles);
			
		} else {
			// 否则即为首页，热门文章
			PageInfo<Article> hotArticle = articleService.getHotArticle(pageNum);
			requset.setAttribute("articles", hotArticle);
		}
		
		
		// 获取最新文章
		List<Article> newArts = articleService.getNewArticle(5);                 
		requset.setAttribute("lasts", newArts);
		
		
		 // 获取友情链接
		List<Link> friendLinks = articleService.getFriendLinks(5);      
		requset.setAttribute("links", friendLinks);
		
		
		//专题分页显示
		List<Special> specials = specialService.getSpecial();
		for (Special special : specials) {
			List<Article> articleList = special.getArticleList();
			int size = articleList.size();
			if (size > 5) {
				for (int i = 4; i < size-1; i++) {
					articleList.remove(i);
				}
				special.setArticleList(articleList);
			}
		}
		requset.setAttribute("specials", specials);

		
		return "index";
	}
	
	
	
}

