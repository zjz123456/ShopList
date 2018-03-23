package com.sy.shoplist.shoplist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUM = "SUM";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_DETAILS = "DETAILS";
    public static final String COLUMN_IMGRUL = "IMGRUL";

    public static final String DATABASE_NAME = "shop.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "goodsinfo";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DataBaseHelper(Context context,String name,int version){
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context,String name){
        this(context, DATABASE_NAME,DATABASE_VERSION);
    }

    public DataBaseHelper(Context context){
        this(context, DATABASE_NAME,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUM + " INTEGER DEFAULT 1," + COLUMN_NAME + " CHAR,"
                + COLUMN_PRICE + " CHAR," + COLUMN_DETAILS + " CHAR," + COLUMN_IMGRUL + " CHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop if table exists"+ TABLE_NAME);
    }
}
