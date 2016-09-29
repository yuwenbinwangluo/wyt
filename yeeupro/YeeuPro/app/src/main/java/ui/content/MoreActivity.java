package ui.content;

import android.os.Bundle;

import com.yu.wyt.R;

import baseframe.core.GActivity;

/**
 * 更多
 */
public class MoreActivity extends GActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_func_layout);



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
        MenuManager.getInstance().gen(this,MenuManager.MORE);

    }
}
