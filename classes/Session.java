package scheduler.classes;

import scheduler.interfaces.SessionInterface;

import java.util.ArrayList;

public class Session implements SessionInterface {

    private Faculty faculty;
    private ArrayList<Student> students;
    private SessionIdGenerator sessionId;
    private String courseId;

    private boolean cancelled;

    private int minStudents;
    private int maxStudents;

    private int numFaculty;
    private int numStudents;

    //////////////////////////
    //     CONSTRUCTORS     //
    //////////////////////////

    public Session(String courseId, int maxStudents, int minStudents) {
        faculty = new Faculty();
        students  = new ArrayList<Student>();
        sessionId = new SessionIdGenerator();
        this.courseId = courseId;
        this.maxStudents = maxStudents;
        this.minStudents = minStudents;
        numFaculty = 0;
        numStudents = 0;
        cancelled = false;
    }

    //////////////////////////
    //   GETTERS & SETTERS  //
    //////////////////////////

    public String getFacultyName() {
        return faculty.getFirstName() + " " + faculty.getLastName();
    }

    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public String getCourseID() {
        return courseId;
    }

    @Override
    public String getSessionID() {
        return sessionId.generateSessionID();
    }

    //////////////////////////
    //        METHODS       //
    //////////////////////////

    /**
     * Adds a faculty member
     * @param newFaculty Faculty member to add
     */
    @Override
    public void addFaculty(Faculty newFaculty) {
        faculty = newFaculty;
        numFaculty++;
    }

    /**
     * Removes the faculty member
     * @return The faculty member that was removed
     */
    @Override
    public Faculty removeFaculty() {
        Faculty returnValue = faculty;
        faculty.removeSession(sessionId.toString());
        faculty = null;
        numFaculty--;
        return returnValue;
    }

    /**
     * Adds a student to the session
     * @param newStudent Student to add
     */
    @Override
    public void addStudent(Student newStudent) {
        students.add(newStudent);
        numStudents++;
    }

    /**
     * Removes a student from the session
     * @param ID Student's IdGenerator's ToString
     * @return Student that was removed
     */
    @Override
    public Student removeStudent(IdGenerator ID) {
        Student removedStudent = new Student();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().toString().equalsIgnoreCase(ID.toString())) {
                removedStudent = students.get(i);
                students.remove(i);
                numStudents--;
            }
        }
        return removedStudent;
    }

    /**
     * Removes all students from the session,
     * and the course from those students
     */
    public void removeAllStudents() {
        for (int i = 0; i < students.size(); i++) {
            students.get(i).removeCourse(courseId);
        }
        students.clear();
    }

    /**
     * Cancels the entire session by removing the faculty
     * and all of the students and applying the
     * proper status
     * @return The number of students removed from the session
     */
    public int cancelSession() {
        int studentsDisplaced = students.size();
        removeFaculty(); // Remove the faculty member
        removeAllStudents(); // Remove all students from that session
        cancelled = true;
        return studentsDisplaced;
    }

    /**
     * Determine whether or not the session
     * has been cancelled
     * @return True or false for session cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns the number of students in the session
     * @return Number of students in the session
     */
    @Override
    public int getNumStudents() {
        return numStudents;
    }

    /**
     * Outputs the students in the session
     * to the console
     */
    public void listStudents() {
        for (int i = 0; i < students.size(); i++) {
            System.out.println("--> " + students.get(i).getFirstName() + " " + students.get(i).getLastName() + " - " + students.get(i).getId());
        }
    }

    /**
     * Determine if the session is full
     * @return Whether or not the session is full
     */
    @Override
    public boolean isFull() {
        return maxStudents == numStudents;
    }

    /**
     * Determine if the session has enough students
     * @return If the session is too empty or not
     */
    @Override
    public boolean isTooEmpty() {
        return numStudents < minStudents;
    }

    /**
     * Returns the list of student objects
     * @return ArrayList of students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Returns whether or not the session has faculty
     * @return Whether or not the session has faculty
     */
    public boolean hasFaculty() { return numFaculty == 1; }
}
