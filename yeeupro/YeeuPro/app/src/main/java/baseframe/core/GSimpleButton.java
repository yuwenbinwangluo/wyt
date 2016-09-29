package baseframe.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;

import com.yu.wyt.R;

import baseframe.config.FormatConfig;
import baseframe.tools.Util;




public class GSimpleButton extends GFrameLayout implements OnTouchListener{

	public int indexId=-1;
	protected OnClickListener clickListener=null;
	protected int upFName=-1;
	protected int downFName=-1;
	protected GImageView bg=null;
	protected GTextField txt=null;
	protected String _mText="";
	protected String _mfontFamily="";
	protected float _mfontSize=12;
	protected int _mfontColor=Color.BLACK;
	protected int _mGravity=Gravity.CENTER;
	protected int _mtx=Integer.MAX_VALUE;
	protected int _mty=Integer.MAX_VALUE;
	



	public GSimpleButton(Context context) {
		super(context);
        initMember(context);
	}

	public GSimpleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAtt(context,attrs);
	}

	public GSimpleButton(Context context, AttributeSet attrs,int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAtt(context,attrs);
	}

	protected void initAtt(Context context, AttributeSet attrs){
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleButton,0,0);
        upFName=attr.getResourceId(R.styleable.SimpleButton_upSrc, 0);
        downFName=attr.getResourceId(R.styleable.SimpleButton_downSrc, 0);
        _mText=attr.getString(R.styleable.SimpleButton_text);
        _mtx=attr.getInt(R.styleable.SimpleButton_tx, Integer.MAX_VALUE);
        _mty=attr.getInt(R.styleable.SimpleButton_ty, Integer.MAX_VALUE);
        if(_mText!=""&&_mText!=null){
            _mfontFamily=attr.getString(R.styleable.TextFormat_fontFamily);
            _mfontSize=attr.getDimension(R.styleable.TextFormat_fontSize,12);
            _mfontColor=attr.getColor(R.styleable.TextFormat_fontColor, Color.BLACK);
        }
        attr.recycle();
        initMember(context);
        bg.setImageResource(upFName);
	}
	protected void initMember(Context context){
        bg=new GImageView(context);
        setBtnState(false);
        initText(context);
    	addEvent();
	}
	private void initText(Context context){
	     if(_mText!=""&&_mText!=null){
		     txt=new GTextField(context);
		     txt.setText(_mText);
		     txt.setTextColor(_mfontColor);
		     txt.setTextSize(_mfontSize);
	    }
	}
	public void setSrc(int upFName,int downFName){
		this.upFName=upFName;
		this.downFName=downFName;
	    if(downFName==-1){
	        bg.setImageResource(upFName);
	    }
        setBtnState(false);
	}
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		FrameLayout.LayoutParams param=null;
		if(bg.getParent()==null){
		    param=new LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight());
		    bg.setScaleType(ScaleType.FIT_XY);
		    addView(bg,param);
		}
		if(txt!=null&&txt.getParent()==null){
		    param=new LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight());
            if(_mtx==Integer.MAX_VALUE&&_mty==Integer.MAX_VALUE){
            	txt.setGravity(_mGravity);
		    }else{
		    	param.leftMargin=_mtx;
		    	param.topMargin=_mty;
		    }
		    addView(txt,param);
		}
	}
	protected void addEvent(){
		setOnTouchListener(this);
	}
	protected void removeEvent(){
        setOnTouchListener(null);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setBtnState(true);
			break;
		case MotionEvent.ACTION_UP:
			setBtnState(false);
			if(clickListener!=null){
				clickListener.onClick(v);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			setBtnState(false);
			break;
		}
		return true;
	}
	@Override
	public void setOnClickListener(OnClickListener l){
		this.clickListener=l;
	}
	public void setBtnState(boolean isdown){
		if(downFName!=-1){
			if(isdown){
			     bg.setImageResource(downFName);
			}else{
				bg.setImageResource(upFName);
			}
		}else{
			if(isdown){
				bg.setColorFilter(FormatConfig.depeen);
			}else{
				bg.clearColorFilter();
			}
		}
	}
	
	
	public String get_mText() {
		return _mText;
	}

	public void set_mText(String _mText) {
		this._mText = _mText;
	     if(txt==null){
	         initText(getContext());
	     }
	}

	public int get_mtx() {
		return _mtx;
	}

	public void set_mtx(int _mtx) {
		this._mtx = _mtx;
	}

	public int get_mty() {
		return _mty;
	}

	public void set_mty(int _mty) {
		this._mty = _mty;
	}
	public int get_mGravity() {
		return _mGravity;
	}

	public void set_mGravity(int _mGravity) {
		this._mGravity = _mGravity;
	}
	public String get_mfontFamily() {
		return _mfontFamily;
	}

	public void set_mfontFamily(String _mfontFamily) {
		this._mfontFamily = _mfontFamily;
	}

	public float get_mfontSize() {
		return _mfontSize;
	}

	public void set_mfontSize(float _mfontSize) {
		this._mfontSize = _mfontSize;
	}

	public int get_mfontColor() {
		return _mfontColor;
	}

	public void set_mfontColor(int _mfontColor) {
		this._mfontColor = _mfontColor;
	}

	
	@Override
	public void dispose() {
		removeEvent();
		Util.disposeObject(bg);
		bg=null;
		Util.disposeObject(txt);
		txt=null;
		
		clickListener=null;
		upFName=-1;
		indexId=-1;
		downFName=-1;
		_mText="";
		_mtx=Integer.MAX_VALUE;
		_mty=Integer.MAX_VALUE;
		_mGravity=Gravity.CENTER;
		
		super.dispose();

	}





}
