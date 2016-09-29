package baseframe.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="dyr_lottery_log")
public class LotteryLog {
	private int id;
	private int mid;//用户Id
	private String info;//获奖记录提示
	private int date;//抽取时间记录
	private int cid;//奖品id
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}

}
