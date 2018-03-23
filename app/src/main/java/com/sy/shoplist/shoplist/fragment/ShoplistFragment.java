package com.sy.shoplist.shoplist.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.sy.shoplist.shoplist.R;
import com.sy.shoplist.shoplist.adapter.ShoplistAdapter;
import com.sy.shoplist.shoplist.javabean.ShopInfo;
import com.sy.shoplist.shoplist.jsons.JsonObjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sy.shoplist.shoplist.jsons.JSDate.JSONFILENAME;

/**
 * 商品列表的fragment页面
 */

public class ShoplistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    private ListView listView;
    //    private List<ShopInfo> list_shop = new ArrayList<ShopInfo>();
    private ShoplistAdapter adapter;
    private SwipeRefreshLayout srlayout;
    private final static int firstload = 10;
    private final static int nextload = 10;
    public int last_index;//当前界面可显示的总item数
    public int total_index;//统计可加载的item数
    private List<ShopInfo> firstList = new ArrayList<ShopInfo>(); //首次加载显示的list
    private List<ShopInfo> nextList = new ArrayList<ShopInfo>();//加载后显示的list
    public boolean isLoading = false;//表示是否正处于加载状态
    public View loadmoreView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shoplist, null, false);
        listView = (ListView) view.findViewById(R.id.list_shop);
        //下拉刷新
        srlayout = (SwipeRefreshLayout) view.findViewById(R.id.srlayout);
        srlayout.setOnRefreshListener(this);
        srlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        inflater = LayoutInflater.from(getActivity());
        loadmoreView = inflater.inflate(R.layout.load_more, null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的
        info();
        adapter = new ShoplistAdapter(getActivity(), firstList);
        listView.setOnScrollListener(this);
        listView.addFooterView(loadmoreView, null, false);
        listView.setAdapter(adapter);
        return view;
    }

    private void info() {
        String str = JsonObjects.getJson(JSONFILENAME, getActivity());
        //将读出的字符串转换成JSONobject
        try {
            JSONObject jsonObject = new JSONObject(str);
//            Toast.makeText(getActivity(),"count:"+ss,Toast.LENGTH_LONG).show();
            JSONArray jsonArray = jsonObject.getJSONArray("books");
            if (jsonArray.length() <= firstload) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    ShopInfo shop = new ShopInfo();
                    JSONObject json = jsonArray.getJSONObject(i);
                    String title = json.getString("title");
                    String price = json.getString("price");
                    String summary = json.getString("summary");
                    String image = json.getString("image");

                    shop.setSname(title);
                    shop.setPrice(price);
                    shop.setDetails(summary);
                    shop.setImgurl(image);
                    firstList.add(shop);
                }
            } else {
                for (int i = 0; i < firstload; i++) {
                    ShopInfo shop = new ShopInfo();
                    JSONObject json = jsonArray.getJSONObject(i);
                    String title = json.getString("title");
                    String price = json.getString("price");
                    String summary = json.getString("summary");
                    String image = json.getString("image");

                    shop.setSname(title);
                    shop.setPrice(price);
                    shop.setDetails(summary);
                    shop.setImgurl(image);
                    firstList.add(shop);
                    nextList.add(shop);
//                    Toast.makeText(getActivity(),"加载了:"+shop, Toast.LENGTH_LONG).show();
                }

                if (firstload < jsonArray.length()) {
                    for (int i = firstload; i < jsonArray.length(); i++) {
                        ShopInfo shop = new ShopInfo();
                        JSONObject json = jsonArray.getJSONObject(i);
                        String title = json.getString("title");
                        String price = json.getString("price");
                        String summary = json.getString("summary");
                        String image = json.getString("image");

                        shop.setSname(title);
                        shop.setPrice(price);
                        shop.setDetails(summary);
                        shop.setImgurl(image);
                        nextList.add(shop);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
//刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //结束后停止刷新
                srlayout.setRefreshing(false);
            }
        }, 2000);
//  一般会从网络获取数据
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                获取数据
//                refreshData();
//                swiper.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)) {
            //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
            if (!isLoading) {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                loadmoreView.setVisibility(View.VISIBLE);
                onLoad();
            }
        }
    }

    /**
     * 刷新加载
     */
    private void onLoad() {
        try {
            //模拟耗时操作
            if (adapter.getCount() < total_index){
                Thread.sleep(1000);
            }else {
                Toast.makeText(getActivity(),"已加载完",Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (adapter == null) {
            adapter = new ShoplistAdapter(getActivity(), firstList);
            listView.setAdapter(adapter);
        } else  {
            adapter.updateView(nextList);
        }
        loadComplete();//刷新结束
    }

    /**
     * 加载完成
     */
    public void loadComplete() {
        loadmoreView.setVisibility(View.GONE);//设置刷新界面不可见
        isLoading = false;//设置正在刷新标志位false
        getActivity().invalidateOptionsMenu();
        listView.removeFooterView(loadmoreView);//如果是最后一页的话，则将其从ListView中移出
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        last_index = i + i1;
        total_index = i2;
        System.out.println("last:  " + last_index);
        System.out.println("total:  " + total_index);
    }
}
