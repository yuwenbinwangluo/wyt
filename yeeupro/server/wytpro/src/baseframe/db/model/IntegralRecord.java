package baseframe.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//中奖记录
@Entity(name="dyr_integral_record")
public class IntegralRecord {
	
	private int id;
	private String c_id;//用户的ID
	private String c_name;//用户名
	private int integral;//积分
	private String getmode;//获取方式
	private int time;//获取时间
	private int allintegral;//当前总共有多少积分
	private int type;//类型，1获取，2提现
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public String getGetmode() {
		return getmode;
	}
	public void setGetmode(String getmode) {
		this.getmode = getmode;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getAllintegral() {
		return allintegral;
	}
	public void setAllintegral(int allintegral) {
		this.allintegral = allintegral;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	

}
