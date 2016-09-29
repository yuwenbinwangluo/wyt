package baseframe.db.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//优惠券
@Entity(name="dyr_coupon")
public class Coupon implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String qrcode;
	private String name;
	private String type;
	private String value;
	private int period;//过期时间
	private int u_id;//商家的ID
	private int s_id;
	private int c_id;//用户id
	private String mark;
	private int usetime;//使用时间
	private int gettime;//领取时间
	private int status;//状态，-1删除，0禁用,1未使用，2已经使用
	private String key;//验证密钥
	private int position;//开放地区
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getUsetime() {
		return usetime;
	}
	public void setUsetime(int usetime) {
		this.usetime = usetime;
	}
	public int getGettime() {
		return gettime;
	}
	public void setGettime(int gettime) {
		this.gettime = gettime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
