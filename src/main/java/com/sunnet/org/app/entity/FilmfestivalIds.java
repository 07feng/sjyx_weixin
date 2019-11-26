package com.sunnet.org.app.entity;

import java.util.List;

public class FilmfestivalIds {
	private List<Content> content;
	private String member_id;
	private String titel;//名称
	private String titelnote;//描述
	
	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTitelnote() {
		return titelnote;
	}

	public void setTitelnote(String titelnote) {
		this.titelnote = titelnote;
	}

	public FilmfestivalIds(List<Content> content, String member_id, String titel,
			String titelnote) {
		super();
		this.content = content;
		this.member_id = member_id;
		this.titel = titel;
		this.titelnote = titelnote;
		
	}

	public FilmfestivalIds() {
		super();
		// TODO Auto-generated constructor stub
	}

}
