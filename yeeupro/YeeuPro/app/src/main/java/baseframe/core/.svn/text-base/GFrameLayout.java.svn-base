package baseframe.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import baseframe.base.IBaseObject;

public class GFrameLayout extends FrameLayout implements IBaseObject{

	public GFrameLayout(Context context) {
		super(context);
	}
	public GFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public GFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	
	
	
	public void dispose(){
		if(getParent()!=null){
			((ViewGroup)(getParent())).removeView(this);
		}
	}
	

}
