package baseframe.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//开放地区
@Entity(name="dyr_position")
public class AreaPosition {
	private int id;
	private String name;
	private int sort;
	private String remark;
	
	@Id
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
