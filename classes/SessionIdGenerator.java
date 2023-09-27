package scheduler.classes;

import scheduler.interfaces.SessionIdGeneratorInterface;

import java.util.UUID;

public class SessionIdGenerator implements SessionIdGeneratorInterface {

    /**
     * Generates a string that has "SES-" and
     * then 7 characters of a random UUID
     * @return The substring
     */
    @Override
    public String generateSessionID() {
        String st1 = "SES-" + UUID.randomUUID().toString();

        return st1.substring(0, 11);
    }

}
