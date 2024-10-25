package library.exceptions;
/**
 * An exception which is thrown when a phony staff member is invoked or when a member tries to perform staff-only actions.
 */
public class InvalidPermissionsError extends Exception{
    /**
     * Constructor for InvalidPermissionsError
     * @param msg Message to be sent to the user upon failure.
     */    
    public InvalidPermissionsError(String msg){
	super(msg);
    }
}
