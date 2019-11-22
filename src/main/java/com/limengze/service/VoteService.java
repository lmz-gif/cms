package com.limengze.service;

import java.util.List;

import com.limengze.entity.Article;

/**
  * @author lmz
  * @date 2019年10月29日 下午6:38:38
  * 
  */

public interface VoteService {

	boolean add(String title, String json, Integer id);

	List<Article> getVotes(Integer integer);

}
