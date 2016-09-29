package baseframe.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//奖品配置
@Entity(name="dyr_couponconfig")
public class CouponConfig {
	private int id;
	//状态，0关闭，1开启
	private int status;
	//获奖几率%
	private float rate;
	//每日抽奖免费次数
	private int count;
	private String start_time;
	private String end_time;
	//是否开始时间抽奖 0 关闭 1开启
	private int istime;
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getIstime() {
		return istime;
	}
	public void setIstime(int istime) {
		this.istime = istime;
	}

}
