package com.iastate.i_attend;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Rushabh on 11/26/16.
 */

public class Course {

    private long id;
    private long instructorId;
    private String instructirEmail;
    private String courseName;
    private LatLng latLng;

    public Course(long id, String name){
        this.id = id;
        courseName = name;
    }

    public Course(long id, String name, long instructorID){
        this.id = id;
        courseName = name;
        instructorId = instructorID;
    }

    public Course(long id, String name, String instructirEmail, LatLng latLng){
        this.id = id;
        courseName = name;
        this.instructirEmail = instructirEmail;
        this.latLng = latLng;
    }

    public String getCourseName(){
        return courseName;
    }

    public long getInstructorId(){
        return instructorId;
    }

    public String getInstructirEmail(){ return instructirEmail; }
    public LatLng getLatLng(){ return latLng; }
}
