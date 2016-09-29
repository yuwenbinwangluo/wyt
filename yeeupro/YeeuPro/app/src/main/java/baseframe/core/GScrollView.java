package baseframe.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import baseframe.base.IBaseObject;

public class GScrollView extends ScrollView implements IBaseObject {
	
    private View inner;//
    private float y;//
    private Rect normal = new Rect();//
    private boolean isCount = false;//
    private float xDistance,yDistance,xLast,yLast;

	public GScrollView(Context context) {
		super(context);
	}
    public GScrollView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public GScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }
    public boolean onGenericMotion(MotionEvent event)
    {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0.0f;
                xLast = event.getX();
                yLast = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = event.getX();
                final float curY = event.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);

                if(yDistance>xDistance)
                    return true;
                break;
            default:
                break;
        }
        return false;
    }

    @Override  
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onGenericMotion(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
	@Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        if (inner != null) {
        	commOnTouchEvent(ev);  
        }  
        return super.onTouchEvent(ev);  
    }  
  

    public void commOnTouchEvent(MotionEvent ev) {  
        int action = ev.getAction();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            break;  
        case MotionEvent.ACTION_UP:  

            if (isNeedAnimation()) {  
                animation();  
                isCount = false;  
            }  
            break;  

        case MotionEvent.ACTION_MOVE:  
            final float preY = y;//
            float nowY = ev.getY();//
            int deltaY = (int) (preY - nowY);//
            if (!isCount) {  
                deltaY = 0; //
            }  
  
            y = nowY;  
            //
            if (isNeedMove()) {  
                //
                if (normal.isEmpty()) {  
                    //
                    normal.set(inner.getLeft(), inner.getTop(),inner.getRight(), inner.getBottom());  
                }  
                //
                inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,inner.getRight(), inner.getBottom() - deltaY / 2);  
            }  
            isCount = true;  
            break;  
  
        default:  
            break;  
        }  
    }  
  
    /*** 
     *
     */  
    public void animation() {  
        //
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),normal.top);  
        ta.setDuration(200);  
        inner.startAnimation(ta);  
        //
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);  
        normal.setEmpty();  
    }  
  
    //
    public boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  
  

    public boolean isNeedMove() {  
        int offset = inner.getMeasuredHeight() - getHeight();  
        int scrollY = getScrollY();  
        //
        if (scrollY == 0 || scrollY == offset) {  
            return true;  
        }  
        return false;  
    }
	@Override
	public void dispose() {
		inner=null;
		
	}  
	
	
	

}
