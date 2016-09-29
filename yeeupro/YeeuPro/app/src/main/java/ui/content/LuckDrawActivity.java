package ui.content;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Float2;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yu.wyt.R;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.core.GActivity;
import baseframe.manager.CustomerManager;
import baseframe.net.NetManager;
import baseframe.tools.Util;
import ui.modes.CouponResult;

/**
 * 抽奖
 */
public class LuckDrawActivity extends GActivity {

    //抽奖状态
    private static final int STATUS1=1;//未抽奖
    private static final int STATUS2=2;//正在抽奖
    private static final int STATUS3=3;//抽奖结束
    private static final int STATUS4=4;//已经抽奖

    private FrameLayout luck_container;
    private int centerPx=0;
    private int centerPy=0;
    private ImageView treasurebox;//宝箱
    private TextView luckdraw_msg0=null;
    private TextView luckdraw_msg1=null;
    private TextView luckdraw_msg99=null;//奖励
    private View luckdraw_bg0=null;
    private Button luckdraw_btn0=null;
    private ImageView treasure_bg=null;
    private Drawable treasureClose_draw=null;//关闭的盒子图
    private Drawable treasureOpen_draw=null;//打开的盒子图
    private int currentLuckStatus=1;//当前抽奖状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luckdraw_layout);

