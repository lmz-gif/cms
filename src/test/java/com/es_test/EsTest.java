package com.es_test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.limengze.dao.ArticleMapper;
import com.limengze.entity.Article;
import com.lmz.utils.DateUtils;
import com.lmz.utils.FileUtils;
import com.lmz.utils.NumberUtils;

/**
 * @author lmz
 * @date 2019年11月21日 下午4:01:20
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class EsTest {
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	@Test
	public void kafkaAdd() throws IOException, ParseException {
		
		// (1)使用工具包中流工具方法读取文件，不得乱码。（4分）
		List<String> fileList = FileUtils.getFileList("D:\\1706EJsoup");
		for (String string : fileList) {
			Article article = new Article();
			// (2)将文件名作为Article对象的title属性值。（2分）
			article.setTitle(string.substring(string.lastIndexOf("\\")+1,string.lastIndexOf(".")));
			
			// (3)文本内容作为Article对象的content属性值。（2分）
			String content = FileUtils.readFileByLine(string);
			article.setContent(content);
			
			// (4)在文本内容中截取前140个字作为摘要。（2分）
			if (content!=null&&content.length()>140) {
				article.setSummary(content.substring(0, 140));
			}else {
				article.setSummary(content);
			}
			
			// (5)“点击量”和“是否热门”、“频道”字段要使用随机值。（2分）
			article.setHits(NumberUtils.random(0, 100000));
			article.setHot(NumberUtils.random(0, 10));
			article.setChannelId(NumberUtils.random(1, 8));
			
			// (6)文章发布日期从2019年1月1日模拟到今天。
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse("2018-07-01");
			article.setCreated(DateUtils.randomDate(date, new Date()));
			
			// (8)编写Kafka生产者，然后将生成Article对象通过Kafka发送到消费端。
			Gson gson = new Gson();
			String json = gson.toJson(article);
			kafkaTemplate.sendDefault("article",json);
			
		}
	}
	
	
	@Test
	public void name() {
		List<Article> articles = articleMapper.getArticles();
		IndexQuery query = new IndexQuery();
		for (Article article : articles) {
			query.setId(article.getId()+"");
			query.setObject(article);
			String index = elasticsearchTemplate.index(query);
			System.out.println("运行完毕"+index);
		}
	}

}
