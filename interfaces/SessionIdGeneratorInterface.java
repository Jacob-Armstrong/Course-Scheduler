package scheduler.interfaces;

import scheduler.classes.SessionIdGenerator;

public interface SessionIdGeneratorInterface {

    // A separate, all purpose ID Generator
    public String generateSessionID();

}