package com.iastate.i_attend;

import java.util.ArrayList;

/**
 * Created by Yuxiang Chen on 2016/11/24.
 */

public class User {

    private long id;
    private String userName;
    private String userType;
    private String userEmail;
    private ArrayList<Course> classes;

    public User(long id, String name, String type){
        this.id = id;
        userName = name;
        userType = type;
    }

    public User(long id, String name, String type, String email){
        this.id = id;
        userName = name;
        userType = type;
        userEmail = email;
    }

    public User(long id, String name, String type, String email, ArrayList<Course> classes){
        this.id = id;
        userName = name;
        userType = type;
        userEmail = email;
        this.classes = classes;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserType(){
        return userType;
    }

    public String getUserEmail(){
        return userEmail;
    }

}
