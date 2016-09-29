package ui.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yu.wyt.R;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.HashMap;
import java.util.List;

import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.core.GActivity;
import baseframe.manager.ActivityManager;
import baseframe.manager.CustomerManager;
import baseframe.net.NetManager;
import baseframe.tools.Util;
import ui.modes.Customer;

/**
 * 登录
 */
public class LoginActivity extends GActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{


    private EditText usernametxt;
    private EditText passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        usernametxt=(EditText)findViewById(R.id.username);
        passwordtxt=(EditText)findViewById(R.id.password);
        CheckBox password_checkbtn=(CheckBox)findViewById(R.id.password_checkbtn);
        Button loginBtn=(Button)findViewById(R.id.button_a);
        Button registerBtn=(Button)findViewById(R.id.button_b);

        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        password_checkbtn.setOnCheckedChangeListener(this);

    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {

        if(isChecked)
        {
            passwordtxt.setInputType(InputType.TYPE_CLASS_TEXT);

        }else{

            passwordtxt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }


    }

    public void onClick(View view)
    {

        int id=view.getId();
        switch (id)
        {
            case R.id.button_b://去注册

                register();

                break;
            case R.id.button_a://登录

                login();

                break;
        }




    }
    private void register()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login()
    {
        String username=usernametxt.getText().toString();
        String password=passwordtxt.getText().toString();

        HashMap<String,String> mapParams=new HashMap<String,String>();
        mapParams.put("type", Command.C_LOGIN);
        mapParams.put("username", username);
        mapParams.put("password", password);
        AjaxParams params=new AjaxParams(mapParams);
        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_LOGIN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, "LoginActivity:" + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d("LoginActivity", "登录成功");
                    //登录成功
                    //返回的是json用户自己数据
                    List<Customer> users = Util.fromJson(strarr[1], new TypeToken<List<Customer>>() {}.getType());
                    success(users.get(0));

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d("LoginActivity", "登录失败");
                    showMsg(LoginActivity.this, "登录失败");
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

    private void success(Customer user)
    {
        CustomerManager.getInstance().setUserSelf(user);
        //存入本地数据
        CustomerManager.getInstance().saveLocalSelfData(this);

        showMsg(LoginActivity.this, "登录成功");
        //注意先结束掉已有的栈activitys
        ((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).removeAll();

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }


    private void showMsg(final Context context, final String msg)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }





}
