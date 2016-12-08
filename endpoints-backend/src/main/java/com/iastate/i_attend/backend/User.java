package com.iastate.i_attend.backend;

import java.util.ArrayList;

/**
 * Created by Rushabh on 2016/11/24.
 */

public class User {

    private long id;
    private String userName;
    private String userType;
    private String userEmail;
    private ArrayList<Course> studentClasses;
    private ArrayList<Course> instructorClasses;

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
        if(userType.equals("Student")){
            this.studentClasses= classes;
        }else{
            this.instructorClasses= classes;
        }
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

    public ArrayList<Course> getUserClasses(){
        if(userType.equals("Student")){
            return studentClasses;
        }else{
            return instructorClasses;
        }
    }

    public void addClass(Course course){
        if(userType.equals("Student")){
            studentClasses.add(course);
        }else{
            instructorClasses.add(course);
        }
    }

}
