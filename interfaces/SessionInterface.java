package scheduler.interfaces;

import scheduler.classes.Faculty;
import scheduler.classes.IdGenerator;
import scheduler.classes.SessionIdGenerator;
import scheduler.classes.Student;

public interface SessionInterface {

    // Manual manipulation of faculty
    void addFaculty(Faculty newFaculty);
    Faculty removeFaculty();

    // Manual manipulation of students
    void addStudent(Student newStudent);
    Student removeStudent(IdGenerator ID);

    // Relevant info about session
    int getNumStudents();
    String getCourseID();
    String getSessionID();
    boolean isFull();
    boolean isTooEmpty();

}
