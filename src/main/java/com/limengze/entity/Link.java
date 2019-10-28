package com.limengze.entity;

import java.io.Serializable;

/**
 * @author lmz
 * @Date 2019年10月24日 
 * 	友情链接类
 */

public class Link implements Serializable {

	private static final long serialVersionUID = 7171125240710343424L;

	private Integer id;
	private String title;    // 链接标题
	private String url;      // 链接地址

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	

	


	public Link() {
		super();
	}
	
}
