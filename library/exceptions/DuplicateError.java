package library.exceptions;
/**
 * An exception which is thrown when there is a duplicate Item in the Library (marked by identical unique IDs).
 */
public class DuplicateError extends Exception{
    /**
     * Constructor for DuplicateError
     * @param msg Message to be sent to the user upon failure.
     */
    public DuplicateError(String msg){
        super(msg);
    }
}
