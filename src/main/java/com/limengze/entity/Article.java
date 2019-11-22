package com.limengze.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author lmz
 * @Date 2019年10月17日
 *  定义文章类
 */
@Document(indexName="test",type="article")
public class Article implements Serializable {

	private static final long serialVersionUID = 8885558194798598334L;

	private Integer id;
	//
	private String title;
	//
	private String content;
	//
	private String picture;
	
	//
	private Integer channelId;
	private Integer categoryId;
	
	//
	private Channel channel;            
	private Category category;          
	
	private Integer userId;
	// 多对一查询用户
	private User user;                  
	// 文章标签
	private String tags;                
	//点击量
	private Integer hits;
	//热度
	private Integer hot;
	//状态--是否禁用
	private Integer status;
	//是否已被逻辑删除
	private Integer deleted;
	//创建时间
	private Date created;
	//修改时间
	private Date updated;
	//评论量
	private Integer commentCnt;
	//关键字
	private String keywords;
	//来源
	private String original;
	//摘要
	private String summary=null;
	
	
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	private ArticleType articleType = ArticleType.TEXT;  // 文章类型类,默认值为TEXT
	
	private List<ImageBean> imgList;                     // 接收上传多个图片的集合
	
	
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the hits
	 */
	public Integer getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}

	/**
	 * @return the hot
	 */
	public Integer getHot() {
		return hot;
	}

	/**
	 * @param hot the hot to set
	 */
	public void setHot(Integer hot) {
		this.hot = hot;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the deleted
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the commentCnt
	 */
	public Integer getCommentCnt() {
		return commentCnt;
	}

	/**
	 * @param commentCnt the commentCnt to set
	 */
	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}
	

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the articleType
	 */
	public ArticleType getArticleType() {
		return articleType;
	}

	/**
	 * @param articleType the articleType to set
	 */
	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	

	/**
	 * @return the imgList
	 */
	public List<ImageBean> getImgList() {
		return imgList;
	}

	/**
	 * @param imgList the imgList to set
	 */
	public void setImgList(List<ImageBean> imgList) {
		this.imgList = imgList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + ", picture=" + picture
				+ ", channelId=" + channelId + ", channel=" + channel + ", categoryId=" + categoryId + ", category="
				+ category + ", userId=" + userId + ", user=" + user + ", tags=" + tags + ", hits=" + hits + ", hot="
				+ hot + ", status=" + status + ", deleted=" + deleted + ", created=" + created + ", updated=" + updated
				+ ", commentCnt=" + commentCnt + ", articleType=" + articleType + ", imgList=" + imgList + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
