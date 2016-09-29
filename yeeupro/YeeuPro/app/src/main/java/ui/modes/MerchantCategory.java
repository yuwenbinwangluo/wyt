package ui.modes;

import java.io.Serializable;

//商家分类
public class MerchantCategory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	
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

	
	
	
}
