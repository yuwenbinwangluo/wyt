package baseframe.core;

import android.os.Handler;
import baseframe.base.RefObject;

public class LoadFile extends RefObject {
	public static final String F_JPG=".jpg";
	public static final String F_PNG=".png";
	public static final String F_JSON=".json";
	
	
	
	public int id=0;
	public int groupID=0;
	public String url="";
	public String name="";
	public String fileDirName="";
	public String fileType="";
	public Object data=null;
    public Handler handler=null;
    public LoadCompleteListener listener=null;
    public boolean isSaveLocal=true;
    
    @Override
	public void dispose(){
		id=0;
		groupID=0;
		url="";
		name="";
		fileDirName="";
		fileType="";
		data=null;
		handler=null;
		listener=null;
		isSaveLocal=true;
		super.dispose();
	}
}
