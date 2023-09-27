package scheduler.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * A student is a Person who can be enrolled in courses.
 */
public class Student extends Person {

    //////////////////////////
    //     DATA MEMBERS     //
    //////////////////////////

    // Required object data
    private String birthday;
    private double GPA;
    private String startDate;

    /**
     * [CONTROL] List of classes student wants to take
     */

    private ArrayList<String> wishList;

    /**
     * [CONTROL] Max amount of courses they can enroll in
     */

    private int MAX_COURSES;

    /**
     * Limit tracking
     */

    private int numCourses;

    /**
     * Arbitrary list of Courses for output
     */

    private ArrayList<String> enrolledCourses;

    //////////////////////////
    //     CONSTRUCTORS     //
    //////////////////////////

    /**
     * Default constructor
     */
    public Student() {
        super();
        GPA = 0;
    }

    /**
     * The constructor my input will be using
     * @param firstName Student's first name
     * @param middleName Student's middle name
     * @param lastName Student's last name
     * @param email Student's email
     * @param phone Student's phone number
     * @param streetAddress Student's street address
     * @param city Student's city
     * @param state Student's state
     * @param zip Student's zip / postal code
     * @param birthday Student's birthday
     * @param GPA Student's GPA
     * @param startDate Date the student started at the college
     * @param wishList List of classes the student wants to take
     * @param MAX_COURSES [Control] Max amount of courses the student can take
     */
    public Student(String firstName, String middleName, String lastName, String email, String phone, String streetAddress, String city, String state, int zip, String birthday, double GPA, String startDate, ArrayList<String> wishList, int MAX_COURSES) {
        super(firstName, middleName, lastName, email, phone, streetAddress, city, state, zip);
        this.birthday = birthday;
        this.GPA = GPA;
        this.startDate = startDate;
        this.wishList = wishList;


        numCourses = 0;
        this.MAX_COURSES = MAX_COURSES;

        enrolledCourses = new ArrayList<String>();

    }

    //////////////////////////
    //   GETTERS & SETTERS  //
    //////////////////////////

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getMAX_COURSES() {
        return MAX_COURSES;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }

    public int getNumCourses() {
        return numCourses;
    }

    //////////////////////////
    //        METHODS       //
    //////////////////////////

    /**
     * Adds a course to the student's list of enrolled courses
     * if it is in their wishlist.
     * @param courseCode Ex. CS1D or BIO3
     */
    public void addCourse(String courseCode) {
        enrolledCourses.add(courseCode);
        for (int i = 0; i < wishList.size(); i++) {
            if (wishList.get(i).equalsIgnoreCase(courseCode)) {
                wishList.remove(i);
            }
        }
        numCourses++;
    }

    /**
     * Removes a course from the student's list of enrolled courses
     * @param courseCode Ex. CS1D or BIO3
     */
    public void removeCourse(String courseCode) {
        for (int i = 0; i < enrolledCourses.size(); i++) {
            if (enrolledCourses.get(i).equalsIgnoreCase(courseCode)) {
                enrolledCourses.remove(i);
                numCourses--;
            }
        }
    }

    /**
     * Checks to see if the student is currently in a certain course
     * @param className Ex. CS1D or BIO3
     * @return Whether or not they're in the course
     */
    public boolean isInCourse(String className) {

        boolean inCourse = false;

        for (int i = 0; i < enrolledCourses.size(); i++) {
            inCourse = enrolledCourses.get(i).equalsIgnoreCase(className);
        }

        return inCourse;
    }

    /**
     * Checks to see if the student has the parameter course in their wishlist
     * @param courseCode Ex. CS1D or BIO3
     * @return Whether or not the course is in their wishlist
     */
    public boolean wantsCourse(String courseCode) {
        for (int i = 0; i < wishList.size(); i++) {
            if (wishList.get(i).equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Outputs student info as a string
     * @return Student info as a string
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
        sb.append("Birthday: ");
        sb.append(getBirthday());
        sb.append("\n");
        sb.append("GPA: ");
        sb.append(getGPA());
        sb.append("\n");
        sb.append("Start date: ");
        sb.append(getStartDate());
        sb.append("\n");
        sb.append("ID: ");
        sb.append(getId());
        sb.append("\n");
        sb.append("Enrolled courses: ");
        for (int i = 0; i < numCourses; i++) {
            sb.append(enrolledCourses.get(i));
            if (i != numCourses-1) {
                sb.append(", ");
            }
        }
        sb.append("\n");

        return sb.toString();

    }
}
