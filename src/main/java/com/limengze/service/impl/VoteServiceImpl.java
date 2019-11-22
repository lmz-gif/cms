package com.limengze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.limengze.dao.VoteMapper;
import com.limengze.entity.Article;
import com.limengze.service.VoteService;

/**
  * @author lmz
  * @date 2019年10月29日 下午6:39:12
  * 
  */
@Component
public class VoteServiceImpl implements VoteService{
	@Autowired
	VoteMapper voteMapper;
	
	@Override
	public boolean add(String title, String json, Integer id) {
		if (voteMapper.add(title, json, id)>0) {
			return true;
		}else {
			return false;
			
		}
	}

	/**
	 *	查询所有该用户投票
	 */
	@Override
	public List<Article> getVotes(Integer userId) {
		return voteMapper.getVotes(userId);
	}

}
