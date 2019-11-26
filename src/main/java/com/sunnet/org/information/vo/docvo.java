package com.sunnet.org.information.vo;

public class docvo {
	private String docid;
	private int countgood;//点赞次数
	private int dockeepid;//收藏次数
	private int docpayid;//打赏次数
    private int doccommentid;//评论次数
    private int docselect;//查看次数
	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public int getCountgood() {
		return countgood;
	}

	public void setCountgood(int countgood) {
		this.countgood = countgood;
	}

	public int getDockeepid() {
		return dockeepid;
	}

	public void setDockeepid(int dockeepid) {
		this.dockeepid = dockeepid;
	}

	public int getDocpayid() {
		return docpayid;
	}

	public void setDocpayid(int docpayid) {
		this.docpayid = docpayid;
	}

	public int getDoccommentid() {
		return doccommentid;
	}

	public void setDoccommentid(int doccommentid) {
		this.doccommentid = doccommentid;
	}

	public int getDocselect() {
		return docselect;
	}

	public void setDocselect(int docselect) {
		this.docselect = docselect;
	}

}
