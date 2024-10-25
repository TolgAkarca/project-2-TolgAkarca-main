package library.people;
import java.io.*;
/**
 * Person object used to represent Staff, Members, and Artists.
 */
public abstract class Person implements Serializable{
    protected final String firstName;
    protected final String lastName;
    /**
     * Gets the person's name. 
     *@return returns the first and last name in the following format: "LASTNAME, FIRSTNAME" 
     */
    public String getName(){
	return lastName + ", " + firstName;
    }

    public Person(String firstName, String lastName){
	this.firstName = firstName;
	this.lastName = lastName;
    }
}
