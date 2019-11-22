package com.limengze.controller;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.limengze.commons.UserConst;
import com.limengze.entity.Article;
import com.limengze.entity.User;
import com.limengze.service.VoteService;

/**
  * @author lmz
  * @date 2019年10月29日 下午6:36:55
  * 
  */
@Controller
@RequestMapping("vote")
public class VoteController {
	@Autowired
	VoteService voteService;
	
	@GetMapping("push")
	public String push(HttpServletRequest request) {
		return "my/vote/add";
	}
	
	/**
	 * 发布投票
	 *
	 *
	 */
	@PostMapping("push")
	@ResponseBody
	public boolean push(HttpServletRequest request,String title,String content) {
		String[] split = content.split(",");
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		for (String str1 : split) {
			String[] split2 = str1.split(":");
			map.put(split2[0], split2[1]);
		}
		Gson gson = new Gson();
		String json = gson.toJson(map);
		User user = (User)request.getSession().getAttribute(UserConst.SESSION_USER_KEY);
		boolean b = voteService.add(title,json,user.getId());
		return b;
	}
	
	
	/**
	 * 投票列表
	 * 
	 */
	@GetMapping("list")
	public String list(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(UserConst.SESSION_USER_KEY);
		List<Article> votes = voteService.getVotes(user.getId());
		request.setAttribute("votes", votes);
		return "my/vote/list";
	}
}
