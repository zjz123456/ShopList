package com.sy.shoplist.shoplist.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sy.shoplist.shoplist.control.MyApplication;
import com.sy.shoplist.shoplist.db.DataBaseHelper;
import com.sy.shoplist.shoplist.javabean.ShopInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_DETAILS;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_ID;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_IMGRUL;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_NAME;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_PRICE;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.COLUMN_SUM;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.TABLE_NAME;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class ShopDao {
    private DataBaseHelper mdbhelper;
    private SQLiteDatabase db;
    private static ShopDao mrecordsDao;
    private static Activity activity;
    private List<ShopInfo> list;
    private ShopInfo shop;

    /**
     * 可以执行多线程的单例模型
     */
    public ShopDao(Context activity) {
        mdbhelper = new DataBaseHelper(activity);
    }

    public static ShopDao getInstance(Activity a) {
        if (null == mrecordsDao) {
            synchronized (ShopDao.class) {
                if (null == mrecordsDao) {
                    mrecordsDao = new ShopDao(a);
                }
            }
        }
        activity = a;
        return mrecordsDao;
    }
//    public ShopDao(Context context) {
//        mdbhelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public static ShopDao getInstance(Activity a) {
//        if (null == mrecordsDao) {
//            synchronized (ShopDao.class) {
//                if (null == mrecordsDao) {
//                    mrecordsDao = new ShopDao(a);
//                }
//            }
//        }
//        activity = a;
//        return mrecordsDao;
//    }

    public void execSQL(String sql) {
        try {
            db = mdbhelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            mdbhelper.close();
        }
    }

    //关闭数据库释放内存
    public void closeclose() {
        mdbhelper.close();
        db.close();
    }

    //插入记录
    public long InsertShop(ShopInfo info) {
        long rows = 0;
        db = mdbhelper.getWritableDatabase();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, info.getSname());
        cv.put(COLUMN_PRICE, info.getPrice());
        cv.put(COLUMN_DETAILS, info.getDetails());
        cv.put(COLUMN_IMGRUL, info.getImgurl());
//        cv.put(DataBaseHelper.COLUMN_SUM, info.getSum());
        try {
            rows = db.insert(TABLE_NAME, null, cv);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            closeclose();
        }
        return rows;
    }

    //查询数据
    public List<ShopInfo> getShops() {
        list = new ArrayList<ShopInfo>();

        db = mdbhelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, " _id desc");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//            shop.setId(Integer.valueOf(cursor.getString(0)));
            shop = new ShopInfo();
            shop.setId(cursor.getInt(0));
            shop.setSum(cursor.getInt(1));
            shop.setSname(cursor.getString(2));
            shop.setPrice(cursor.getString(3));
            shop.setDetails(cursor.getString(4));
            shop.setImgurl(cursor.getString(5));
            list.add(shop);
        }
        return list;
    }

    /**
     * 获得所有购物车中的商品价格
     *
     * @return
     */
    public List<Integer> getprice_add() {
        List<Integer> lists = new ArrayList<Integer>();
        db = mdbhelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, " _id desc");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int price = 0;
            String str = cursor.getString(3);
            int sum = cursor.getInt(1);
            str = str.trim();
            //截取.之前的字符串
            String ss = str.substring(0, str.indexOf("."));
//            Log.i("tag",ss);
            String str2 = "";
            if (ss != null && !"".equals(ss)) {
                for (int i = 0; i < ss.length(); i++) {
                    if (ss.charAt(i) >= 48 && ss.charAt(i) <= 57) {
                        str2 += ss.charAt(i);
                    }
                }
            }
            price = Integer.parseInt(str2) * sum;
            lists.add(price);
        }
        return lists;
    }

    /**
     * 添加信息
     *
     * @param info
     */
    public void add_shop(ShopInfo info) {
        String sql = "insert into " + TABLE_NAME + "(" + COLUMN_SUM + "," + COLUMN_NAME + "," +
                COLUMN_PRICE + "," + COLUMN_DETAILS + "," + COLUMN_IMGRUL + ")" + "values(?,?,?,?,?)";
        Object[] object =
                {info.getSum(), info.getSname(), info.getPrice(), info.getDetails(), info.getImgurl()};
        MyApplication.writeDataBase.execSQL(sql, object);
    }

    /**
     * 删除
     *
     * @param names
     */
    public void deldepartments(String names) {

        String sql = "delete from " + TABLE_NAME + " where " + COLUMN_NAME + " = '" + names + "'";
        try {
            db = mdbhelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeclose();
        }

    }

    public void delete_by_id(int id) {

        String sql = "delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + id + "'";
        try {
            db = mdbhelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeclose();
        }

    }

    public void delete_by_img(String img) {

        String sql = "delete from " + TABLE_NAME + " where " + COLUMN_IMGRUL + " = '" + img + "'";
        try {
            db = mdbhelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeclose();
        }
    }

    //修改信息记录
    public long Updateshop(int sum, int id) {
        long rows = 0;
        db = mdbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SUM, sum);
        try {
            rows = db.update(TABLE_NAME, cv, "_id = " + id, null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            closeclose();
        }
        return rows;
    }
}
