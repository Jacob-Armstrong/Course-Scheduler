package scheduler.classes;

import java.util.ArrayList;
import java.util.Date;

public class Faculty extends Person {

    // Required Object data
    private String hireDate;
    private boolean tenured;

    /**
     * [CONTROL] Customizable limit
     */

    private int maxCourses;

    /**
     * [CONTROL] Customizable limit
     */

    private int maxSessions;

    /**
     * Limit tracking
     */

    private int numCourses = 0;

    /**
     * Limit tracking
     */

    private int numSessions = 0;

    // Arbitrary list of Course & Session names for output
    private ArrayList<String> courses;
    private ArrayList<String> sessions;

    //////////////////////////
    //     CONSTRUCTORS     //
    //////////////////////////

    /**
     * Default constructor
     */
    public Faculty() {
        super();
        tenured = false;
    }

    /**
     * The constructor my input will be using
     * @param firstName Faculty's first name
     * @param middleName Faculty's middle name
     * @param lastName Faculty's last name
     * @param email Faculty's email address
     * @param phone Faculty's phone number
     * @param streetAddress Faculty's street address
     * @param city Faculty's city
     * @param state Faculty's state
     * @param zip Faculty's zip/postal code
     * @param hireDate Date the faculty member was hired
     * @param tenured Whether or not the faculty member is tenured
     * @param maxCourses [CONTROL] Amount of courses a faculty member can teach
     * @param maxSessions [CONTROL] Amount of sessions in their courses a faculty member can teach
     */
    public Faculty(String firstName, String middleName, String lastName, String email, String phone, String streetAddress, String city, String state, int zip, String hireDate, boolean tenured, int maxCourses, int maxSessions) {
        super(firstName, middleName, lastName, email, phone, streetAddress, city, state, zip);
        this.hireDate = hireDate;
        this.tenured = tenured;
        this.maxCourses = maxCourses;
        this.maxSessions = maxSessions;
        courses = new ArrayList<String>();
        sessions = new ArrayList<String>();
    }

    //////////////////////////
    //   GETTERS & SETTERS  //
    //////////////////////////

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String isTenured() {
        if (tenured) {
            return "True";
        } else {
            return "False";
        }
    }

    public void setTenured(boolean tenured) {
        this.tenured = tenured;
    }

    public int getMaxCourses() {
        return maxCourses;
    }

    public void setMaxCourses(int maxCourses) {
        this.maxCourses = maxCourses;
    }

    public int getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public int getNumSessions() {
        return numSessions;
    }

    //////////////////////////
    //        METHODS       //
    //////////////////////////

    /**
     * Adds a course to the list of courses
     * @param courseCode Ex. CS1A or BIO2
     */
    public void addCourse(String courseCode) {
        courses.add(courseCode);
        numCourses++;
    }

    /**
     * Removes a course from the list of courses
     * @param courseCode Ex. CS1A or BIO2
     */
    public void removeCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equalsIgnoreCase(courseCode)) {
                courses.remove(i);
                numCourses--;
            }
        }
    }

    /**
     * Adds a session to the list of sessions
     * @param sessionID SessionIdGenerator's toString ID
     */
    public void addSession(String sessionID) {
        sessions.add(sessionID);
        numSessions++;
    }

    /**
     * Removes a session from the list of sessions
     * @param sessionID SessionIdGenerator's toString ID
     */
    public void removeSession(String sessionID) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).equalsIgnoreCase(sessionID)) {
                sessions.remove(i);
                numSessions--;
            }
        }
    }

    /**
     * Outputs faculty info as a string
     * @return Faculty info as a string
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("=======");
        sb.append("\n");
        sb.append("\n");
        sb.append("Name: ");
        sb.append(getFullName());
        sb.append("\n");
        sb.append("Email: ");
        sb.append(getEmail());
        sb.append("\n");
        sb.append("Phone: ");
        sb.append(getPhone());
        sb.append("\n");
        sb.append("Address: ");
        sb.append(getStreetAddress()).append(", ").append(getCity()).append(", ").append(getState()).append(", ").append(getZip());
        sb.append("\n");
        sb.append("Hire date: ");
        sb.append(getHireDate());
        sb.append("\n");
        sb.append("Tenured: ");
        sb.append(isTenured());
        sb.append("\n");
        sb.append("ID: ");
        sb.append(getId());
        sb.append("\n");
        sb.append("\n");

        sb.append("Courses taught: ");
        for (int i = 0; i < numCourses; i++) {
            sb.append(courses.get(i));
            if (i != numCourses-1) {
                sb.append(", ");
            }
        }
        sb.append("\n");

        return sb.toString();

    }


}
