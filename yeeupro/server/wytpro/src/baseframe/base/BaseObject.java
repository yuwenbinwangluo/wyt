package baseframe.base;

public class BaseObject implements IBaseObject{
    public static byte ACTIVED = 0;
    public static byte NO_ACTIVED = 1;
    public static byte WAIT_DESTROY = 2;
    public static short WAIT_DESTROY_TIME = 500;
	
    public byte actived = 1;
    public short time=0;
	
	public void dispose(){
		 actived = BaseObject.WAIT_DESTROY;
		 time=BaseObject.WAIT_DESTROY_TIME;
	}
}
