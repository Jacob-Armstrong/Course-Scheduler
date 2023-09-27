package scheduler.interfaces;

import scheduler.classes.*;

import java.io.IOException;
import java.util.ArrayList;

public interface SchedulerInterface {

    // Manual manipulation of students
    void addStudent(Student newStudent);
    Student removeStudent(IdGenerator ID);

    // Manual manipulation of faculty
    void addFaculty(Faculty newFaculty);
    Faculty removeFaculty(IdGenerator ID);

    // Manual manipulation of courses
    void addCourse(Course newCourse);
    Course removeCourse(String ID);

    // Get a list of courses
    ArrayList<String> getCourses();

    // Scheduling implementation
    void scheduleFaculty();
    void scheduleStudents();

    // Apply relevant rules to schedule
    void cull();

    // Output
    void printEverything();
    void printStats();
    void writeToFiles(String scheduledPath, String unscheduledPath, String facultyPath, String studentPath, String unscheduledStudentPath) throws IOException;


}