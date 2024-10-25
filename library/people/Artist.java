package library.people;
/**
 * Used to represent Artists who have works in the Library catalogue.
 */
public class Artist extends Person{
    private static final long serialVersionUID = 64398177;
    private final int artistID;
    
    /**
     * Constructs an Artist object. Sets the artist id to the hash code of the first name plus the last name with a space inbetween.
     *@param firstName The artist's first name. 
     *@param lastName The artist's last name.
     */
    public Artist(String firstName, String lastName){
	super(firstName, lastName);
        this.artistID = (firstName+" "+lastName).hashCode();
    }

    /**
     * Gets the artist id.
     *@return Returns the artist id.
     */
    public int getArtistID(){
	return artistID;
    }

    @Override
    public String toString(){
	return super.getName();
    }
}
