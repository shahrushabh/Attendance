package com.iastate.i_attend;

/**
 * Created by Rushabh on 11/26/16.
 */

public class Course {

    private long id;
    private long instructorId;
    private String courseName;

    public Course(long id, String name){
        this.id = id;
        courseName = name;
    }

    public Course(long id, String name, long instructorID){
        this.id = id;
        courseName = name;
        instructorId = instructorID;
    }

    public String getCourseName(){
        return courseName;
    }

    public long getInstructorId(){
        return instructorId;
    }
}
