package model;

public class Course {
    private String courseId;
    private String name;
    private String Instructor;
    private int maxStudents;

    public Course(String courseId, String name, String Instructor, int maxStudents){
        this.courseId = courseId;
        this.name = name;
        this.Instructor = Instructor;
        this.maxStudents = maxStudents;
    }

    public String getCourseId(){ return  courseId; }
    public String getName(){ return  name; }
    public String getInstructor() { return Instructor; }
    public int getMaxStudents() { return maxStudents; }

    public String toString(){
        return String.format("Course{id='%s', name='%s', Instructor='%s, maxStudent=%d",
                courseId, name, Instructor, maxStudents);
    }
}
