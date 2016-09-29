package baseframe.core;

import baseframe.base.IBaseObject;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class GTextField extends TextView implements IBaseObject {
	

	public GTextField(Context context) {
		super(context);
	}
	public GTextField(Context context,AttributeSet attrs) {
		super(context,attrs);
	}
	public GTextField(Context context,AttributeSet attrs,int defStyle) {
		super(context,attrs,defStyle);
	}
	

	
	public void dispose(){
		
	}

}
