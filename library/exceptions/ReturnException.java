package library.exceptions;
/**
 * An exception which is thrown when an Item cannot be returned to the Library.
 */
public class ReturnException extends Exception{
    /**
     * Constructor for ReturnException
     * @param msg Message to be sent to the user upon failure. Ideally, it should indicate why exactly the return could not be accomplished.
     */
    public ReturnException(String msg){
	super(msg);
    }
}
