package com.iastate.i_attend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Yuxiang Chen on 2016/11/24.
 */

public class UsersDataSource {

    private static UsersDataSource dsInstance = null;

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public static UsersDataSource getDsInstance(Context context){
        if(dsInstance == null){
            dsInstance = new UsersDataSource(context.getApplicationContext());
        }
        return dsInstance;
    }

    private UsersDataSource(Context context){
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public User createUser(String userName, String userType){
        ContentValues newUserContent = new ContentValues();
        newUserContent.put(dbHelper.COLUMN_USERNAME, userName);
        newUserContent.put(dbHelper.COLUMN_USER_TYPE, userType);

        long id = database.insert(dbHelper.TABLE_USERS, null, newUserContent);

        User newUser = getUser(id);
        return newUser;
    }

    public User getUser(long id){
        String[] id_string = {Long.toString(id)};
        Cursor cursor = database.query(SQLiteHelper.TABLE_USERS, new String[] {SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_USERNAME, SQLiteHelper.COLUMN_USER_TYPE}, "_id=?", id_string, null, null, null);
        cursor.moveToFirst();
        User toReturn = cursorToUser(cursor);
        return toReturn;
    }

    public String getUserType(String username){
        Cursor cursor = database.query(SQLiteHelper.TABLE_USERS, new String[] {SQLiteHelper.COLUMN_USER_TYPE}, "username=?", new String[] {username}, null, null, null);
        cursor.moveToFirst();
        String usertype = "";
        if(cursor.getCount() != 0)
                usertype = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_USER_TYPE));
        return usertype;
    }

    private User cursorToUser(Cursor cursor){
        Log.d("Cols", "" + cursor.getColumnIndex(SQLiteHelper.COLUMN_ID) + " " + cursor.getColumnIndex(SQLiteHelper.COLUMN_USERNAME) + " " + cursor.getColumnIndex(SQLiteHelper.COLUMN_USER_TYPE));
        User user = new User(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_USERNAME)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_USER_TYPE)));
        return user;
    }


}
