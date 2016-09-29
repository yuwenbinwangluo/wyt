package baseframe.db.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//商家分类
@Entity(name="dyr_category")
public class MerchantCategory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
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
