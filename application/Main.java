package scheduler.application;

import scheduler.classes.Course;
import scheduler.classes.Faculty;
import scheduler.classes.Scheduler;
import scheduler.classes.Student;
import scheduler.interfaces.SchedulerInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// CONTROL VARIABLES:
// Line 1: maxCourses
// Line 2: Faculty maxCourses
// Line 3: Faculty maxSessions
// Line 4: Max students per course
// Line 5: Min students per course
// Line 6: Max students per session
// line 7: Min students per session
// Line 8: Number of sessions to generate
// Line 9: Max faculty per course

public class Main {

    public static void main(String[] args) throws IOException {

        SchedulerInterface mySchedule = new Scheduler();

        int[] controlVariables = inputControlVariables("C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\inputfiles\\controlVariables.txt");

        ArrayList<Course> inputCourses = courseFileInput("C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\inputfiles\\courseInfo.txt", controlVariables);

        for (int i = 0; i < inputCourses.size(); i++) {
            mySchedule.addCourse(inputCourses.get(i));
        }

        ArrayList<Faculty> inputFaculty = facultyFileInput("C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\inputfiles\\facultyInfo.txt", controlVariables[1], controlVariables[2]);

        for (int i = 0; i < inputFaculty.size(); i++) {
            mySchedule.addFaculty(inputFaculty.get(i));
        }

        ArrayList<Student> inputStudents = studentFileInput("C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\inputfiles\\studentInfo.txt", mySchedule, controlVariables[0]);

        for (int i = 0; i < inputStudents.size(); i++) {
            mySchedule.addStudent(inputStudents.get(i));
        }

        mySchedule.scheduleFaculty();

        mySchedule.scheduleStudents();

        mySchedule.cull();

        mySchedule.printStats();

        // mySchedule.printEverything(); // Optional printout to console

        String scheduledPath = "C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\outputfiles\\scheduledCourseSessions.txt";
        String unscheduledPath = "C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\outputfiles\\unscheduledCourseSessions.txt";
        String facultyPath = "C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\outputfiles\\faculty.txt";
        String studentsPath = "C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\outputfiles\\scheduledStudents.txt";
        String unscheduledStudentsPath = "C:\\Users\\silve\\Documents\\CS_Stuff\\CS4A\\Final Project\\src\\scheduler\\outputfiles\\unscheduledStudents.txt";

        mySchedule.writeToFiles(scheduledPath, unscheduledPath, facultyPath, studentsPath, unscheduledStudentsPath);

    }

    public static ArrayList<String> generateRandomWishlist(SchedulerInterface scheduler, int maxCourses) {
        ArrayList<String> availableCourses = scheduler.getCourses();
        ArrayList<String> wishList = new ArrayList<>();

        Random rand = new Random();
        int randomClassNumber = rand.nextInt(availableCourses.size());

        wishList.add(availableCourses.get(randomClassNumber));

        while (wishList.size() != maxCourses) {

            randomClassNumber = rand.nextInt(availableCourses.size());

            for (int i = 0; i < wishList.size(); i++) {
                if (!wishList.contains(availableCourses.get(randomClassNumber))) {
                    wishList.add(availableCourses.get(randomClassNumber));
                }
            }
        }

        return wishList;

    }

    private static int[] inputControlVariables(String controlVariablesPath) throws IOException {
        ArrayList<Integer> controlVariablesList = new ArrayList<>();

        String line;
        BufferedReader br = new BufferedReader(new FileReader(controlVariablesPath));

        while ((line = br.readLine()) != null) {
            controlVariablesList.add(Integer.parseInt(line));
        }

        int[] controlVariables = new int[9];

        for (int i = 0; i < controlVariablesList.size(); i++) {
            controlVariables[i] = controlVariablesList.get(i);
        }


        return controlVariables;
      
    }

    public static ArrayList<Student> studentFileInput(String studentFilePath, SchedulerInterface scheduler, int maxCourses) throws IOException {

        ArrayList<Student> inputStudents = new ArrayList<>();

        String line;
        BufferedReader br = new BufferedReader(new FileReader(studentFilePath));

        while((line = br.readLine()) != null) {

            String[] values = line.split(",");

            String firstName = values[0];
            String middleName = values[1];
            String lastName = values[2];
            String email = values[3];
            String phone = values[4];
            String streetAddress = values[5];
            String city = values[6];
            String state = values[7];
            int zip = Integer.parseInt(values[8]);
            String birthday = values[9];
            double GPA = Double.parseDouble(values[10]);
            String startDate = values[11];

            ArrayList<String> wishList = generateRandomWishlist(scheduler, maxCourses);

            Student temp = new Student(firstName, middleName, lastName, email, phone, streetAddress, city, state, zip, birthday, GPA, startDate, wishList, maxCourses);

            inputStudents.add(temp);

        }

        return inputStudents;

    }

    public static ArrayList<Faculty> facultyFileInput(String facultyFilePath, int maxCourses, int maxSessions) throws IOException {

        ArrayList<Faculty> inputFaculty = new ArrayList<>();

        String line;
        BufferedReader br = new BufferedReader(new FileReader(facultyFilePath));

        while((line = br.readLine()) != null) {

            String[] values = line.split(",");

            String firstName = values[0];
            String middleName = values[1];
            String lastName = values[2];
            String email = values[3];
            String phone = values[4];
            String streetAddress = values[5];
            String city = values[6];
            String state = values[7];
            int zip = Integer.parseInt(values[8]);
            String hireDate = values[9];
            boolean tenured = Boolean.parseBoolean(values[10]);

            Faculty temp = new Faculty(firstName, middleName, lastName, email, phone, streetAddress, city, state, zip, hireDate, tenured, maxCourses, maxSessions);

            inputFaculty.add(temp);

        }

        return inputFaculty;

    }

    public static ArrayList<Course> courseFileInput(String courseFilePath, int[] controlVariables) throws IOException {

        ArrayList<Course> inputCourse = new ArrayList<>();

        String line;
        BufferedReader br = new BufferedReader(new FileReader(courseFilePath));

        while((line = br.readLine()) != null) {

            String[] values = line.split(",");

            String department = values[0];
            String code = values[1];
            String description = values[2];

            Course temp = new Course(department, code, description, controlVariables[3], controlVariables[4], controlVariables[5], controlVariables[6], controlVariables[7], controlVariables[8]);

            inputCourse.add(temp);

        }

        return inputCourse;

    }


}
