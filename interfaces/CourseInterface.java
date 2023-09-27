package scheduler.interfaces;

import scheduler.classes.Faculty;
import scheduler.classes.Session;
import scheduler.classes.SessionIdGenerator;

public interface CourseInterface {

    // Manual session manipulation
    void addSession(SessionIdGenerator sessionId);
    Session removeSession(SessionIdGenerator sessionId);

    // Manual faculty manipulation
    void addFaculty(Faculty newFaculty);
    Faculty removeFaculty();
}
