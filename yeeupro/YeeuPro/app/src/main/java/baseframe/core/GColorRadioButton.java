package baseframe.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.yu.wyt.R;

import baseframe.core.adapters.BitmapColorTransform;

/**
 * 颜色改变单选按钮(目前只支持top)
 */
public class GColorRadioButton extends RadioButton implements CompoundButton.OnCheckedChangeListener{


    private BitmapColorTransform bColorTransform;
    private Bitmap oriBitmap;
    private OnColorCheckedChangeListener mListener;

    public GColorRadioButton(Context context) throws Exception {
        this(context, null);
    }


    public GColorRadioButton(Context context, AttributeSet attributeSet) throws Exception
    {
        super(context,attributeSet);

        TypedArray attr=context.obtainStyledAttributes(attributeSet, R.styleable.BitmapColorTransform,0,0);
        final int color= attr.getColor(R.styleable.BitmapColorTransform_tcolor, Color.GRAY);

        Drawable[] drawables=getCompoundDrawables();
        BitmapDrawable drawable=(BitmapDrawable)drawables[1];//top
        if(drawable!=null) {
            oriBitmap = drawable.getBitmap();

            bColorTransform=BitmapColorTransform.create();
            bColorTransform.setAdapter(new BitmapColorTransform.IBitmapColorAdapter() {
                @Override
                public Bitmap getBitmap() {
                    return oriBitmap;
                }

                @Override
                public int getColor() {
                    return color;
                }

                @Override
                public void setBitmap(Bitmap bitmap) {

                    onCheckedChanged(null,isChecked());

                }
            });

        }else{

            throw new Exception("need BitmapDrawable");

        }
        attr.recycle();
    }
    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        Log.w(getClass().getName(),"can not to setOnCheckedChangeListener");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        Bitmap bitmap;
        if(isChecked)
        {
            bitmap=bColorTransform.getAfterBitmap();
        }else{
            bitmap=oriBitmap;
        }
        BitmapDrawable bitmapDrawable=new BitmapDrawable(getResources(),bitmap);
        bitmapDrawable.setBounds(0,0,bitmapDrawable.getMinimumWidth(),bitmapDrawable.getMinimumHeight());
        setCompoundDrawables(null,bitmapDrawable,null,null);
        if(mListener!=null)
        {
            mListener.onCheckedChanged(buttonView,isChecked);
        }
    }

    public void setOnColorCheckedChangeListener(OnColorCheckedChangeListener listener)
    {
        mListener=listener;
        super.setOnCheckedChangeListener(this);
    }


    public interface OnColorCheckedChangeListener extends OnCheckedChangeListener
    {
        void OnColorCheckedChange(GColorRadioButton button,boolean isChecked);
    }


}
