package scheduler.classes;

import java.util.ArrayList;

public class Course {

    private String department;
    private String code;
    private String description;

    private int maxStudentsPerCourse;
    private int minStudentsPerCourse;
    private int maxStudentsPerSession;
    private int minStudentsPerSession;

    private boolean cancelled;

    private int numFaculty;
    ArrayList<Faculty> faculty;
    private int maxFaculty;
    private int totalStudents;

    private int numSessionsToGenerate;

    ArrayList<Session> sessions;

    private String courseId;

    //////////////////////////
    //     CONSTRUCTORS     //
    //////////////////////////

    public Course() {
    }

    public Course(String department, String code, String description, int maxStudentsPerCourse, int minStudentsPerCourse, int maxStudentsPerSession, int minStudentsPerSession, int numSessionsToGenerate, int maxFaculty) {
        this.department = department;
        this.code = code;
        this.description = description;
        this.maxStudentsPerCourse = maxStudentsPerCourse;
        this.minStudentsPerCourse = minStudentsPerCourse;
        this.maxStudentsPerSession = maxStudentsPerSession;
        this.minStudentsPerSession = minStudentsPerSession;
        this.numSessionsToGenerate = numSessionsToGenerate;
        this.maxFaculty = maxFaculty;

        courseId = department + code;

        sessions = new ArrayList<>();
        faculty = new ArrayList<>();

        for (int i = 0; i < numSessionsToGenerate; i++) {
            Session generateSessions = new Session(getCourseId(), getMaxStudentsPerSession(), getMinStudentsPerSession());
            sessions.add(generateSessions);
        }

        totalStudents = 0;
        cancelled = false;

    }

    //////////////////////////
    //   GETTERS & SETTERS  //
    //////////////////////////

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxStudentsPerCourse() {
        return maxStudentsPerCourse;
    }

    public void setMaxStudentsPerCourse(int maxStudentsPerCourse) {
        this.maxStudentsPerCourse = maxStudentsPerCourse;
    }

    public int getMinStudentsPerCourse() {
        return minStudentsPerCourse;
    }

    public void setMinStudentsPerCourse(int minStudentsPerCourse) {
        this.minStudentsPerCourse = minStudentsPerCourse;
    }

    public int getMaxStudentsPerSession() {
        return maxStudentsPerSession;
    }

    public void setMaxStudentsPerSession(int maxStudentsPerSession) {
        this.maxStudentsPerSession = maxStudentsPerSession;
    }

    public int getMinStudentsPerSession() {
        return minStudentsPerSession;
    }

    public void setMinStudentsPerSession(int minStudentsPerSession) {
        this.minStudentsPerSession = minStudentsPerSession;
    }

    public int getNumSessionsToGenerate() {
        return numSessionsToGenerate;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public String getCourseId() {
        return courseId;
    }

    public int getNumFaculty() {
        return numFaculty;
    }

    public int getMaxFaculty() {
        return maxFaculty;
    }

    public void addFaculty(Faculty newFaculty) {
        faculty.add(newFaculty);
        numFaculty++;
    }

    public ArrayList<Faculty> getFaculty() {
        return faculty;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    //////////////////////////
    //        METHODS       //
    //////////////////////////

    /**
     * Determines if every session in the course is full
     * @return Whether or not there are open seats in any courses
     */
    public boolean allSessionsFull() {

        int fullSessions = 0;

        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).isFull()) {
                fullSessions++;
            }
        }

        return fullSessions == sessions.size();

    }

    /**
     * Assigns a student to any session of the course
     * if the course isn't full, has a faculty teaching
     * it and the student wants to be in that course
     * @param student Student to be added
     */
    public void assignStudentToSession(Student student) {
        for (int i = 0; i < sessions.size(); i++) {
            if ((!sessions.get(i).isFull()) && sessions.get(i).hasFaculty()) {
                sessions.get(i).addStudent(student);
                student.addCourse(courseId);
                totalStudents++;
                break;
            }
        }
    }

    /**
     * Removes a session from the list of sessions
     * @param sessionId Session's SessionIdGenerator's toString
     */
    public void removeSession(String sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionID().equalsIgnoreCase(sessionId)) {
                sessions.remove(i);
            }
        }
    }

    /**
     * Cancels the entire course by cancelling
     * all of the sessions, removing the faculty
     * and applying the proper status.
     */
    public void cancelCourse() {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).hasFaculty()) {
                sessions.get(i).cancelSession();
            }
            sessions.get(i).removeAllStudents();
            removeSession(sessions.get(i).getSessionID());
        }
        sessions.clear();
        for (int i = 0; i < faculty.size(); i++) {
            faculty.get(i).removeCourse(courseId);
        }
        faculty.clear();
        cancelled = true;
    }

    /**
     * Removes a number of students from the
     * course, typically used when a session
     * is cancelled but not the whole course
     * @param students Number of students to be removed from the total
     */
    public void removeStudents(int students) {
        totalStudents -= students;
    }

    /**
     * Determines whether or not the entire
     * has been cancelled.
     * @return Whether or not course is cancelled.
     */
    public boolean isCancelled() {
        return cancelled;
    }
}
