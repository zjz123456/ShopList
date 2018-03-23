package com.sy.shoplist.shoplist.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sy.shoplist.shoplist.R;
import com.sy.shoplist.shoplist.adapter.ShopCartAdapter;
import com.sy.shoplist.shoplist.control.MyApplication;
import com.sy.shoplist.shoplist.dao.ShopDao;
import com.sy.shoplist.shoplist.javabean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

public class ShopcartFragment extends Fragment {
    private List<ShopInfo> list = new ArrayList<ShopInfo>();//购物车商品集合
    private ShopDao dao;
    private ListView listView;
    private ShopCartAdapter adapter;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcart, null, false);
        listView = (ListView) view.findViewById(R.id.list_cart);
        submit = (Button) view.findViewById(R.id.submit);
        info();
        return view;
    }

    private void info() {
        dao = new ShopDao(getActivity());
        list = MyApplication.dao.getShops();
        if (list.size() != 0) {
            adapter = new ShopCartAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
//            Toast.makeText(getActivity(),"当前没数据",Toast.LENGTH_SHORT).show();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> prices = MyApplication.dao.getprice_add();
                int sum = 0;
                for (int i = 0;i<prices.size();i++){
                    sum+=prices.get(i);
                }
                Toast.makeText(getActivity(), "结算共需:"+sum+"元", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
