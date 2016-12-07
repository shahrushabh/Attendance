package com.iastate.i_attend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yuxiang Chen on 2016/11/24.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    //Table name
    public static final String TABLE_USERS = "users";

    //Table Columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_USEREMAIL = "useremail";
    public static final String COLUMN_USER_TYPE = "usertype";

    //Database Name
    private static final String DATABASE_NAME = "users.db";

    //Version #
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String usersTable = "create table " + TABLE_USERS + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " VARCHAR(30) NOT NULL, " + COLUMN_USEREMAIL + " VARCHAR(20) " + COLUMN_USER_TYPE + " VARCHAR(12) NOT NULL)";
        sqLiteDatabase.execSQL(usersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(SQLiteHelper.class.getName(),"Upgrading database from version " + i + " to " + i1 + ", which will destory all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }
}
