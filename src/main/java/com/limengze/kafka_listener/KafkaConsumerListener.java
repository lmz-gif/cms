package com.limengze.kafka_listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.limengze.entity.Article;
import com.limengze.service.UserService;

/**
  * @author lmz
  * @date 2019年11月20日 下午2:32:26
  * 
  */
@Component("kafkaConsumerListener")
public class KafkaConsumerListener implements MessageListener<String, String>{

	@Autowired
	UserService userService;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		Gson gson = new Gson();
		if (data.key()!=null && "article".equals(data.key())) {
			String value = data.value();
			Article article = gson.fromJson(value, Article.class);
			userService.publish(article);
		}
		
	}

}
