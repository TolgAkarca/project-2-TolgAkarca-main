package library.people;
import library.exceptions.ReturnException;
import java.util.Arrays;
/**
 * Used to represent people who have memberships with the Library.
 */
public class Member extends Person{
    private static final long serialVersionUID = 64398177;
    private final int memberID;
    private final int passkey;
    private final int[] checkedOut;

    /**
     * Constructs a Member object.
     *@param firstName The member's first name. 
     *@param lastName The member's last name.
     *@param memberID String used to set the memberID.
     *@param passkey String used to set the passkey.
     */
    public Member(String firstName, String lastName, String memberID, String passkey){
	super(firstName, lastName);
        this.memberID = memberID.hashCode();
	this.passkey = passkey.hashCode();
	this.checkedOut = new int[0];
    }

    /**
     * Constructs a Member object. Used internally for creating altered copies.
     *@param firstName The member's first name. 
     *@param lastName The member's last name.
     *@param memberID The memberID.
     *@param passkey The passkey.
     *@param checkedOut The list of checked out item unique ids.
     */
    private Member(String firstName, String lastName, int memberID, int passkey, int[] checkedOut){
	super(firstName, lastName);
        this.memberID = memberID;
	this.passkey = passkey;
	this.checkedOut = checkedOut.clone();
    }

    /**
     * Get checked out unique ids.
     *@return returns array of unique ids of checked out items.
     */
    public int[] getCheckedOut(){
	return checkedOut.clone();
    }

    /**
     * Checks out item from the library.
     * @return new copy of Member with the newly checked out item added to the checked out list. 
     */
    public Member checkOut(int uID){
	int[] updatedCheckedOut = null;
	boolean foundEmpty = false;
	for(int i = 0; i < checkedOut.length; i++){
	    if(checkedOut[i] == -1){
	        updatedCheckedOut = checkedOut.clone();
		updatedCheckedOut[i] = uID;
		foundEmpty = true;
		break;
	    }
	}
	if(!foundEmpty){
	    updatedCheckedOut = Arrays.copyOf(checkedOut, checkedOut.length+1);
	    updatedCheckedOut[updatedCheckedOut.length-1] = uID;
	}
        return new Member(this.firstName, this.lastName, this.memberID, this.passkey, updatedCheckedOut.clone());
    }

    /**
     * Returns item to the library.
     * @return new copy of Member with the item returned and removed from the checked out list.
     * @throws ReturnException If the item cannot be returned (i.e. was never owned by the Member to begin with).
     */
    public Member returnItem(int uID) throws ReturnException{
	int[] updatedCheckedOut = null;
	boolean foundEmpty = false;
	int i = 0;
	for(i = 0; i < checkedOut.length; i++){
	    if(checkedOut[i] == uID){
	        break;
	    }
	}
	if(i == checkedOut.length)
	    throw new ReturnException(memberID + " could not return " + uID);
	updatedCheckedOut = checkedOut.clone();
	updatedCheckedOut[i] = -1;
        return new Member(this.firstName, this.lastName, this.memberID, this.passkey,  updatedCheckedOut.clone());
    }

    /**
     * Gets member id.
     *@return the member ID is returned.
     */
    public int getMemberID(){
	return memberID;
    }

    /**
     * Checks equality between the Staff member and another object. Two staff members are considered equal if their staff IDs and their passkeys are identical.
     * @return if the two Staff members are equal return true.
     */
    @Override
    public boolean equals(Object o){
	if(!(o instanceof Member))
	    return false;
	return ((Member)o).memberID == memberID && ((Member)o).passkey == passkey;
    }

    @Override
    public String toString(){
	return super.getName();
    }
}
