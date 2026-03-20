package service;

import model.Student;
import repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private final StudentRepository repository;

    // Constructor injection - makes testing with mockito easy
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    // Core Operations
    public void enrollStudent(Student student){
        if (repository.exists(student.getStudentId())){
            throw new IllegalArgumentException(
                    "Student with ID " + student.getStudentId() + " alerady exists"
            );
        }

        repository.save(student);
    }

    public Optional<Student> findStudent(String studentId){
        return repository.findById(studentId);
    }

    public void removeStudent(String studentID){
        if(!repository.exists(studentID)){
            throw new IllegalArgumentException(
                    "Student with ID " + studentID + " not found"
            );
        }
        repository.delete(studentID);
    }

    public void updateGpa(String StudentId, double newGpa){
        Student student = repository.findById(StudentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found: " + StudentId
                ));

        student.setGpa(newGpa);
        repository.save(student);
    }

    // Stream powered Querry methods
    public List<Student> getTopStudents(double minGpa){
        return repository.findAll().stream()
                .filter(s -> s.getGpa() >= minGpa)
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .collect(Collectors.toList());
    }

    public Map<String, List<Student>> groupByCourse(){
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Student::getCourse));
    }

    public List<Student> searchByName(String name){
        return repository.findAll().stream()
                .filter(s -> s.getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Student> getTopRankedStudents(){
        return repository.findAll().stream()
                .max(Comparator.comparingDouble(Student::getGpa));
    }

    public Double getAverageGpa(){
        return repository.findAll().stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    public Map<String, Long> getStudentCountPerCourse(){
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Student::getCourse,
                        Collectors.counting()
                ));
    }

    public int getTotalStudents(){
        return repository.count();
    }
}
