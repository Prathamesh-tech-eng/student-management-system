//import model.Course;
import model.Student;
import repository.StudentRepository;
import service.StudentService;

import java.io.IOException;
import java.util.List;
//import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException{

        //write up the layers
        StudentRepository repository = new StudentRepository();
        StudentService service = new StudentService(repository);

        // Enroll Students
        service.enrollStudent(new Student("S001", "Prathamesh", 8.65, "Backend Development",
                List.of("java", "Spring", "SQL")));
        service.enrollStudent(new Student("S002", "Mohit", 9.69, "Cloud",
                List.of("AWS", "Docker", "Kubernetes")));
        service.enrollStudent(new Student("S003", "Ramharsh", 8.90, "Full Stack",
                List.of("React", "Java", "MongoDB")));
        service.enrollStudent(new Student("S004", "Priyank", 7.90, "DSA",
                List.of("Arrays", "Trees", "Graph")));
        service.enrollStudent(new Student("S005", "UdayKumar", 8.05, "Data Science",
                List.of("Python", "PowerBI", "SQL")));

        System.out.println("Total Students: " + service.getTotalStudents());

        //Querry Operation
        System.out.println("Top Students with GPA >= 8.5");
        service.getTopStudents(8.5)
                .forEach(System.out::println);

        System.out.println("Grouped by Course");
        service.groupByCourse()
                .forEach((course, student) -> {
                    System.out.println(course + ":");
                    student.forEach(s -> System.out.println(" " + s));
                });

        System.out.println("Search by Name 'a' ");
        service.searchByName("a")
                .forEach(System.out::println);

        System.out.println("Top Ranked Students : ");
        service.getTopRankedStudents()
                .ifPresent(System.out::println);

        System.out.println("Average GPA : ");
        System.out.printf("%.2f%n" ,service.getAverageGpa());

        System.out.println("Student Count Per Course : ");
        service.getStudentCountPerCourse()
                .forEach((course, count) ->
                        System.out.println(course + " : " + count + " Students"));


        //Update and Remove
        System.out.println(" Updating Priyank's GPA : ");
        service.updateGpa("S004", 8.2);
        service.findStudent("S004").ifPresent(System.out::println);

        //File Persistence
        System.out.println(" Saving to CSV ");
        repository.saveToFile("students.csv");

        //Clear and Reload
        StudentRepository freshRepository = new StudentRepository();
        freshRepository.loadFromFile("students.csv");
        System.out.println(" Students after reload : " + freshRepository.count());

    }
}
