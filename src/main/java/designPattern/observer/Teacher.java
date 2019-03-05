package designPattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 老师
 *
 * @author bin.yu
 * @create 2019-03-05 19:33
 **/
public class Teacher {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    private List<Student> students;

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
    public void teach(String content) {
        for (Student student : students) {
            student.getMessage(content);
        }
    }


}
