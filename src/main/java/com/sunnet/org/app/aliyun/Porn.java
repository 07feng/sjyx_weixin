package com.sunnet.org.app.aliyun;

import java.util.List;

/**
 * 鉴黄
 * @author rq1
 *
 */
public class Porn {
	private List<Tags> tags;//tags输出，对应正常、性感、色情三个标签的输出分数，
	//每个标签会输出“confidence”和“value”两个参数，value是标签名称，confidence是对应分数，范围0-100。
	//输出标签数量最少1个，最多3个，如果某个类别标签未输出，则对应的confidence为零。
	private int errno;//0为成功，非0为失败，详细说明参加错误码
	private String request_id;//request_id 请求id信息
	private String err_msg;//处理失败时的说明
	public List<Tags> getTags() {
		return tags;
	}
	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public Porn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Porn(List<Tags> tags, int errno, String request_id, String err_msg) {
		super();
		this.tags = tags;
		this.errno = errno;
		this.request_id = request_id;
		this.err_msg = err_msg;
	}
	
	
}
