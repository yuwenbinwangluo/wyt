package ui.modes;

import java.io.Serializable;

public class DistrictData  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int upid;
	private int level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUpid() {
		return upid;
	}
	public void setUpid(int upid) {
		this.upid = upid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
