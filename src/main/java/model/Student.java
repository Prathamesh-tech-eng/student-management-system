package model;

import java.util.List;
import java.util.Objects;

public class Student {
    private String studentId;
    private String name;
    private double gpa;
    private String course;
    private List<String> skills;

    public Student(String StudentId, String name, Double gpa, String course, List<String> skills){
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        this.course = course;
        this.skills = skills;
    }

    //Getters
    public String getStudentId(){ return studentId; }
    public String getName(){ return name; }
    public double getGpa() { return gpa; }
    public String getCourse(){ return course; }
    public List<String> stringList() { return  skills; }


    //Setters
    public void setGpa(double gpa){ this.gpa = gpa; }
    public  void setCourse(String course){ this.course = course; }

    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(studentId, student.studentId);  //equality by ID
    }

    public  int hashCode(){
        return Objects.hash(studentId);
    }

    public String toString(){
        return String.format("Student{id='%s', name='%s', gpa=%.2f, course='%s'}",
                studentId, name, gpa, course );
    }
}
