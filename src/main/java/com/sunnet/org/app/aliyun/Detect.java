package com.sunnet.org.app.aliyun;
/**
 * 人像识别
 * @author rq1
 *
 */
public class Detect {
	private int face_num;//检测出来的人脸个数
	private int errno;//0为成功，非0为失败，详细说明参加错误码
	private String request_id;//request_id 请求id信息
	private String err_msg;//处理失败时的说明
	public int getFace_num() {
		return face_num;
	}
	public void setFace_num(int face_num) {
		this.face_num = face_num;
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
	@Override
	public String toString() {
		return "detect [face_num=" + face_num + ", errno=" + errno
				+ ", request_id=" + request_id + ", err_msg=" + err_msg + "]";
	}
	public Detect(int face_num, int errno, String request_id, String err_msg) {
		super();
		this.face_num = face_num;
		this.errno = errno;
		this.request_id = request_id;
		this.err_msg = err_msg;
	}
	public Detect() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
