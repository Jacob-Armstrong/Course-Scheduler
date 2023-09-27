# Spring 2021 Final Project (Java)

For the sake of organization, I have put all of the files with code
into the same structure as my actual project. This project was designed
to work on my local machine (as per the requirements of the assignment)
so some tweaks would be needed to input/output paths.

## Application

> Main.java

This is the file that runs the program. In it,
there are functions for input/output of files,
which will not work because it requires paths
that are on my computer, not on here.

Aside from the file input/output, there are only
4 functions: scheduleFaculty, scheduleStudents (these two
are self explanatory), cull (applying the rules for whether
or not a class is cancelled) and printStats (self explanatory).

All four of these functions are found in the Scheduler class in
the "classes" folder if you would like to see them.

## Classes

> Course.java

The class for Course objects. This contains all relevant 
information to the courses, and a little bit of
additional functionality at the bottom, which is 
commented with what their purporse is.

> Session.java

These are unique sessions of a course. For example, if Saddleback
teaches BIO100, each of these are individual instances of that course.
A 10:00am-12:00pm with Joe and Bob is one session, but there are
multiple instances of this class being taught at different times or by
different people.

> Person.java, Faculty.java, Student.java

This class is a base class which contains data that
both "students" and "faculty" will have, as to avoid
redundancy. Student and Faculty are derived (inherit from) 
Person, but they have separate data in them that makes them
unique, as well as additional extra functions.

> IdGenerator.java, SessionIdGenerator.java

These are just a way to generate unique IDs for people
and sessions. It just creates a random UUID string and
makes some changes to fit what I needed for IDs.

> Scheduler.java

This is the real meat & potatoes of the project. This contains
all of the functions that are used to run the actual program,
the ones that you saw in Main.java. They are mostly documented,
so you can check out my solution to scheduling everything and 
applying the relevant rules. 

## InputFiles

> controlVariables.txt

This text file contains all of the
variables I am able to change in order to
test the program, such as how many classes a student
can take, or how many people can teach a class. I did not
label them in the file because of the way my input is handled,
so here is a legend for what each number means:

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

> courseInfo.txt

Input for courses in the style of:
Department, Course Number, Course Name

> facultyInfo.txt

Input for faculty members in the style of:
Firstname, middlename, lastname, email, phone, address, city, state, zip, hire date, whether or not they're tenured

> studentInfo.txt

Input for student in the style of:
first, middle, last, email, phone, address, city, state, zip, birthday, GPA, start date at the college

## Interfaces

These files are my solution to the program's
extendability. If anyone wanted to use my program
10 years from now, but they had a better scheduling
algorithm than the one I wrote, all they have to do is
add a new scheduler object and make sure it implements
the scheduler interface, and all of my code will still work.
Using this interface means that the functions included SchedulerInterface
MUST be included in their version of the scheduler, meaning 
all of the functions in Main.java will still work perfectly fine, 
just using their implementation instead.

## OutputFiles

> faculty.txt

Program output of all faculty members.
Self explanatory, as the data is labelled.

> scheduledCourseSessions.txt

Every course (and its sessions) that passed
the <cull> algorithm, meaning the rules applied
fine (they had enough students).

> scheduledStudents.txt

Similar to faculty.txt, but includes
only students who were able to get into
at least ONE course session. Self explanatory
data included.

> unscheduledCourseSessions.txt

All of the courses that did not
have enough students across all of
their sessions, so they were cancelled.

> unscheduledStudents.txt

Similar to scheduledStudents.txt, but
includes only students who did not get into
a single session.
