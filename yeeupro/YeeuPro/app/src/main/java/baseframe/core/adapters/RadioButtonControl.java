package baseframe.core.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import baseframe.tools.Util;

/**
 *N组图+文字选择控制
 */
public class RadioButtonControl{

    private ArrayList<ViewGroup> groups=new ArrayList<ViewGroup>();
    private ArrayList<Bitmap> selectedBitmaps=new ArrayList<Bitmap>();
    private IRadioButtonControlAdapter adapter;
    private int imgId;
    private int txtId;
    private ViewGroup currentSel;


    public void setAdapter(ArrayList<ViewGroup> groups,int imgId,int txtId,IRadioButtonControlAdapter adapter)
    {
        this.groups=groups;
        this.imgId=imgId;
        this.txtId=txtId;
        this.adapter=adapter;

        ViewGroup group;
        for(int i=0;i<groups.size();i++)
        {
            group=groups.get(i);
            ImageView img=(ImageView)group.findViewById(imgId);
            selectedBitmaps.add(Util.getColorBitmap(((BitmapDrawable)img.getDrawable()).getBitmap(),adapter.getSelectedImageColor()));

            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    click((ViewGroup)v);

                }
            });
        }
    }
    private void click(ViewGroup group)
    {
        if(currentSel!=group)
        {
            //修改上一次记录


            currentSel=group;
            TextView txt=(TextView)group.findViewById(txtId);
            ImageView img=(ImageView)group.findViewById(imgId);
//            img.setImageDrawable();

        }
    }




    public interface IRadioButtonControlAdapter
    {
        int getSelectedImageColor();
        int getSelectedTextColor();
        void onSelectedChange(int index);
    }






}
