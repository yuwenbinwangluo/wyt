package baseframe.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//商户[注意：数据库text类型 默认是varchar,如果是mysql关键字则需要加 \"括起来]
@Entity(name="dyr_merchant")
public class Merchant implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private int uid;
	private String name;
	private String addr;
	private int c_id;
	private String telnumber;
	private String type;//商家类型
	private int status;
	private String desp;
	private String pic;
	private String thumbnail;
	private int reg_time;
	private int expire_time;
	private int position;//开放地区如 怀来。。
	private int province;
	private int city;
	private int district;
	private int community;
	private String point;
	private String qq;
	private String weixin;
	private int index;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	@Column(name="name",columnDefinition="varchar")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="addr",columnDefinition="text")
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	@Column(name="telnumber",columnDefinition="varchar")
	public String getTelnumber() {
		return telnumber;
	}
	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
	}
	@Column(name="type",columnDefinition="varchar")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="\"desc\"",columnDefinition="text")
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desc) {
		this.desp = desc;
	}
	@Column(name="pic",columnDefinition="text")
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	@Column(name="thumbnail",columnDefinition="text")
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getReg_time() {
		return reg_time;
	}
	public void setReg_time(int reg_time) {
		this.reg_time = reg_time;
	}
	public int getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public int getDistrict() {
		return district;
	}
	public void setDistrict(int district) {
		this.district = district;
	}
	public int getCommunity() {
		return community;
	}
	public void setCommunity(int community) {
		this.community = community;
	}
	@Column(name="point",columnDefinition="varchar")
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	@Column(name="qq",columnDefinition="varchar")
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Column(name="weixin",columnDefinition="varchar")
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	@Column(name="\"index\"",columnDefinition="int")
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