        luck_container=(FrameLayout)findViewById(R.id.luck_container);
        luck_container.post(new Runnable() {
            @Override
            public void run() {
                toInit();
            }
        });
    }
    private void toInit()
    {
        initUI();





    }
    private void initUI()
    {
        treasure_bg=(ImageView)findViewById(R.id.treasure_bg);
        FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)treasure_bg.getLayoutParams();
        float rate=treasure_bg.getHeight()/treasure_bg.getWidth();//拿到宽高比
        params.width= (int) (AppConfig.APP_W*1.3f);//放大1.3倍
        treasure_bg.setLayoutParams(params);
        treasure_bg.setX((AppConfig.APP_W>>1)-(params.width>>1));
        centerPx=AppConfig.APP_W>>1;
        centerPy=(int)(params.width*rate)>>1;

        float pivoth=(float)centerPy/luck_container.getHeight();//相对于父容器的百分比

        RotateAnimation rotate_forever=new RotateAnimation(0,360,Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,pivoth);
        rotate_forever.setDuration(4000);
        rotate_forever.setRepeatMode(Animation.RESTART);
        rotate_forever.setRepeatCount(Animation.INFINITE);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        rotate_forever.setInterpolator(lin);
        treasure_bg.startAnimation(rotate_forever);

        treasurebox=(ImageView)findViewById(R.id.treasurebox);
        luckdraw_msg0=(TextView) findViewById(R.id.luckdraw_msg0);
        luckdraw_msg1=(TextView) findViewById(R.id.luckdraw_msg1);
        luckdraw_msg99=(TextView) findViewById(R.id.luckdraw_msg99);
        luckdraw_bg0=findViewById(R.id.luckdraw_bg0);
        luckdraw_btn0=(Button) findViewById(R.id.luckdraw_btn0);

        treasureClose_draw=treasurebox.getDrawable();
        treasureOpen_draw=ContextCompat.getDrawable(this,R.drawable.treasurebox_open);

        ImageView backbtn0=(ImageView)findViewById(R.id.backbtn0);
        backbtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        treasurebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLuckStatus==STATUS1)
                {
                    //先请求服务器，抽奖
                    sendLuckDraw();
                }
            }
        });
        luckdraw_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLuckStatus==STATUS3)
                {
                    //查看奖品详情



                }else if(currentLuckStatus==STATUS4)
                {
                    //查看奖品详情




                }
            }
        });
        //请求抽奖状态
        sendLuckStatus();
    }

    private void sendLuckStatus()
    {
        AjaxParams params=new AjaxParams();
        params.put("type", Command.C_GET_LUCKDRAW_STATUS);
        params.put("cid", CustomerManager.getInstance().getUserSelf().getId()+"");

        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_LUCKDRAW, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, TAG + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d(TAG, "获取抽奖状态成功");
                    //返回的是json用户自己数据
                    CouponResult couponResult = Util.fromJson(strarr[1], new TypeToken<CouponResult>() {}.getType());
                    successBack(couponResult);

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d(TAG, "获取抽奖状态失败");

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

    //向服务器发送抽奖请求
    private void sendLuckDraw()
    {
        sendGetLuckDraw();
    }
    //请求抽奖
    private void sendGetLuckDraw()
    {
        AjaxParams params=new AjaxParams();
        params.put("type", Command.C_GET_LUCKDRAW_GET);
        params.put("cid", CustomerManager.getInstance().getUserSelf().getId()+"");

        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_LUCKDRAW, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, TAG + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d(TAG, "获取抽奖状态成功");
                    //返回的是json用户自己数据
                    CouponResult couponResult = Util.fromJson(strarr[1], new TypeToken<CouponResult>() {}.getType());
                    successBack(couponResult);

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d(TAG, "获取抽奖状态失败");

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
    private void onSendLuckDrawCallback()
    {
        //抽奖成功，动画演示
        setStatus(STATUS2);


    }
    //获取抽奖状态成功返回
    private void successBack(CouponResult couponResult)
    {
        if(couponResult.getCount()>0)
        {
            SpannableString spannable=new SpannableString(getResources().getString(R.string.luckdraw_msg0));
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_org_0)),0,3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置前景色
            spannable.setSpan(new RelativeSizeSpan(1.3f),0,3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//1.3f表示默认字体大小的1.3倍
            //注：也可以用 Html解析 但是HTML可支持的属性有限
            setLuckDrawRecordTitle(spannable);
            setLuckDrawRecordCount(couponResult.getCount(),couponResult.getStartTime()+"-"+couponResult.getEndTime());

            setStatus(STATUS1);

        }else{
            SpannableString spannable=new SpannableString(couponResult.getInfo());
            setLuckDrawRecordName(couponResult.getName());
            setLuckDrawRecordTitle(spannable);
            setLuckDrawRecordCount(couponResult.getCount(),couponResult.getStartTime()+"-"+couponResult.getEndTime());

            setStatus(STATUS4);

        }
    }
    //设置状态
    private void setStatus(int status)
    {
        setStatus(status,0);
    }
    //设置状态
    private void setStatus(int status,int addy)
    {
        currentLuckStatus=status;
        switch (status)
        {
            case STATUS1:
                setStatus1();
                break;
            case STATUS2:
                setStatus2();
                break;
            case STATUS3:
                setStatus3(addy);
                break;
            case STATUS4:
                setStatus4();
                break;
        }
    }
    //设置获奖的名称
    private void setLuckDrawRecordName(String str)
    {
        luckdraw_msg99.setText(str);
    }
    //设置获奖的title
    private void setLuckDrawRecordTitle(SpannableString spannable)
    {
        luckdraw_msg0.setText(spannable);
    }
    //设置获奖的次数和抽奖时间
    private void setLuckDrawRecordCount(int count,String time)
    {
        String str=getResources().getString(R.string.luckdraw_msg1);
        String result=String.format(str,count,time);
        //可以设置字符串的单个文字颜色
        luckdraw_msg1.setText(result);
    }
    //设置为 未抽奖状态
    private void setStatus1()
    {
        luckdraw_msg0.setX(centerPx-(luckdraw_msg0.getWidth()>>1));
        luckdraw_msg0.setY(centerPy-400);

        luckdraw_msg1.setX(centerPx-(luckdraw_msg1.getWidth()>>1));
        luckdraw_msg1.setY(centerPy*2- Util.dip2px(this,40));

        treasurebox.setImageDrawable(treasureClose_draw);
        treasurebox.setX(centerPx-(treasurebox.getWidth()>>1));
        treasurebox.setY(centerPy-(treasurebox.getHeight()>>1)-40);

        luckdraw_bg0.setVisibility(View.INVISIBLE);
        luckdraw_btn0.setVisibility(View.INVISIBLE);
        luckdraw_msg99.setVisibility(View.INVISIBLE);
        luckdraw_msg0.setVisibility(View.VISIBLE);
        luckdraw_msg1.setVisibility(View.VISIBLE);


    }
    //设置为 正在奖状态
    private void setStatus2()
    {
        float hper=(treasurebox.getY()+(treasurebox.getHeight()>>1))/luck_container.getHeight();
        RotateAnimation rotateAnimation=new RotateAnimation(-8,8,Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,hper);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(8);
        rotateAnimation.setDuration(50);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                treasurebox.clearAnimation();
                treasurebox.setRotation(0);
                TranslateAnimation tAnimation=new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT,0,
                        Animation.RELATIVE_TO_PARENT,0,
                        Animation.ABSOLUTE, 0,//:指的绝对坐标(单位像素),假如100,就是相对于原点正方向偏移100个像素.
                        Animation.ABSOLUTE,180);//相对于作用对象的原点偏移x像素
                tAnimation.setDuration(400);
                tAnimation.setInterpolator(new BounceInterpolator());
                tAnimation.setRepeatMode(Animation.RESTART);
                tAnimation.setFillAfter(true);//停在最后状态
                tAnimation.setStartOffset(200);
                tAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        overTreasureMoveDown();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                treasurebox.startAnimation(tAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        treasurebox.startAnimation(rotateAnimation);
    }
    private void toMoveUp(View view,int dis)
    {
        AnimationSet animationSet=new AnimationSet(false);
        animationSet.setFillAfter(true);

        TranslateAnimation tAnimation=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0,
                Animation.RELATIVE_TO_PARENT,0,
                Animation.ABSOLUTE, 0,//:指的绝对坐标(单位像素),假如100,就是相对于原点正方向偏移100个像素.
                Animation.ABSOLUTE,-dis);//相对于作用对象的原点偏移x像素
        tAnimation.setDuration(600);
        tAnimation.setInterpolator(new LinearInterpolator());
        tAnimation.setRepeatMode(Animation.RESTART);
        tAnimation.setFillAfter(true);//停在最后状态
        animationSet.addAnimation(tAnimation);
        //
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(600);
        animationSet.addAnimation(alphaAnimation);

        view.startAnimation(animationSet);
    }

    private void overTreasureMoveDown()
    {
        //宝箱完成下移动画
        setStatus(STATUS3,140);
        toMoveUp(luckdraw_bg0,140);
        toMoveUp(luckdraw_msg99,140);
        toMoveUp(luckdraw_btn0,140);
//        setTreasureScaleAnima();
    }
    //目前有问题 未找到原因（缩放应用时位置也跟着变了！！！！！）
    private void setTreasureScaleAnima()
    {
        treasurebox.clearAnimation();

        float hper=(treasurebox.getY()+(treasurebox.getHeight()>>1))/(float)luck_container.getHeight();
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setStartOffset(100);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        treasurebox.startAnimation(scaleAnimation);
    }



    //设置为 抽奖结束状态
    private void setStatus3(int addy)
    {
        luckdraw_bg0.setVisibility(View.VISIBLE);
        luckdraw_btn0.setVisibility(View.VISIBLE);
        luckdraw_msg0.setVisibility(View.VISIBLE);
        luckdraw_msg1.setVisibility(View.VISIBLE);
        luckdraw_msg99.setVisibility(View.VISIBLE);

        //
        luckdraw_bg0.setX(centerPx-(luckdraw_bg0.getWidth()>>1));
        luckdraw_bg0.setY(centerPy-Util.dip2px(this,140)+addy);
        //
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        luckdraw_msg99.measure(spec,spec);//动态测量
        int hw=luckdraw_msg99.getMeasuredWidth()>>1;
        int hh=luckdraw_msg99.getMeasuredHeight()>>1;
        luckdraw_msg99.setX((luckdraw_bg0.getWidth()>>1)-hw+luckdraw_bg0.getX());
        luckdraw_msg99.setY((luckdraw_bg0.getHeight()>>1)-hh+luckdraw_bg0.getY()-40);

        //
        luckdraw_btn0.setX(centerPx-(luckdraw_btn0.getWidth()>>1));
        luckdraw_btn0.setY(centerPy-Util.dip2px(this,44)+addy);

        luckdraw_msg0.setText(getResources().getString(R.string.luckdraw_msg2));
        luckdraw_msg0.setX(centerPx-(luckdraw_msg0.getWidth()>>1));
        luckdraw_msg0.setY(centerPy-360);

        luckdraw_msg1.setX(centerPx-(luckdraw_msg1.getWidth()>>1));
        luckdraw_msg1.setY(centerPy*2- Util.dip2px(this,60));

        treasurebox.setImageDrawable(treasureOpen_draw);
        Float2 floatTreasure= getTreasureStopPos();
        treasurebox.setX(floatTreasure.x);
        treasurebox.setY(floatTreasure.y);
    }

    private Float2 getTreasureStopPos()
    {
        Float2 float2=new Float2(centerPx-(treasurebox.getWidth()>>1),centerPy-(treasurebox.getHeight()>>1));
        return float2;
    }


    //设置为 已抽奖状态
    private void setStatus4()
    {
        luckdraw_bg0.setVisibility(View.INVISIBLE);
        luckdraw_msg99.setVisibility(View.INVISIBLE);
        luckdraw_btn0.setVisibility(View.VISIBLE);
        luckdraw_msg0.setVisibility(View.VISIBLE);
        luckdraw_msg1.setVisibility(View.VISIBLE);

        //
        luckdraw_btn0.setX(centerPx-(luckdraw_btn0.getWidth()>>1));
        luckdraw_btn0.setY(centerPy+160);

        luckdraw_msg0.setText(getResources().getString(R.string.luckdraw_msg2));
        luckdraw_msg0.setX(centerPx-(luckdraw_msg0.getWidth()>>1));
        luckdraw_msg0.setY(centerPy-360);

        luckdraw_msg1.setX(centerPx-(luckdraw_msg1.getWidth()>>1));
        luckdraw_msg1.setY(centerPy*2- Util.dip2px(this,60));

        treasurebox.setImageDrawable(treasureOpen_draw);
        treasurebox.setX(centerPx-(treasurebox.getWidth()>>1));
        treasurebox.setY(centerPy-(treasurebox.getHeight()>>1)-40);
    }





    @Override
    protected void onResume()
    {
        super.onResume();

    }



    //清空或释放
    private void clearElement()
    {
        Util.clearAnimation(treasurebox);
        Util.clearAnimation(luckdraw_msg99);
        Util.clearAnimation(luckdraw_bg0);
        Util.clearAnimation(luckdraw_btn0);
        Util.clearAnimation(treasure_bg);

        treasureClose_draw=null;
        treasureOpen_draw=null;
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        clearElement();

    }
}
