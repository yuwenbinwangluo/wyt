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

import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.core.GActivity;
import baseframe.manager.ActivityManager;
import baseframe.manager.CustomerManager;
import baseframe.net.NetManager;
import baseframe.tools.Util;
import ui.modes.Customer;

/**
 * 注册
 */
public class RegisterActivity extends GActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{


    private String usernametxt;
    private String passwordtxt;
    private EditText username;
    private EditText password;
    private EditText yes_password;
    private EditText email;
    private  EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

         username=(EditText)findViewById(R.id.username);
         password=(EditText)findViewById(R.id.password);
         yes_password=(EditText)findViewById(R.id.yes_password);
         email=(EditText)findViewById(R.id.email);
         phone=(EditText)findViewById(R.id.phone);
        Button registerBtn=(Button)findViewById(R.id.registerBtn);
        Button button_login=(Button)findViewById(R.id.button_login);
        CheckBox checkbox1=(CheckBox)findViewById(R.id.checkbox1);
        CheckBox checkbox2=(CheckBox)findViewById(R.id.checkbox2);

        registerBtn.setOnClickListener(this);
        button_login.setOnClickListener(this);
        checkbox1.setOnCheckedChangeListener(this);
        checkbox2.setOnCheckedChangeListener(this);
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox1://

                passwordState(password,isChecked);

                break;
            case R.id.checkbox2://

                passwordState(yes_password,isChecked);

                break;
        }
    }
    private void passwordState(EditText text,boolean isChecked)
    {
        if(isChecked)
        {
            text.setInputType(InputType.TYPE_CLASS_TEXT);

        }else {
            text.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }


    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.registerBtn://注册按钮

                register();

                break;
            case R.id.button_login://登录按钮

                tologin();

                break;
        }
    }

    private void tologin()
    {
        finish();
    }
    private void register()
    {
        String usernametxt=username.getText().toString();
        String passwordtxt=password.getText().toString();
        String yes_passwordtxt=yes_password.getText().toString();
        String emailtxt=email.getText().toString();
        String phonetxt=phone.getText().toString();


        if(passwordtxt.equals(""))
        {
            //email 不合法
            Toast.makeText(this,"请输入相同的密码",Toast.LENGTH_SHORT).show();

            return;
        }
        if(!passwordtxt.equals(yes_passwordtxt))
        {
            //email 不合法
            Toast.makeText(this,"请输入相同的密码",Toast.LENGTH_SHORT).show();

            return;
        }
        if(!Util.checkEmail(emailtxt))
        {
            //email 不合法
            Toast.makeText(this,"请输入正确的邮箱",Toast.LENGTH_SHORT).show();

            return;
        }

        if(!Util.checkPhone(phonetxt))
        {
            //email 不合法
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();

            return;
        }

        AjaxParams params=new AjaxParams();
        params.put("type", Command.C_REGISTER);
        params.put("username", usernametxt);
        params.put("password", passwordtxt);
        params.put("email", emailtxt);
        params.put("telnumber", phonetxt);
        params.put("point", "-1,-1");//------------用户定位经纬度
        params.put("position", "1");//------------开放地区编号

        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_LOGIN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, result.toString());
                String[] strarr = result.toString().split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    //返回的是json用户自己数据
                    Customer user = Util.fromJson(strarr[1], new TypeToken<Customer>() {
                    }.getType());
                    success(user);

                }else if (code==Command.Code100) {
                    showMsg(RegisterActivity.this, Command.ERROR_TELNUMBER);
                }else if (code==Command.Code101) {
                    showMsg(RegisterActivity.this, Command.ERROR_EMAIL);
                } else if (code==Command.Code102) {
                    showMsg(RegisterActivity.this,  Command.ERROR_TELNUMBER_OCCUPY);
                }else if (code==Command.Code103) {
                    showMsg(RegisterActivity.this,  Command.ERROR_USERNAMEOCCUPY);
                }else{
                    showMsg(RegisterActivity.this, "注册失败");
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

        showMsg(RegisterActivity.this, "注册成功");
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
