package ui.content;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yu.wyt.R;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import base.component.GApplication;
import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.core.GActivity;
import baseframe.net.IResponseCallBack;
import baseframe.net.NetManager;
import baseframe.tools.Util;
import ui.modes.DistrictData;
import ui.modes.MerchantCategory;

/**
 * 创建商家
 */
public class CreateMerchantActivity extends GActivity {

    private static ArrayList<DistrictData> provinces=null;//省份列表

    private ArrayList<DistrictData> currentCitys=new ArrayList<DistrictData>();//当前城市
    private ArrayList<DistrictData> currentDistricts=new ArrayList<DistrictData>();//当前地区
    private ArrayList<MerchantCategory> categories=new ArrayList<MerchantCategory>();//商户类型
    private Spinner province;
    private Spinner city;
    private Spinner district;
    private Spinner merchant_type;
    //数据
    private int selectedProvinceId;//当前选择的省份ID
    private int selectedCityId;//当前选择的城市ID
    private int selectedDistrictId;//当前选择的地区ID
    private String selectedCategory;//当前选择的商户类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_merchant_layout);

        getProvince(0);
        currentCitys=getCityByProvince(provinces.get(0).getId());
        currentDistricts=getDistrictByCity(currentCitys.get(0).getId());
        initUI();
    }

    private void initUI()
    {
        if(provinces!=null&&provinces.size()>0)
        {
            setProvinceAdapter();
            setCityAdapter();
            setDistrictAdapter();
            initOther();

            sendMerchantCategoryReq();







        }
    }
    private void initOther()
    {
        ImageView backbtn0=(ImageView)findViewById(R.id.backbtn0);
        backbtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submitReviewBtn=(Button)findViewById(R.id.submitReviewBtn);
        submitReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCreateMerchantReq();
            }
        });
    }
    //返回
    private void sendCreateMerchantReq()
    {
        EditText merchant_name=(EditText)findViewById(R.id.merchant_name);
        EditText merchant_telphone=(EditText)findViewById(R.id.merchant_telphone);
        EditText merchant_qq=(EditText)findViewById(R.id.merchant_qq);
        EditText merchant_weixin=(EditText)findViewById(R.id.merchant_weixin);
        EditText merchant_address=(EditText)findViewById(R.id.merchant_address);

        if(TextUtils.isEmpty(merchant_name.getText().toString()))
        {
            Util.logToastInfo(this,"店铺名称不正确");
            return;
        }
        if(!Util.checkPhone(merchant_telphone.getText().toString()))
        {
            Util.logToastInfo(this,"手机号码不正确");
            return;
        }
        AjaxParams params=new AjaxParams();
        params.put("type", Command.C_CREATE_BUSINESS_USER);
        params.put("uid", "99999");//--------------目前是无效的
        params.put("name", merchant_name.getText().toString());
        params.put("telnumber", merchant_telphone.getText().toString());
        params.put("merchantType", selectedCategory);//商家类型
        params.put("addr", merchant_address.getText().toString());
        params.put("desc", "商家描述");//商家描述-------------------会员才可以编辑
        params.put("position", "1");//-------商家开放位置目前写死
        params.put("province", selectedProvinceId+"");
        params.put("city", selectedCityId+"");
        params.put("district", selectedDistrictId+"");
        params.put("point", "-1");//------------------经纬度目前无效
        params.put("district", selectedDistrictId+"");
        params.put("qq", merchant_qq.getText().toString());
        params.put("weixin", merchant_weixin.getText().toString());

        try {
            params.put("files", new File(Environment.getExternalStorageDirectory().getPath() + "/temp.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_CREATEBUSINESS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, TAG + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d(TAG, "创建商户成功");
                    Util.toToast("创建商户成功",CreateMerchantActivity.this);
                    finish();

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d(TAG, "创建商户失败");
                    Util.toToast("创建商户失败",CreateMerchantActivity.this);
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

        },true);




    }

    //发送商户类型请求
    private void sendMerchantCategoryReq()
    {
        HashMap<String,String> mapParams=new HashMap<String,String>();
        mapParams.put("type", Command.C_GET_BUSINESS_CATEGROY);
        AjaxParams params=new AjaxParams(mapParams);
        NetManager.Post(AppConfig.SERVER_DOMAIN+AppConfig.SERVER_NAME+AppConfig.SERVER_SEVERLET_BUSINESS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, TAG + result);
                String resultstr=result.toString();

                String[] strarr = resultstr.split("\\^");
                int code=Integer.parseInt(strarr[0]);
                if (code==Command.Code0) {
                    Log.d(TAG, "获取商户类型数据成功");
                    //登录成功
                    //返回的是json用户自己数据
                    ArrayList<MerchantCategory> categories = Util.fromJson(strarr[1], new TypeToken<ArrayList<MerchantCategory>>() {}.getType());
                    success(categories);

                } else if (code==Command.Code1) {
                    //登录失败
                    Log.d(TAG, "请求商户类型数据失败");

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
    private void success(ArrayList<MerchantCategory> categories)
    {
        this.categories=categories;
        //刷新UI
        setCategoriesAdapter();
    }




    private void setCategoriesAdapter()
    {
        if(categories.size()==0)
        {
            return;
        }
        selectedCategory=categories.get(0).getTitle();
        merchant_type=(Spinner) findViewById(R.id.merchant_type);
        merchant_type.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater= LayoutInflater.from(CreateMerchantActivity.this);
                convertView=inflater.inflate(android.R.layout.simple_spinner_dropdown_item,null);
                if(convertView!=null)
                {
                    TextView textView=(TextView)convertView.findViewById(android.R.id.text1);
                    textView.setText(categories.get(position).getTitle());
                }
                return convertView;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return currentDistricts.size();
            }

            @Override
            public Object getItem(int position) {
                return currentDistricts.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater= LayoutInflater.from(CreateMerchantActivity.this);
                convertView=inflater.inflate(android.R.layout.simple_spinner_dropdown_item,null);
                if(convertView!=null)
                {
                    TextView textView=(TextView)convertView.findViewById(android.R.id.text1);
                    textView.setText(categories.get(position).getTitle());
                }
                return convertView;
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });


        merchant_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategory=categories.get(position).getTitle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setDistrictAdapter()
    {
        if(currentDistricts.size()==0)
        {
            return;
        }
        selectedDistrictId=currentDistricts.get(0).getId();
        district=(Spinner) findViewById(R.id.district);

        setAreaAdapter(currentDistricts, district, new IResponseCallBack<Integer>() {
            @Override
            public void callback(Integer result) {
                selectedDistrictId=currentDistricts.get(result).getId();
            }
        });
    }

    private void setCityAdapter()
    {
        if(currentCitys.size()==0)
        {
            return;
        }
        selectedCityId=currentCitys.get(0).getId();

        city=(Spinner)findViewById(R.id.city);
        setAreaAdapter(currentCitys, city, new IResponseCallBack<Integer>() {
            @Override
            public void callback(Integer result) {
                selectedCityId=currentCitys.get(result).getId();
                currentDistricts=getDistrictByCity(selectedCityId);
                if(currentDistricts.size()==0)
                {
                    currentDistricts.add(currentCitys.get(result));
                }
                setDistrictAdapter();
            }
        });
    }

    private void setProvinceAdapter()
    {
        selectedProvinceId=provinces.get(0).getId();
        province=(Spinner) findViewById(R.id.province);
        setAreaAdapter(provinces, province, new IResponseCallBack<Integer>() {
            @Override
            public void callback(Integer result) {
                selectedProvinceId=provinces.get(result).getId();
                currentCitys=getCityByProvince(selectedProvinceId);

                setCityAdapter();
            }
        });
    }
    private void setAreaAdapter(final ArrayList<DistrictData> areas, Spinner components,final IResponseCallBack<Integer> callBack)
    {
        components.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater= LayoutInflater.from(CreateMerchantActivity.this);
                convertView=inflater.inflate(android.R.layout.simple_spinner_dropdown_item,null);
                if(convertView!=null)
                {
                    TextView textView=(TextView)convertView.findViewById(android.R.id.text1);
                    textView.setText(areas.get(position).getName());
                }
                return convertView;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return areas.size();
            }

            @Override
            public Object getItem(int position) {
                return areas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater= LayoutInflater.from(CreateMerchantActivity.this);
                convertView=inflater.inflate(android.R.layout.simple_spinner_dropdown_item,null);
                if(convertView!=null)
                {
                    TextView textView=(TextView)convertView.findViewById(android.R.id.text1);
                    textView.setText(areas.get(position).getName());
                }
                return convertView;
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });


        components.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(callBack!=null)
                {
                    callBack.callback(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void getProvince(int upid)
    {
        if(provinces==null)
        {
            provinces=new ArrayList<DistrictData>();
            getDistrictData(upid,provinces);
        }
    }
    private ArrayList<DistrictData> getCityByProvince(int provinceId)
    {
        ArrayList<DistrictData> cities=new ArrayList<DistrictData>();
        getDistrictData(provinceId,cities);
        return cities;
    }
    private ArrayList<DistrictData> getDistrictByCity(int cityId)
    {
        ArrayList<DistrictData> districts=new ArrayList<DistrictData>();
        getDistrictData(cityId,districts);
        return districts;
    }
    private ArrayList<DistrictData> getDistrictData(int upid,ArrayList<DistrictData> datas)
    {
        SQLiteDatabase database=((GApplication)getApplication()).getDbManager().openDatabase();
        Cursor cursor=database.query("district",null,"upid=?",new String[]{upid+""},null,null,null);
        DistrictData province;
        while (cursor.moveToNext())
        {
            province=new DistrictData();
            int id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name=cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int level=cursor.getInt(cursor.getColumnIndexOrThrow("level"));
            province.setId(id);
            province.setName(name);
            province.setLevel(level);
            province.setUpid(upid);
            datas.add(province);
        }
        return datas;
    }







    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}
