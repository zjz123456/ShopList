package com.sy.shoplist.shoplist.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static com.sy.shoplist.shoplist.control.ActivityCollector.addActivity;
import static com.sy.shoplist.shoplist.control.ActivityCollector.removeActivity;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class BaseActivity extends AppCompatActivity {
    private long mExitTime = System.currentTimeMillis();  //为当前系统时间，单位：毫秒

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime < 800) {
            this.finish();   //关闭本活动页面
            System.exit(0);
        } else {
            Toast.makeText(this, "再按返回键退出！", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();   //这里赋值最关键，别忘记
        }
    }
}
