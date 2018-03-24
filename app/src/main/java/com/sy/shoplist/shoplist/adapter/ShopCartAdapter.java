package com.sy.shoplist.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sy.shoplist.shoplist.R;
import com.sy.shoplist.shoplist.control.MyApplication;
import com.sy.shoplist.shoplist.fragment.ShopcartFragment;
import com.sy.shoplist.shoplist.imageurl.MyImageView;
import com.sy.shoplist.shoplist.javabean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class ShopCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShopInfo> lists = new ArrayList<ShopInfo>();
    public static int sum = 1;//
    private ShopcartFragment fragment;
    private List<String> delet_ids = new ArrayList<String>();

    public ShopCartAdapter(Context context, List<ShopInfo> list, ShopcartFragment fragment) {
        this.context = context;
        this.lists = list;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return lists.isEmpty() ? 0 : lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.isEmpty() ? null : lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopInfo info = lists.get(i);
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_shopcart, null);
            holder = new ViewHolder();
//            view = LayoutInflater.from(context).inflate(R.layout.item_shoplist, null, false);
            holder.name = (TextView) view.findViewById(R.id.name_cart);
            holder.price = (TextView) view.findViewById(R.id.price_cart);
            holder.sinfo = (TextView) view.findViewById(R.id.info_cart);
            holder.img = (MyImageView) view.findViewById(R.id.img_shop_cart);
            holder.sum = (EditText) view.findViewById(R.id.tv_num);
            holder.btn_add = (Button) view.findViewById(R.id.btn_add);
            holder.btn_sub = (Button) view.findViewById(R.id.btn_sub);
//            holder.delete_shop = (TextView) view.findViewById(R.id.delete_shop);
            holder.check = (CheckBox) view.findViewById(R.id.check);
            view.setTag(holder);//复用vh
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final String sname = info.getSname();
        final int id = info.getId();
        final String img = info.getImgurl();
        holder.name.setText(sname);
        holder.price.setText(info.getPrice());
        holder.sinfo.setText(info.getDetails());
        holder.img.setImageURL(info.getImgurl());
        holder.sum.setText(info.getSum() + "");
        final ViewHolder finalHolder = holder;
        //选取要删除的购物车商品
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalHolder.check.isChecked()) {
                    delet_ids.add(id+"");
                } else if (delet_ids.size() > 0) {
                    delet_ids.remove(id+"");

                }
            }
        });
//        holder.delete_shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                MyApplication.dao.deldepartments(sname);
//                MyApplication.dao.delete_by_id(id);
//                fragment.updateAdapter();
//            }
//        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now_number = finalHolder.sum.getText().toString();
                int num = Integer.parseInt(now_number) + 1;
                finalHolder.sum.setText("" + num);
                MyApplication.dao.Updateshop(num, id);
            }
        });
        holder.btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now_number = finalHolder.sum.getText().toString();
                int i = Integer.parseInt(now_number);
                if (i > 1) {
                    int num = i - 1;
                    finalHolder.sum.setText("" + num);
                    MyApplication.dao.Updateshop(num, id);
                }
            }
        });
        return view;
    }

    final class ViewHolder {
        TextView name;
        TextView price;
        TextView sinfo;
//        TextView delete_shop;
        Button btn_add;
        Button btn_sub;
        EditText sum;
        MyImageView img;
        CheckBox check;
    }

    public List<String> get_ids() {
        return delet_ids;
    }
}
