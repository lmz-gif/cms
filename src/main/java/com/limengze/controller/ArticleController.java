package com.limengze.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.limengze.entity.Article;
import com.limengze.entity.ArticleType;
import com.limengze.entity.Category;
import com.limengze.entity.Channel;
import com.limengze.service.ArticleService;
import com.limengze.service.CategoryService;
import com.limengze.service.ChannelService;

/**
 * @author lmz
 * @Date 2019年10月18日
 * 
 */

@RequestMapping("article")
@Controller
public class ArticleController {
	
	@Autowired
	ArticleService arts;
	
	@Autowired
	CategoryService cats;
	
	@Autowired
	ChannelService chns;
	
	
	/**
	 * 获取文章内容
	 * @param request
	 * @param aId
	 * @return
	 */
	@RequestMapping("getDetail")
	public String getDetail(HttpServletRequest request, Integer aId) {
		Article article = arts.getDetail(aId);        // 根据文章ID获取文章
		
		if (article.getArticleType() == ArticleType.TEXT) {
			request.setAttribute("article", article);
			return "article/detail";
		} else {
			Gson gson = new Gson();
			article.setImgList(gson.fromJson(article.getContent(), List.class));
			request.setAttribute("article", article);
			return "article/imgdetail";
		}
		
	}
	
	
	
	/**
	 * Ajax获取频道列表
	 * @return
	 */
	@GetMapping("getAllChnl")
	@ResponseBody
	public List<Channel> getAllChnl() {
		List<Channel> channels = chns.getAllChannel();
		return channels;
	}
	
	
	/**
	 * 二级联动获取分类列表
	 * @param chnlId
	 * @return
	 */
	@GetMapping("getCatByChnl")
	@ResponseBody
	public List<Category> getCatByChnl(Integer chnlId) {
		List<Category> categories = cats.getListByChnlId(chnlId);
		return categories;
	}
	
	
	/**
	 * 	修改回显
	 * @param request
	 * @param id       文章ID
	 * @return
	 */
	@GetMapping("update")
	public String toUpdate(HttpServletRequest request, Integer id) {
		Article article = arts.getDetail(id);                 // 根据ID获取文章
		request.setAttribute("article", article);
		request.setAttribute("content1", article.getContent());
		return "my/update";
	}
	
}

