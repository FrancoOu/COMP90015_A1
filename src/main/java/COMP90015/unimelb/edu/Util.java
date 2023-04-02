package COMP90015.unimelb.edu;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Gaoyuan Ou(1301025)
 */
public final class Util {

    public static ObjectMapper mapper = new ObjectMapper();

    public static final String NOT_FOUND = "Word not found";
    public static final String ADDED = "Word added";
    public static final String UPDATED = "Word updated";
    public static final String REMOVED = "Word removed";
    public static final String EXISTED = "Word existed";

}
