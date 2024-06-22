package com.s22010360.musicaa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName ="register.db";
    public static final String tbName = "DataTable";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "USERNAME";
    public static final String Col_3 = "PASSWORD";

    public DBHelper(@Nullable Context context){
        super(context, DBName,null,1);
        SQLiteDatabase myDB = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ tbName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,username);
        contentValues.put(Col_3,password);
        long result = myDB.insert(tbName,null,contentValues);
        if (result== -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase myDB =this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username= ?",new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }
    public boolean userLogin(String username,String pwd){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + tbName +" where username = ? and password=?", new String[]{username,pwd});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}

