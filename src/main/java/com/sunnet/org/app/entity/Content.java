package com.sunnet.org.app.entity;

public class Content {
	private String id;//文件id
	private String doctitle;//文件描述
	private int sort;

	 
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public Content(String id, String doctitle,int sort ) {
		super();
		this.id = id;
		this.doctitle = doctitle;
		this.sort = sort;
	}

	public Content() {
		super();
		// TODO Auto-generated constructor stub
	}

}
