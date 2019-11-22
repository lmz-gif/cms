package com.limengze.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.limengze.commons.ResultMsg;
import com.limengze.entity.Article;
import com.limengze.entity.Special;
import com.limengze.service.ArticleService;
import com.limengze.service.SpecialService;
import com.lmz.utils.StringUtils;

/**
 * @author lmz
 * @Date 2019年10月25日 专题文章控制层
 */

@Controller
@RequestMapping("special")
public class SpecialController {

	@Autowired
	SpecialService specialService;

	@Autowired
	ArticleService articleService;

	/**
	 * 专题管理主页面
	 * 
	 * @return
	 */
	@RequestMapping("managerSpecial")
	public String managerSpecial(HttpServletRequest request) {
		List<Special> specials = specialService.getSpecial();
		for (Special special : specials) {
			if (special.getArticleList() != null) {
				special.setArticleNum(special.getArticleList().size());
			} else {
				special.setArticleNum(0);
			}
		}
		request.setAttribute("specials", specials);
		return "admin/special/list";

	}

	/**
	 * 进入添加页面
	 *
	 */
	@GetMapping("addSpecial")
	public String addSpecial() {
		return "admin/special/add";
	}

	/**
	 * 添加专题
	 *
	 */
	@RequestMapping("addSpecial")
	@ResponseBody
	public ResultMsg addSpecial(Special special, HttpServletRequest request) {
		if (StringUtils.hasText(special.getTitle())) {
			ResultMsg msg = new ResultMsg(specialService.addSpecial(special), "添加失败", null);
			return msg;
		} else {
			ResultMsg msg = new ResultMsg(0, "修改失败", null);
			return msg;
		}
	}
	
	/**
	 * 添加专题
	 *
	 */
	@RequestMapping("delSpecial")
	public String delSpecial(Integer id, HttpServletRequest request) {
		specialService.delSpecial(id);
		return "redirect:managerSpecial";
	}

	/**
	 * 追加文章页面
	 *
	 */
	@GetMapping("addArticleToSpecial")
	public String addArticleToSpecial(Integer id, HttpServletRequest request) {

		Special special = specialService.getSpecialById(id);
		request.setAttribute("special", special);

		List<Article> articles = articleService.getArticles();
		request.setAttribute("articles", articles);
		return "admin/special/detail";
	}

	/**
	 * 追加文章
	 *
	 */
	@RequestMapping("addArticleToSpecial")
	@ResponseBody
	public ResultMsg addArticleToSpecial(Integer articleId, Integer specialId, HttpServletRequest request) {
		ResultMsg msg = new ResultMsg(specialService.addArticleToSpecial(articleId, specialId), "添加失败", null);
		System.out.println("aaa");
		return msg;
	}

	/**
	 * 删除文章
	 *
	 */
	@RequestMapping("delArticleFromSpecial")
	@ResponseBody
	public ResultMsg delArticleFromSpecial(Integer sid, Integer aid, HttpServletRequest request) {
		ResultMsg msg = new ResultMsg(specialService.delArticleFromSpecial(sid, aid), "删除失败", null);
		return msg;
	}

	/**
	 * 修改专题回显
	 * 
	 */
	@GetMapping("updateSpecial")
	public String updateSpecial(Integer id, HttpServletRequest request) {
		Special special = specialService.getSpecialById(id);
		request.setAttribute("special", special);
		return "admin/special/update";
	}

	/**
	 * 修改专题
	 * 
	 */
	@PostMapping("updateSpecial")
	@ResponseBody
	public ResultMsg updateSpecial(Special special) {
		if (StringUtils.hasText(special.getTitle())) {
			ResultMsg msg = new ResultMsg(specialService.updateSpecial(special), "修改失败", null);
			return msg;
		} else {
			ResultMsg msg = new ResultMsg(0, "修改失败", null);
			return msg;
		}
	}
}
