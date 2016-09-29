package baseframe.core.adapters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import baseframe.base.IBaseObject;
import baseframe.core.GFloatColor;
import baseframe.tools.Util;

/**
 * 颜色变换
 */
public class BitmapColorTransform implements IBaseObject{

    private float cR=1;
    private float cG=1;
    private float cB=1;
    private float cA=1;
    //定义RGBA的矩阵
    float[] src = new float[]{
            cR, 0, 0, 0, 0,
            0, cG, 0, 0, 0,
            0, 0, cB, 0, 0,
            0, 0, 0, cA, 0};
    private IBitmapColorAdapter adapter;
    private Bitmap afterBitmap;

    private BitmapColorTransform()
    {
    }

    public static BitmapColorTransform create()
    {
        return new BitmapColorTransform();
    }


    public void setAdapter(IBitmapColorAdapter adapter)
    {
        this.adapter=adapter;
        setColorBitmap();
    }
    private void setColorBitmap()
    {
        GFloatColor color= Util.ConvertIntToColor32(adapter.getColor());
        src[0]=color.getR();
        src[6]=color.getG();
        src[12]=color.getB();
        src[18]=color.getA();
        Bitmap srcBitmap=adapter.getBitmap();
        afterBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(afterBitmap);
        Paint paint=new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(src));
        canvas.drawBitmap(srcBitmap,new Matrix(),paint);
        adapter.setBitmap(afterBitmap);
    }
    public Bitmap getAfterBitmap()
    {
        return afterBitmap;
    }


    public interface IBitmapColorAdapter
    {
        //获取需要变换的图片
        Bitmap getBitmap();
        //获取要变换的颜色
        int getColor();
        //作用效果
        void setBitmap(Bitmap bitmap);
    }
    @Override
    public void dispose()
    {
        if(afterBitmap!=null)
        {
            Util.disposeObjectWithData(afterBitmap);
            afterBitmap=null;
        }
    }


}
