package com.iastate.i_attend;

/**
 * Created by Yuxiang Chen on 2016/11/24.
 */

public class User {

    private long id;
    private String userName;
    private String userType;

    public User(long id, String name, String type){
        this.id = id;
        userName = name;
        userType = type;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserType(){
        return userType;
    }

}
