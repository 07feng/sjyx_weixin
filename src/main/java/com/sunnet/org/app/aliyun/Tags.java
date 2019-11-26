package com.sunnet.org.app.aliyun;

public class Tags {
	private Double confidence;//是对应分数，范围0-100。
	private String value;//标签名称
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Tags(Double confidence, String value) {
		super();
		this.confidence = confidence;
		this.value = value;
	}
	public Tags() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
