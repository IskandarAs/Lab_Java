package Cash;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import recycler_view.Student;
import recycler_view.StudentsAdapter;

public class StudentsCash {
    private static StudentsCash instance = new StudentsCash();

    private StudentsCash() {}

    public static synchronized StudentsCash getInstance() {
        return instance;
    }

    private Set<Student> students = new LinkedHashSet<>();

    @NonNull
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(@NonNull Student student) {
        students.add(student);
    }

    public boolean contains(@NonNull Student student) {
        return students.contains(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void sortStudents() {
        if (students.size() != 0) {
            ArrayList<Student> alStudents = getStudents();
            Collections.sort(alStudents, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getSurname().compareTo(o2.getSurname());
                }
            });
            students.clear();
            students.addAll(alStudents);
        }
    }
}
