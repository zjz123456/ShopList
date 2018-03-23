package com.sy.shoplist.shoplist.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import static com.sy.shoplist.shoplist.control.ActivityCollector.addActivity;
import static com.sy.shoplist.shoplist.control.ActivityCollector.removeActivity;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class BaseActivity extends AppCompatActivity {
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
}
