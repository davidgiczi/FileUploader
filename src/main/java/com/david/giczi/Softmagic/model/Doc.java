package com.david.giczi.Softmagic.model;

public class Doc {

	private int id;
	private String title;
	private String extension;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	@Override
	public String toString() {
		return "Doc [id=" + id + ", title=" + title + ", extension=" + extension + "]";
	}
	
}
