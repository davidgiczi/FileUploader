package com.david.giczi.Softmagic.model;

public class Link {

	private int id;
	private String href;
	private String title;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Link [id=" + id + ", href=" + href + ", title=" + title + "]";
	}
	
}
