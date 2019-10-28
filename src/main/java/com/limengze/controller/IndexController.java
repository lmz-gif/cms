package com.limengze.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.limengze.comons.PageUtils;
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
		
		// 获取所有的频道
		List<Channel> channels = channelService.getAllChannel();
		
		if (chnId != 0) {                                              // 如果频道的id不为0,即不为首页
			List<Category> categories = categoryService.getListByChnlId(chnId);   // 获取该频道下的分类列表
			requset.setAttribute("categories", categories);
			PageInfo<Article> articles = articleService.getArtList(chnId, catId, pageNum);    // 获取分类下的文章列表
			requset.setAttribute("articles", articles);
		} else {                                                       // 否则即为首页
			PageInfo<Article> hotArticle = articleService.getHotArticle(pageNum);// 获取热门文章放置到首页
			requset.setAttribute("articles", hotArticle);
		}
		
		List<Article> newArts = articleService.getNewArticle(5);                 // 获取最新文章
		List<Link> friendLinks = articleService.getFriendLinks(5);       // 获取友情链接
		
		List<Special> specials = specialService.getSpecial();
		requset.setAttribute("specials", specials);

		requset.setAttribute("lasts", newArts);
		requset.setAttribute("links", friendLinks);
		
		requset.setAttribute("chnId", chnId);                          // 频道id
		requset.setAttribute("catId", catId);                          // 分类id
		
		requset.setAttribute("channels", channels);
		return "index";
	}
	
	
	
}

