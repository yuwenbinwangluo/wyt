package ui.content;

import android.content.Intent;
import android.os.Bundle;

import com.yu.wyt.R;

import baseframe.core.GActivity;
import baseframe.manager.ActivityManager;
import baseframe.manager.CustomerManager;
import baseframe.net.IResponseCallBack;
import baseframe.tools.Util;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends GActivity {

    private boolean isAlreadyLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);


        isAlreadyLogin= CustomerManager.getInstance().isAlreadyLogin(this);
        Util.delayPerform(new IResponseCallBack<Object>() {
            @Override
            public void callback(Object result) {

                end();

            }
        },3000);



    }

    private void end()
    {
        if(isAlreadyLogin)
        {
            ((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).removeAll();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }






}
