package repository;

import model.Student;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository {
    //In-memory Storage - HashMap for O(1) LookUp by Id
    private final Map<String, Student> storage = new HashMap<>();

    //CRUD Operations
    public void save(Student student) {
        storage.put(student.getStudentId(), student);
    }

    public Optional<Student> findById(String studentId) {
        return Optional.ofNullable(storage.get(studentId));
    }

    public List<Student> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean exists(String studentId) {
        return storage.containsKey(studentId);
    }

    public void delete(String studentId) {
        storage.remove(studentId);
    }

    public int count() {
        return storage.size();
    }

    //File Persistence
    public void saveToFile(String filePath) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write("studentID, name, gpa, course, skills");
            writer.newLine();

            for (Student student : storage.values()){
                String skillsJoined = String.join("|", student.getSkills());
                writer.write(String.format("%s,%s,%.2f,%s,%s",
                        student.getStudentId(),
                        student.getName(),
                        student.getGpa(),
                        student.getCourse(),
                        skillsJoined));
                writer.newLine();
            }
        }
        System.out.println("Saved" + storage.size() + " students to "+ filePath);
    }

    public void loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                } //skip header

                String[] parts = line.split(",");
                if (parts.length < 5) continue; //skip malformed lines

                String studentId = parts[0];
                String name = parts[1];
                double gpa = Double.parseDouble(parts[2]);
                String course = parts[3];
                List<String> skills = Arrays.asList(parts[4].split("\\|"));

                save(new Student(studentId, name, gpa, course, skills));
            }
        }
        System.out.println("Loaded " + storage.size() + " students from " + filePath);

    }

}
