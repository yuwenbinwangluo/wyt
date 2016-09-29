package ui.content;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yu.wyt.R;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.core.GActivity;
import baseframe.core.GScrollView;
import baseframe.net.AfinalManager;
import baseframe.net.NetManager;
import baseframe.tools.Util;
import ui.modes.Merchant;

/**
 * 主界面
 */
public class MainActivity extends GActivity implements View.OnClickListener{


    private GScrollView scrollView=null;
    private int scrollerh=400;//dp
    private ViewPager main_ads_viewpager=null;
    private ViewPager main_func_viewpager=null;
    private List<View> ads_pagers=new ArrayList<View>();
    private List<View> func_pagers=new ArrayList<View>();
    private List<View> recommends=new ArrayList<View>();
    private LinearLayout recommendlist=null;
    private FinalBitmap loader=null;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.main_layout);

        scrollView=(GScrollView)findViewById(R.id.main_scrollview);
        recommendlist=(LinearLayout)findViewById(R.id.recommendlist);
        initLoader();

        initTitle();
        caculteScrollH();
        genAdsPager();
        genFuncPager();

    }
    private void initLoader()
    {
        loader= AfinalManager.getInstance().getAFinalBitmap(this);
    }


    //初始化左上角定位功能
    private void initTitle()
    {
        View locationBtn=findViewById(R.id.locationBtn);
        EditText search_text=(EditText) findViewById(R.id.search_text);
        View search_btn=findViewById(R.id.search_btn);
        View sweep_btn=findViewById(R.id.sweep_btn);

        locationBtn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        sweep_btn.setOnClickListener(this);
    }

    //初始化底部导航menu
    private void initMenu()
    {
        MenuManager.getInstance().gen(this,MenuManager.HOME);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initMenu();
        if(loader!=null)loader.setExitTasksEarly(false);

        sendIndexRecommend();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(loader!=null)loader.setExitTasksEarly(true);
    }

    //请求推荐列表
    private void sendIndexRecommend()
    {
        AjaxParams params=new AjaxParams();
        params.put("type", Command.C_GET_BUSINESS_USER);

        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_BUSINESS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
//                Log.d(TAG, TAG + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d(TAG, "获取推荐商户数据成功");
                    //登录成功
                    //返回的是json用户自己数据
                    ArrayList<Merchant> merchants = Util.fromJson(strarr[1], new TypeToken<ArrayList<Merchant>>() {}.getType());
                    setRecommendList(merchants);

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d(TAG, "请求推荐商户数据失败");

                }
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg)
            {
                try {
                    throw new Exception(strMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
    private void setRecommendList(ArrayList<Merchant> merchants)
    {
        View temp;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Merchant data;
        for(int i=0;i<merchants.size();i++)
        {
            data=merchants.get(i);
            if(i<recommends.size())
            {
                temp=recommends.get(i);
            }else{
                temp=LayoutInflater.from(this).inflate(R.layout.main_recommendlist_view,recommendlist,false);
                recommends.add(temp);
            }
            //图片
            ImageView recommendlist_img=(ImageView)temp.findViewById(R.id.recommendlist_img);
            String imgUrl=Util.parseDownLoadURL(data.getPic(),this);
            loader.display(recommendlist_img,imgUrl,Util.dip2px(this,84),Util.dip2px(this,80));
            //图片
            TextView recommendlist_title=(TextView)temp.findViewById(R.id.recommendlist_title);
            recommendlist_title.setText(data.getName());
            //地址
            TextView recommendlist_address=(TextView)temp.findViewById(R.id.recommendlist_address);
            recommendlist_address.setText(Util.trimStringLen(data.getAddr(),15));
            //距离
            TextView recommendlist_dis=(TextView)temp.findViewById(R.id.recommendlist_dis);
            recommendlist_dis.setText("一万公里");//--------测试

            if(temp.getParent()==null)
            {
                recommendlist.addView(temp);
            }
        }
        if(recommends.size()>merchants.size())
        {
            for(int m=merchants.size();m<recommends.size();m++)
            {
                Util.removeFromParent(recommends.get(m));
            }
        }
    }






    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.locationBtn:

                break;
            case R.id.search_btn:

                break;
            case R.id.sweep_btn:

                break;
        }
    }











    private void caculteScrollH()
    {
//        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)scrollView.getLayoutParams();
//        scrollerh= AppConfig.APP_H- Util.dip2px(this,40)-Util.dip2px(this,70+12);//减去缝隙
//        params.height=scrollerh;
//        scrollView.setLayoutParams(params);

    }

    //生成页面滑动视图
    private void genFuncPager()
    {
        main_func_viewpager=(ViewPager)findViewById(R.id.main_func_viewpager);
        func_pagers.add(getLayoutView(R.layout.viewpager_mainfunc_view));

        main_func_viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return func_pagers.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                main_func_viewpager.addView(func_pagers.get(position));//----------注意要加入子view
                return func_pagers.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                main_func_viewpager.removeView(func_pagers.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

        });
        main_func_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "positionOffset:" + positionOffset);
                Log.d(TAG, "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged:" + state);
            }
        });
    }



    //生成页面滑动视图
    private void genAdsPager()
    {
        main_ads_viewpager=(ViewPager)findViewById(R.id.main_ads_viewpager);
        ads_pagers.add(getViewImage(R.id.img_0, R.drawable.img_ads0, R.layout.viewpager_ads_view));
        ads_pagers.add(getViewImage(R.id.img_0, R.drawable.img_ads1, R.layout.viewpager_ads_view));
        ads_pagers.add(getViewImage(R.id.img_0, R.drawable.img_ads2, R.layout.viewpager_ads_view));
        ads_pagers.add(getViewImage(R.id.img_0, R.drawable.img_ads3, R.layout.viewpager_ads_view));
        ads_pagers.add(getViewImage(R.id.img_0, R.drawable.img_ads4, R.layout.viewpager_ads_view));

        main_ads_viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return ads_pagers.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                main_ads_viewpager.addView(ads_pagers.get(position));//----------注意要加入子view
                return ads_pagers.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                main_ads_viewpager.removeView(ads_pagers.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

        });
        main_ads_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "positionOffset:" + positionOffset);
                Log.d(TAG, "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged:" + state);
            }
        });
    }
    private View getLayoutView(int pagerId)
    {
        View view= LayoutInflater.from(this).inflate(pagerId,null);
        return view;
    }

    private View getViewImage(int resId,int imgResId,int pagerId)
    {
        View view= LayoutInflater.from(this).inflate(pagerId,null);
        ImageView imageView=(ImageView)(view.findViewById(resId));
        imageView.setImageDrawable(ContextCompat.getDrawable(this, imgResId));

        return view;
    }




    @Override
    protected void onStop()
    {
        super.onStop();

    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}
