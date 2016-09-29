package baseframe.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class MarketProductData{
	
	@Id
	private int id=0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String pid="'";
	private String title="";
	private String name="";
	private String desp="";
	private String image_server_host="";
	private String image_server_name="";
	private String url="";
	private float price=0;
	
	
	///////////////
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getImage_server_name() {
		return image_server_name;
	}
	public void setImage_server_name(String imageServerName) {
		image_server_name = imageServerName;
	}
	public String getImage_server_host() {
		return image_server_host;
	}
	public void setImage_server_host(String imageServerHost) {
		image_server_host = imageServerHost;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}









	
	
	
	
	
	
	
	


}
