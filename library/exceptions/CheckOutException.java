package library.exceptions;
/**
 * An exception which is thrown when an Item cannot be checked out from the Library.
 */
public class CheckOutException extends Exception{
    /**
     * Constructor for CheckOutException
     * @param msg Message to be sent to the user upon failure. Ideally, it should indicate why exactly the check out could not be accomplished.
     */
    public CheckOutException(String msg){
	super(msg);
    }
}
