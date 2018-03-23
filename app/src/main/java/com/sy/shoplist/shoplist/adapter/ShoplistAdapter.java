package com.sy.shoplist.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.shoplist.shoplist.R;
import com.sy.shoplist.shoplist.control.MyApplication;
import com.sy.shoplist.shoplist.dao.ShopDao;
import com.sy.shoplist.shoplist.imageurl.MyImageView;
import com.sy.shoplist.shoplist.javabean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class ShoplistAdapter extends BaseAdapter {
//    private List<ShopInfo> list_add = new ArrayList<ShopInfo>();
    private List<ShopInfo> list;
    private Context context;
    private ShopDao dao;

    public ShoplistAdapter(Context context, List<ShopInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    @Override
    public ShopInfo getItem(int i) {
        return list.isEmpty() ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void updateView(List<ShopInfo> nowList)
    {
        this.list = nowList;
        this.notifyDataSetChanged();//强制动态刷新数据进而调用getView方法
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ShopInfo info = list.get(i);
        ViewHolder vh = null;
        if (view == null) {
            final LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_shoplist, null);
            vh = new ViewHolder();
//            view = LayoutInflater.from(context).inflate(R.layout.item_shoplist, null, false);
            vh.name = (TextView) view.findViewById(R.id.sname);
            vh.price = (TextView) view.findViewById(R.id.sprice);
            vh.sinfo = (TextView) view.findViewById(R.id.sinfo);
            vh.img = (MyImageView) view.findViewById(R.id.img_shop);
            vh.img_btn = (ImageButton) view.findViewById(R.id.button_addshop);
            view.setTag(vh);//复用vh
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.name.setText(info.getSname());
        vh.sinfo.setText(info.getDetails());
        vh.price.setText(info.getPrice() + "");
        String url = info.getImgurl();
        vh.img.setImageURL(url);
        //点击添加数据到购物车
        vh.img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.dao.add_shop(info);
//                dao.add_shop(info);
//                Toast.makeText(context,"info"+info,Toast.LENGTH_SHORT).show();
//                dao.closeclose();
            }
        });
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView price;
        TextView sinfo;
        MyImageView img;
        ImageButton img_btn;
    }
}
