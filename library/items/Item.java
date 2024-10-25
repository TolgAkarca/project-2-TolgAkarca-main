package library.items;
import java.util.ArrayList;
import library.exceptions.*;
import library.people.Artist;
/**
 * Interface for Items in the Library catalogue.
 */
public interface Item{
    /**
     * Enum for Genre items.
     */
    public static enum Genre{
	SCI_FI, FANTASY, DRAMA, HORROR, CRIME, COMEDY, NONFICTION;
    }
        /**
     *Returns if there are copies available
     *@return if copies are vailable, return true; otherwise, return false;
     */
    public boolean isAvailable();
    /**
     *Checks out the Item from the Library.
     *@return if available, returns a new Item with the number of copies available decreased by one
     *@throws CheckOutException if the item cannot be checked out.
     */
    public Item checkOut() throws CheckOutException;
    /**
     *Returns the Item to the Library.
     *@return if possible, returns a new Item with the number of copies available increased by one
     *@throws ReturnException if the item cannot be returned.
     */
    public Item returnItem() throws ReturnException;
    /**
     *Gets subjects from Item.
     *@return A copy of the subject list is returned.
     */
    public ArrayList<String> getSubjects();
    /**
     *Gets the unique id.
     *@return The unique id is returned.
     */
    public int getUID();
    /**
     *Gets the description.
     *@return The description is returned.
     */
    public String getDescription();
    /**
     *Gets the genre
     *@return The genre is returned. If the genre was not set, returns null.
     */
    public Genre getGenre();
    /**
     *Gets the number of copies.
     *@return The number of copies. If the number of copies was not set, returns 0.
     */
    public int getCopies();
    /**
     *Gets the number of copies.
     *@return The number of copies available is returned.
     */    
    public int getAvailableCopies();
    /**
     *Gets the title.
     *@return The title is returned.
     */
    public String getTitle();
    /**
     *Gets the artist id
     *@return The artist id is returned. If the artist was not set, return 0.
     */
    public int getArtistID();
}
