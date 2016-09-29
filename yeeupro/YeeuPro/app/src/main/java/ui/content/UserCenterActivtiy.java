package ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yu.wyt.R;

import baseframe.core.GActivity;
import baseframe.manager.ActivityManager;
import baseframe.manager.CustomerManager;

/**
 * 用户中心
 */
public class UserCenterActivtiy extends GActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter_layout);


        View create_merchant_btn=findViewById(R.id.create_merchant_btn);
        create_merchant_btn.setOnClickListener(this);
        View tcdenglu=findViewById(R.id.tcdenglu);
        tcdenglu.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.create_merchant_btn:
                //创建商家
                createMerchant();
                break;
            case R.id.tcdenglu:
                //登出
                loginOut();
                break;
        }

    }
    private void loginOut()
    {
        CustomerManager.getInstance().loginOut(this);
    }


    private void createMerchant()
    {
        toActivity(ActivityManager.CREATE_MERCHANT_PAGE);
    }
    private void toActivity(String action)
    {
        Intent intent=new Intent(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initMenu();

    }
    //初始化底部导航menu
    private void initMenu()
    {
        MenuManager.getInstance().gen(this,MenuManager.USERCENTER);

    }
    @Override
    protected void onPause()
    {
        super.onPause();

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
