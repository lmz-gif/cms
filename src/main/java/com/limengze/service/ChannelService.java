package com.limengze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limengze.dao.ChannelMapper;
import com.limengze.entity.Channel;

/**
 * @author lmz
 * @Date 2019年10月17日
 * 频道分类服务层
 */

@Service
public interface ChannelService {
	
	// 获取频道列表
	List<Channel> getAllChannel();
	
}

