package com.sy.shoplist.shoplist.control;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sy.shoplist.shoplist.dao.ShopDao;
import com.sy.shoplist.shoplist.db.DataBaseHelper;

import static com.sy.shoplist.shoplist.db.DataBaseHelper.DATABASE_NAME;
import static com.sy.shoplist.shoplist.db.DataBaseHelper.DATABASE_VERSION;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class MyApplication extends Application {
    // 数据库
    public static DataBaseHelper dbHelper;
    public static SQLiteDatabase readDataBase;
    public static SQLiteDatabase writeDataBase;
    public static Context context;
    public static ShopDao dao;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 初始化数据库
        initDB();
    }

    private void initDB() {
        if (dao == null) {
            dao = new ShopDao(context);
        }
        if (dbHelper == null) {
            dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        if (writeDataBase == null) {
            writeDataBase = dbHelper.getWritableDatabase();
        }
        if (readDataBase == null) {
            readDataBase = dbHelper.getReadableDatabase();
        }
    }

    /**
     * 关闭数据库
     */

    public static void closeDB() {
        if (writeDataBase != null) {
            writeDataBase.close();
        }
        if (readDataBase != null) {
            readDataBase.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
