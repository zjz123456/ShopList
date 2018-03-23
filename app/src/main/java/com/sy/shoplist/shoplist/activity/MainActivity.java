package com.sy.shoplist.shoplist.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sy.shoplist.shoplist.R;
import com.sy.shoplist.shoplist.fragment.ShopcartFragment;
import com.sy.shoplist.shoplist.fragment.ShoplistFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button_shoplist; //商品列表
    private Button button_shopcart; //购物车
    private FrameLayout framelayout;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ShopcartFragment scf;
    private ShoplistFragment slf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scf = new ShopcartFragment();
        slf = new ShoplistFragment();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, slf);
        ft.commit();
        init();
    }

    private void init() {
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        button_shoplist = (Button) findViewById(R.id.button_shoplist);
        button_shopcart = (Button) findViewById(R.id.button_shopcart);
        button_shoplist.setOnClickListener(this);
        button_shopcart.setOnClickListener(this);
        selectFragmentlist();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_shoplist:
                selectFragmentlist();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.framelayout, slf);
                ft.commit();
                break;
            case R.id.button_shopcart:
                selectFragmentcart();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.framelayout, scf);
                ft.commit();
                break;
        }
    }
    //按钮背景设置
    private void selectFragmentlist(){
        button_shoplist.setBackgroundResource(R.drawable.bg_btn_style_white);
        button_shopcart.setBackgroundResource(R.drawable.bg_btn_style_gray);
    }
    private void selectFragmentcart(){
        button_shoplist.setBackgroundResource(R.drawable.bg_btn_style_gray);
        button_shopcart.setBackgroundResource(R.drawable.bg_btn_style_white);
    }
}
