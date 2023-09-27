package scheduler.classes;

import scheduler.interfaces.IdGeneratorInterface;

import java.util.UUID;

public class IdGenerator implements IdGeneratorInterface {

    /**
     * Generates a string that contains
     * the first 8 characters of a UUID
     * @return The string
     */
    @Override
    public String generateID() {

        String st1 = UUID.randomUUID().toString();

        return st1.substring(0, 8);
    }
}
