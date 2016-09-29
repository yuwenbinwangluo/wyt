package baseframe.base;

import javax.persistence.Id;




public class BaseData extends BaseObject{
	
	@Id
	private long id=0;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}



	@Override
	public void dispose(){
		id=0;
		super.dispose();
	}
}
