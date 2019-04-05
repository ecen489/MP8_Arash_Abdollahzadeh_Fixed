package com.example.mp8_arash_abdollahzadeh;

import java.io.Serializable;

public class Grade implements Serializable {

    int course_id;
    String course_name;
    String grade;
    int student_id;
    String student_name;

    public Grade() {}
    public Grade(int course, String name, String grad, int ID){
        course_id = course;
        course_name=name;
        grade = grad;
        student_id = ID;
    }

    public int getcourse_id()       { return course_id; }
    public String getcourse_name()  { return course_name; }
    public String getgrade() { return grade; }
    public int getstudent_id() { return student_id;}

    public String getStudent_name() {
        return student_name;
    }
}



