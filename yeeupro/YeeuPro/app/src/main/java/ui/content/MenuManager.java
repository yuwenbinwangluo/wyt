package ui.content;


import android.app.Activity;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yu.wyt.R;

import java.util.ArrayList;

import baseframe.manager.ActivityManager;

/**
 * 底部菜单栏管理器
 */
public class MenuManager{
    private static MenuManager _instance=new MenuManager();
    //menu类型
    public static final int HOME=0;
    public static final int LUCKDRAW=1;
    public static final int USERCENTER=2;
    public static final int MORE=3;

    private Activity activity;
    private RadioGroup group;

    public static MenuManager getInstance()
    {
        return _instance;
    }
    private ArrayList<RadioButton> btns=new ArrayList<RadioButton>();
    private int currentSelectedIndex=-1;

    //共用布局，每次进入需要重新生成
    public void gen(Activity activity,int checkedIndex)
    {
        MenuManager.getInstance().clear();

        this.activity=activity;
        currentSelectedIndex=checkedIndex;
        group=(RadioGroup)activity.findViewById(R.id.menugroup);
        btns.add((RadioButton)activity.findViewById(R.id.homebtn));
        btns.add((RadioButton)activity.findViewById(R.id.prizebtn));
        btns.add((RadioButton)activity.findViewById(R.id.userbtn));
        btns.add((RadioButton)activity.findViewById(R.id.morebtn));
        setSelectedBtn();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=changeIdByIndex(currentSelectedIndex);
                if(checkedId!=id)
                {
                    switch (checkedId)
                    {
                        case R.id.homebtn:
                            toHome();
                            break;
                        case R.id.prizebtn:
                            toLuckDraw();
                            break;
                        case R.id.userbtn:
                            toUserCenter();
                            break;
                        case R.id.morebtn:
                            toMore();
                            break;
                    }
                }
            }
        });
    }
    private void setSelectedBtn()
    {
        btns.get(currentSelectedIndex).setChecked(true);
    }

    private int changeIdByIndex(int index)
    {
        int id=-1;
        switch (index)
        {
            case HOME:
                id=R.id.homebtn;
                break;
            case LUCKDRAW:
                id=R.id.prizebtn;
                break;
            case USERCENTER:
                id=R.id.userbtn;
                break;
            case MORE:
                id=R.id.morebtn;
                break;
        }
        return id;
    }

    private void toHome()
    {
        toActivity(ActivityManager.HOME_PAGE);
    }
    private void toLuckDraw()
    {
        toActivity(ActivityManager.LUCKDRAW_PAGE);
    }
    private void toUserCenter()
    {
        toActivity(ActivityManager.USERCENTER_PAGE);
    }
    private void toMore()
    {
        toActivity(ActivityManager.MORE_PAGE);
    }
    private void toActivity(String action)
    {
        Intent intent=new Intent(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    private void clear()
    {
        if(group!=null)
        {
            group.setOnCheckedChangeListener(null);
        }
        btns.clear();
        currentSelectedIndex=-1;
    }





}
