package library.items;
import library.items.Item;
import library.exceptions.*;
import java.util.ArrayList;
import java.io.*;
/**
 * Represents a Movie Item in the Library.
 */
public class Movie implements Item{
    private final int uID;
    private final int copies;
    private final int copiesAvailable;
    private final String title;
    private final String description;
    private final ArrayList<String> subjects;
    private final Genre genre; 
    private final int directorID;
    private final int duration; //in seconds

    /**
     * A Builder object for a Movie Item.
     */
    public static class MovieBuilder{
	private final int uID;
	private int copies;
	private int copiesAvailable;
	private String title;
	private String description;
	private ArrayList<String> subjects = new ArrayList<>();
	private Genre genre; 
	private int directorID;
        private int duration; 

	/**
	 * MovieBuilder constructor; used to create a Movie Item. The title is needed to set the unique ID (the hash code of the title plus "MOVIE").
	 * @param title Title for the Movie.
	 */
	public MovieBuilder(String title){
	    this.title = title;
	    this.uID = (title+"MOVIE").hashCode();
	}

	/**
	 * Sets the number of copies (and thus the number of available copies).
	 * @param copies The number of copies for the Movie.
	 * @return Returns the same MovieBuilder instance.
	 */
	public MovieBuilder setCopies(int copies){
	    this.copies = copies;
	    this.copiesAvailable = copies;
	    return this;
	}

	/**
	 * Sets the description.
	 * @param description The description for the Movie.
	 * @return Returns the same MovieBuilder instance.
	 */
	public MovieBuilder setDescription(String description){
	    this.description = description;
	    return this;
	}

	/**
	 * Sets the subjects.
	 * @param subjects An array containing each subject.
	 * @return Returns the same MovieBuilder instance.
	 */	
	public MovieBuilder setSubjects(String... subjects){
	    this.subjects = new ArrayList<>();
	    for(String s: subjects){
		this.subjects.add(s);
	    }
	    return this;
	}

	/**
	 * Sets the genre.
	 * @param genre The genre of the Movie.
	 * @return Returns the same MovieBuilder instance.
	 */	
	public MovieBuilder setGenre(Genre genre){
	    this.genre = genre;
	    return this;
	}

	/**
	 * Sets the director.
	 * @param name The name of the Movie's director.
	 * @return Returns the same MovieBuilder instance.
	 */	
	public MovieBuilder setDirector(String name){
	    if(name != null)
		this.directorID = name.hashCode();
	    return this;
	}

	/**
	 * Sets the duration.
	 * @param seconds The duration of the Movie in seconds.
	 * @return Returns the same MovieBuilder instance.
	 */	
	public MovieBuilder setDuration(int seconds){
	    this.duration = seconds;
	    return this;
	}

	/**
	 * Builds the Movie object.
	 * @return Returns the constructed Movie.
	 */	
	public Movie build(){
	    return new Movie(this);
	}
    }

    
    /**
     * Constructs a Movie object.
     * @param b MovieBuilder object used to construct the Movie.  
     */
    private Movie(MovieBuilder b){
	uID = b.uID;
	copies = b.copies;
	copiesAvailable = b.copiesAvailable;
	title = b.title;
	description = b.description;
	subjects = b.subjects;
	genre = b.genre;
	directorID = b.directorID;
	duration = b.duration;
    }
    
    /**
     * Constructs a copy of the Movie object with a different number of copies available.
     * @param b Movie object used to construct a copy of the Movie.
     * @param newCopiesAvailable the new number of copies available.
     */
    private Movie(Movie b, int newCopiesAvailable){
	uID = b.getUID();
        copies = b.getCopies();
	copiesAvailable = newCopiesAvailable;
	title = b.getTitle();
	description = b.getDescription();
	subjects = b.getSubjects();
	genre = b.getGenre();
	directorID = b.getDirectorID();
        duration = b.getDuration();
    }
    
    public boolean isAvailable(){
	if(copiesAvailable > 0)
	    return true;
	return false;
    }

    public Item checkOut() throws CheckOutException{
	if(isAvailable())
	    return new Movie(this, copiesAvailable - 1);
        throw new CheckOutException("Could not check out " + title  + "!");
    }

    public Item returnItem() throws ReturnException{
	if(isAvailable())
	    return new Movie(this, copiesAvailable + 1);
        throw new ReturnException("Could not return " + title  + "!");
    }

    
    public ArrayList<String> getSubjects(){
	return new ArrayList<String>(subjects);
    }

    public int getUID(){
	return uID;
    }

    public String getDescription(){
	return description;
    }

    public Genre getGenre(){
	return genre;
    }

    public int getCopies(){
	return copies;
    }

    public int getAvailableCopies(){
	return copiesAvailable;
    }

    public String getTitle(){
	return title;
    }

    /**
     * Gets the director id.
     *@return The director id is returned.
     */    
    public int  getDirectorID(){
	return directorID;
    }

    public int getArtistID(){
	return getDirectorID();
    }
    
    public int getDuration(){
	return duration;
    }

    @Override
    public String toString(){
        return String.format("%s (Movie, 0x%x)", title, uID);
    }
}
