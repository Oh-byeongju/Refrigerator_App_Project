package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private  Context context;
    private static final String DATABASE_NAME = "myref.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ref01";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "p_name";
    private static final String COLUMN_ST = "p_st";
    private static final String COLUMN_CNT = "p_cnt";
    private static final String COLUMN_DATE = "p_date";
    private static final String COLUMN_MEMO = "p_memo";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER primary key autoincrement, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ST + " TEXT, "
                + COLUMN_CNT + " INTEGER, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_MEMO + " TEXT); ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists " + TABLE_NAME;

        db.execSQL(sql);
        onCreate(db);
    }

    public void addInfo(String p_name, String p_st, int p_cnt, String p_date, String p_memo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, p_name);
        cv.put(COLUMN_ST, p_st);
        cv.put(COLUMN_CNT, p_cnt);
        cv.put(COLUMN_DATE, p_date);
        cv.put(COLUMN_MEMO, p_memo);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readInfo(String storage){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where p_st = " + "\"" + storage + "\"" , null);
        return res;
    }

    public Cursor p_select(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor selectInfo(String name, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where p_name = " + "\"" + name + "\"" + " and p_date = " + "\"" + date + "\"" , null);
        return res;
    }

    public void DeleteInfo(String name, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE p_name = " + "\"" + name + "\"" + " AND p_date = " + "\"" + date + "\"");
        db.close();
    }

    public void updateData(String name, String st, Integer cnt, String date, String memo, String p_name, String p_date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET p_name = " + "\"" + name + "\"" + ", p_st = " + "\"" + st + "\"" + ", p_cnt = " + cnt + ", " +
                "p_date = " + "\"" + date + "\"" + ", p_memo = " + "\"" + memo + "\"" +" WHERE p_name = " + "\"" + p_name + "\"" + " AND p_date = " + "\"" + p_date + "\"");
        db.close();
    }
}
