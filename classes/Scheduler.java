package scheduler.classes;

import scheduler.interfaces.SchedulerInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The scheduler object holds a list of all
 * relevant objects, such as courses, students
 * and faculty, and applies relevant algorithms
 * to them to create a schedule
 */

public class Scheduler implements SchedulerInterface {

    ArrayList<Course> allCourses;
    ArrayList<Student> allStudents;
    ArrayList<Faculty> allFaculty;

    public Scheduler() {
        allCourses = new ArrayList<>();
        allStudents = new ArrayList<>();
        allFaculty = new ArrayList<>();
    }

    /**
     * Manually add a student to the list of courses
     * @param newStudent Student to add
     */
    @Override
    public void addStudent(Student newStudent) {
        allStudents.add(newStudent);
    }

    /**
     * Manually remove a student from the list of courses
     * @param ID IdGenerator
     * @return Student object that was removed
     */
    @Override
    public Student removeStudent(IdGenerator ID) {
        Student removedStudent = new Student();

        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).getId().toString().equalsIgnoreCase(ID.toString())) {
                removedStudent = allStudents.get(i);
                allStudents.remove(i);
            }
        }
        return removedStudent;
    }

    /**
     * Manually add a faculty to the list of faculty
     * @param newFaculty Faculty to add
     */
    @Override
    public void addFaculty(Faculty newFaculty) {
        allFaculty.add(newFaculty);
    }

    /**
     * Manually remove a faculty member from the list of faculty
     * @param ID IdGenerator
     * @return Faculty object that was removed
     */
    @Override
    public Faculty removeFaculty(IdGenerator ID) {
        Faculty removedFaculty = new Faculty();

        for (int i = 0; i < allFaculty.size(); i++) {
            if (allFaculty.get(i).getId().toString().equalsIgnoreCase(ID.toString())) {
                removedFaculty = allFaculty.get(i);
                allFaculty.remove(i);
            }
        }
        return removedFaculty;
    }

    /**
     * Manually add a course to the list of courses
     * @param newCourse Course to add
     */
    @Override
    public void addCourse(Course newCourse) {
        allCourses.add(newCourse);
    }

    /**
     * Manually remove a course from the list of courses
     * @param ID Ex. CS1D or BIO4
     * @return Course object that was removed
     */
    @Override
    public Course removeCourse(String ID) {
        Course removedCourse = new Course();

        for (int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).getCourseId().equalsIgnoreCase(ID)) {
                removedCourse = allCourses.get(i);
                allCourses.remove(i);
            }
        }
        return removedCourse;
    }

    /**
     * Assigns faculty to courses and then to
     * sessions within the course they were
     * assigned to.
     */
    @Override
    public void scheduleFaculty() {

        // Create relevant limits for assigning faculty as easier variables
        int maxCourseTeach = allFaculty.get(0).getMaxCourses();
        int maxFacultyPerCourse = allCourses.get(0).getMaxFaculty();
        int maxSessionTeach = allFaculty.get(0).getMaxSessions();
        int maxSessions = allCourses.get(0).getNumSessionsToGenerate();

        // First, assign faculty members to the courses

        Course currentCourse; // Course we're working with in the loop for sake of simplicity
        Faculty currentFaculty; // ^^ but faculty

        for (int listCourses = 0; listCourses < allCourses.size(); listCourses++) { // For each available course...
            currentCourse = allCourses.get(listCourses);
            if (currentCourse.getNumFaculty() < currentCourse.getMaxFaculty()) { // If the course has less faculty than max...
                for (int i = 0; i < allFaculty.size(); i++) { // For each available faculty member...
                    currentFaculty = allFaculty.get(i);
                    if (currentFaculty.getNumCourses() < maxCourseTeach && currentCourse.getFaculty().size() < maxFacultyPerCourse) { // If they're not teaching the max number of courses...
                        currentCourse.addFaculty(currentFaculty); // Add that faculty member to the course.
                        currentFaculty.addCourse(currentCourse.getCourseId());
                    }
                }
            }
        }

        // Now that every course has X faculty members... every course has Y sessions. Let's assign them some sessions!

        for (int listCourses = 0; listCourses < allCourses.size(); listCourses++) {
            currentCourse = allCourses.get(listCourses);

            // I don't think these variables are used anymore but I'm scared it'll break if I remove them x.x
            int currentSession = 0;
            int facultyInCourse = currentCourse.getFaculty().size();
            //

            // Random number for session assignment
            Random rand = new Random();

            for (int i = 0; i < currentCourse.getSessions().size(); i++) { // For Y amount of sessions...
                if (currentCourse.getNumFaculty() != 0) { // If the course has assigned faculty...
                    int randomDistribution = rand.nextInt(currentCourse.getNumFaculty()); // Random number with a range of how many faculty are teaching (Z)

                    if (currentCourse.getFaculty().get(randomDistribution).getNumSessions() < maxSessionTeach) {
                        currentCourse.getSessions().get(i).addFaculty(currentCourse.getFaculty().get(randomDistribution)); // Assign this session to faculty member Z
                    }
                }
            }

            // WARNING : BAD AND DUMB CODE BELOW

            /*
            for (int i = 0; i < facultyInCourse; i++) {

                currentFaculty = currentCourse.getFaculty().get(i);

                if (currentFaculty.getNumSessions() < maxSessionTeach && currentSession <= maxSessions && !currentCourse.getSessions().get(currentSession).hasFaculty()) {
                    currentCourse.getSessions().get(currentSession).addFaculty(currentCourse.getFaculty().get(i));
                    currentSession++;
                }
            }
             */
        }
    }

    /**
     * Assigns students to sessions of a course
     * if the course is on their wishlist and
     * they're not enrolled in that course yet
     */
    @Override
    public void scheduleStudents() {

        for (int i = 0; i < allCourses.size(); i++) { // For every available course...

            String courseTBAssigned = allCourses.get(i).getCourseId();

            Course currentCourse = allCourses.get(i);

            for (int j = 0; j < allStudents.size(); j++) { // For every student...

                Student currentStudent = allStudents.get(j); // Student we're working with in the loop

                if (!currentStudent.isInCourse(courseTBAssigned) && !currentCourse.allSessionsFull() && currentStudent.wantsCourse(courseTBAssigned) && currentStudent.getNumCourses() != currentStudent.getMAX_COURSES()) {
                    currentCourse.assignStudentToSession(currentStudent);
                }
            }
        }
    }

    /**
     * Cancels individual sessions if they
     * do not have enough students, and then
     * the entire course if that causes the course
     * to fall below the minimum limits.
     */
    @Override
    public void cull() {

        int currentCourseNum = allCourses.size();

        for (int i = 0; i < currentCourseNum; i++) { // For every course in the list...

            Course currentCourse = allCourses.get(i); // Course we're working with this iteration
            int totalStudents = currentCourse.getTotalStudents();
            int minStudents = currentCourse.getMinStudentsPerCourse();

            if (totalStudents < minStudents && !currentCourse.isCancelled()) { // If there aren't enough students in the whole course
                currentCourse.cancelCourse(); // Cancel course
            }

            if (currentCourse.getSessions().size() != 0) { // If this course hasn't been cancelled...
                for (int j = 0; j < currentCourse.getSessions().size(); j++) { // For every session in that course...
                    if (currentCourse.getSessions().get(j).isTooEmpty() && currentCourse.getSessions().get(j).hasFaculty()) { // If the session is too empty...
                        int studentsDisplaced = currentCourse.sessions.get(j).cancelSession(); // Cancel session
                        currentCourse.removeStudents(studentsDisplaced);
                        currentCourse.removeSession(currentCourse.getSessions().get(j).getSessionID()); // Remove session from course
                    }
                }
            }

            // At this point, a session may have been cancelled that cancels the entire class.
            // So... check again...

            if (totalStudents < minStudents && !currentCourse.isCancelled()) {
                currentCourse.cancelCourse();
            }
        }
    }

    /**
     * Prints out all relevant course
     * information, including who is
     * teaching it and who is in what
     * session.
     */
    public void printEverything() {

        Course currentCourse; // Course we're working with

        for (int i = 0; i < allCourses.size(); i++) { // Courses :D
            currentCourse = allCourses.get(i);
            if (!currentCourse.isCancelled()) {
                System.out.println("=============== " + currentCourse.getCourseId() + " ===============");
                System.out.println(currentCourse.getDescription());
                System.out.println("\nProfessors teaching this course:\n");
                for (int j = 0; j < currentCourse.getNumFaculty(); j++) {
                    System.out.println(currentCourse.getFaculty().get(j).getFirstName() + " " + currentCourse.getFaculty().get(j).getLastName());
                }

                System.out.println();
                System.out.println("Number of total students: " + currentCourse.getTotalStudents());
                System.out.println();

                int sessionsInCurrentCourse = currentCourse.sessions.size();

                System.out.println("Sessions of " + currentCourse.getCourseId() + ":\n"); // Sessions :D
                for (int j = 0; j < sessionsInCurrentCourse; j++) {
                    if (currentCourse.sessions.get(j).hasFaculty()) {
                        System.out.println("> Session id: " + currentCourse.getSessions().get(j).getSessionID());
                        System.out.println("> Faculty teaching: " + currentCourse.getSessions().get(j).getFacultyName() + " - " + currentCourse.getSessions().get(j).getFaculty().getId());
                        System.out.println("> Students in this session " + "(" + currentCourse.getSessions().get(j).getNumStudents() + "):");
                        currentCourse.getSessions().get(j).listStudents();
                        System.out.println("\n<><><><><>\n");
                    }
                }
            }
        }
    }

    /**
     * Prints statistics of the program
     */
    public void printStats() {
        System.out.println("====================");
        System.out.println(" Program Statistics ");
        System.out.println("====================");
        System.out.println();
        System.out.println("Total students: " + allStudents.size());
        System.out.println("Total faculty: " + allFaculty.size());
        System.out.println("Total Courses: " + allCourses.size());
        System.out.print("Total sessions scheduled: ");
        int totalSessions = 0;
        for (int i = 0; i < allCourses.size(); i++) {
            if (!allCourses.get(i).isCancelled()) {
                if (allCourses.get(i).getSessions().size() != 0) {
                    totalSessions += allCourses.get(i).getSessions().size();
                }
            }
        }
        System.out.println(totalSessions);
        System.out.print("Total courses unscheduled: ");
        int cancelledCourses = 0;
        for (int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).isCancelled()) {
                cancelledCourses++;
            }
        }
        System.out.println(cancelledCourses);
        System.out.print("Total students with no classes: ");
        int unscheduledStudents = 0;
        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).getNumCourses() == 0) {
                unscheduledStudents++;
            }
        }
        System.out.println(unscheduledStudents);
    }

    /**
     * Writes all relevant information to the
     * txt files specified by parameters
     * @param scheduledPath path to txt for scheduled courses
     * @param unscheduledPath path to txt for unscheduled courses
     * @param facultyPath path to txt for faculty members
     * @param studentPath path to txt for scheduled students
     * @param unscheduledStudentPath path to txt for unscheduled students
     * @throws IOException
     */
    public void writeToFiles(String scheduledPath, String unscheduledPath, String facultyPath, String studentPath, String unscheduledStudentPath) throws IOException {
        Course currentCourse; // Course we're working with

        BufferedWriter bw = new BufferedWriter(new FileWriter(scheduledPath));

        for (int i = 0; i < allCourses.size(); i++) { // Courses :D
            currentCourse = allCourses.get(i);
            if (!currentCourse.isCancelled()) {
                bw.write("=============== " + currentCourse.getCourseId() + " ===============");
                bw.newLine();
                bw.write(currentCourse.getDescription());
                bw.newLine();
                bw.write("\nProfessors teaching this course:\n");
                for (int j = 0; j < currentCourse.getNumFaculty(); j++) {
                    bw.write(currentCourse.getFaculty().get(j).getFirstName() + " " + currentCourse.getFaculty().get(j).getLastName());
                    bw.newLine();
                }

                bw.newLine();
                bw.write("Number of total students: " + currentCourse.getTotalStudents());
                bw.newLine();
                bw.newLine();

                int sessionsInCurrentCourse = currentCourse.sessions.size();

                bw.write("Sessions of " + currentCourse.getCourseId() + ":\n"); // Sessions :D
                bw.newLine();
                for (int j = 0; j < sessionsInCurrentCourse; j++) {
                    if (currentCourse.sessions.get(j).hasFaculty()) {
                        bw.write("> Session id: " + currentCourse.getSessions().get(j).getSessionID());
                        bw.newLine();
                        bw.write("> Faculty teaching: " + currentCourse.getSessions().get(j).getFacultyName() + " - " + currentCourse.getSessions().get(j).getFaculty().getId());
                        bw.newLine();
                        bw.write("> Students in this session " + "(" + currentCourse.getSessions().get(j).getNumStudents() + "):");
                        bw.newLine();
                        for (int k = 0; k < currentCourse.getSessions().get(j).getNumStudents(); k++) {
                            bw.write("--> " + currentCourse.getSessions().get(j).getStudents().get(k).getFirstName() + " " + currentCourse.getSessions().get(j).getStudents().get(k).getLastName() + " [" + currentCourse.getSessions().get(j).getStudents().get(k).getId() + "]");
                            if (k < currentCourse.getSessions().get(j).getNumStudents()-1) {
                                bw.newLine();
                            }
                        }
                        bw.newLine();
                        bw.newLine();
                        bw.write("<><><><><>");
                        bw.newLine();
                        bw.newLine();
                    }
                }
            }
        }

        bw.close();

        bw = new BufferedWriter(new FileWriter(unscheduledPath));

        for (int i = 0; i < allCourses.size(); i++) {
            currentCourse = allCourses.get(i);
            if (currentCourse.isCancelled()) {
                bw.write("=============== " + currentCourse.getCourseId() + " ===============");
                bw.newLine();
                bw.write(currentCourse.getDescription());
                bw.newLine();
                bw.newLine();
                bw.write("Number of students that needed to be in this class: " + currentCourse.getMinStudentsPerCourse());
                bw.newLine();
                bw.newLine();
            }
        }

        bw.close();

        bw = new BufferedWriter(new FileWriter(facultyPath));

        for (int i = 0; i < allFaculty.size(); i++) {
            bw.write(allFaculty.get(i).toString());
            bw.newLine();
            bw.newLine();
        }

        bw.close();

        bw = new BufferedWriter(new FileWriter(studentPath));

        for (int i = 0; i < allStudents.size(); i++) {
            if(allStudents.get(i).getNumCourses() != 0) {
                bw.write(allStudents.get(i).toString());
                bw.newLine();
                bw.newLine();
            }
        }

        bw.close();

        bw = new BufferedWriter(new FileWriter(unscheduledStudentPath));

        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).getNumCourses() == 0) {
                bw.write(allStudents.get(i).toString());
                bw.newLine();
                bw.newLine();
            }
        }

        bw.close();

    }

    public ArrayList<String> getCourses() {
        ArrayList<String> allCoursesString = new ArrayList<>();

        for (int i = 0; i < allCourses.size(); i++) {
            allCoursesString.add(allCourses.get(i).getCourseId());
        }

        return allCoursesString;
    }
}