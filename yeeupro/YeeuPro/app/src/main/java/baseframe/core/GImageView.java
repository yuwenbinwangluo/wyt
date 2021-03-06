package baseframe.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yu.wyt.R;

import java.util.ArrayList;

import baseframe.base.IBaseObject;
import baseframe.config.BaseConfig;
import baseframe.tools.Util;


/**
 * 
 * @author yu
 * 图片控件-增加描边功能
 */
public class GImageView extends ImageView implements IBaseObject {
    private int _mBorderColor = Color.RED;
    private int _mBoderSize = BaseConfig.NUM_1;
    private boolean isBorderEnable = false;
    private int _mBorderDir=BaseConfig.LEFT|BaseConfig.TOP|BaseConfig.RIGHT|BaseConfig.DOWN;
    private Paint _mPaint=null;
    public Handler loadHandler=null;
	
	
	
	
	public GImageView(Context context) {
		super(context);
	}
	public GImageView(Context context,AttributeSet attrs){
		super(context,attrs);
		initAttr(attrs);
	}
	public GImageView(Context context,AttributeSet attrs,int defStyleAttr){
		super(context,attrs,defStyleAttr);
		initAttr(attrs);
	}
	private void initAttr(AttributeSet attrs){
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.Border,0,0);
        _mBorderColor=attr.getColor(R.styleable.Border_b_color, Color.BLACK);
        _mBoderSize=attr.getDimensionPixelSize(R.styleable.Border_size, BaseConfig.NUM_1);
        boolean _isBorderEnable=attr.getBoolean(R.styleable.Border_enable, false);
        if(_isBorderEnable){
        	setBorderEnable(_isBorderEnable);
        }
        attr.recycle();
	}
	
	@SuppressLint("HandlerLeak")
	public void load(String url){
		if(loadHandler==null){
			final GImageView gimage=this;
			loadHandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					LoadFile file=(LoadFile)msg.obj;
					@SuppressWarnings("unchecked")
					ArrayList<Object> arr=(ArrayList<Object>)(file.data);
					if(msg.what==BaseConfig.LOAD_RESOURCE){
						gimage.setImageBitmap((Bitmap)(arr.get(0)));
					}
				}
			};
		}
		Util.loadOneResource(url, BaseConfig.LOAD_RESOURCE, loadHandler);
	}


	//获取图片资源 bitmap
	public Bitmap getBitmap(String filename,String defPackageName)
	{
		int identifier=getResources().getIdentifier(filename, "mipmap", defPackageName);
		return BitmapFactory.decodeResource(getResources(), identifier);
	}
	//设置图片资源
	public void setImage(int resourceId)
	{
		super.setImageDrawable(ContextCompat.getDrawable(getContext(), resourceId));

	}
	
	
	
	
	public void setBorderEnable(boolean borderEnable) {
		if (isBorderEnable != borderEnable) {
			isBorderEnable = borderEnable;
			if (isBorderEnable) {
				if (_mPaint == null) {
					_mPaint = new Paint();
					_mPaint.setColor(_mBorderColor);
				}
			}
			invalidate();
		}
	}

	public void setBorderSize(int size) {
		if (_mBoderSize != size) {
			_mBoderSize = size;
			invalidate();
		}
	}

	public void setBorderColor(int color) {
		if (_mBorderColor != color) {
			_mBorderColor = color;
			_mPaint.setColor(_mBorderColor);
			invalidate();
		}
	}
	public void setBorderDir(int dir) {
		if (_mBorderDir != dir) {
			_mBorderDir = dir;
			invalidate();
		}
	}
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(isBorderEnable){
			if(_mBoderSize>0){
		        int vw = getWidth();
		        int vh = getHeight();
		        if(Util.isSuit(_mBorderDir, BaseConfig.LEFT)){
		            Util.tempRect.setEmpty();
		            Util.tempRect.set(0, 0, _mBoderSize, vh);
		            canvas.drawRect(Util.tempRect, _mPaint);
		        }
		        if(Util.isSuit(_mBorderDir, BaseConfig.TOP)){
		            Util.tempRect.setEmpty();
		            Util.tempRect.set(0, 0,vw, _mBoderSize);
		            canvas.drawRect(Util.tempRect, _mPaint);
		        }
		        if(Util.isSuit(_mBorderDir, BaseConfig.RIGHT)){
		            Util.tempRect.setEmpty();
		            Util.tempRect.set(vw - _mBoderSize, 0, vw, vh);
		            canvas.drawRect(Util.tempRect, _mPaint);
		        }
		        if(Util.isSuit(_mBorderDir, BaseConfig.DOWN)){
		            Util.tempRect.setEmpty();
		            Util.tempRect.set(0, vh - _mBoderSize, vw, vh);
		            canvas.drawRect(Util.tempRect, _mPaint);
		        }
			}
		}
	}
	@Override
	public void dispose() {
		Util.removeHandler(loadHandler);
		loadHandler=null;
		
		_mPaint=null;
		_mBorderDir=BaseConfig.LEFT|BaseConfig.TOP|BaseConfig.RIGHT|BaseConfig.DOWN;
		setImageBitmap(null);
		if(getParent()!=null){
			((ViewGroup)(getParent())).removeView(this);
		}

	}
	
	
	
	
	
	
	

}
