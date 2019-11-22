package com.limengze.entity;

import java.io.Serializable;

/**
  * @author lmz
  * @date 2019年10月29日 下午6:25:08
  * 
  */

public class Option implements Serializable{
	private static final long serialVersionUID = 5432859664998934347L;
	private String optionKey;
	private String optionTitle;
	private String voteNum;
	private String voteTotal;
	public Option() {
		super();
	}
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	public String getVoteNum() {
		return voteNum;
	}
	public void setVoteNum(String voteNum) {
		this.voteNum = voteNum;
	}
	public String getVoteTotal() {
		return voteTotal;
	}
	public void setVoteTotal(String voteTotal) {
		this.voteTotal = voteTotal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((optionKey == null) ? 0 : optionKey.hashCode());
		result = prime * result + ((optionTitle == null) ? 0 : optionTitle.hashCode());
		result = prime * result + ((voteNum == null) ? 0 : voteNum.hashCode());
		result = prime * result + ((voteTotal == null) ? 0 : voteTotal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (optionKey == null) {
			if (other.optionKey != null)
				return false;
		} else if (!optionKey.equals(other.optionKey))
			return false;
		if (optionTitle == null) {
			if (other.optionTitle != null)
				return false;
		} else if (!optionTitle.equals(other.optionTitle))
			return false;
		if (voteNum == null) {
			if (other.voteNum != null)
				return false;
		} else if (!voteNum.equals(other.voteNum))
			return false;
		if (voteTotal == null) {
			if (other.voteTotal != null)
				return false;
		} else if (!voteTotal.equals(other.voteTotal))
			return false;
		return true;
	}
	
	
}
