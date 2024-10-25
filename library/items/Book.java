package library.items;
import library.items.Item;
import library.exceptions.*;
import java.util.ArrayList;
/**
 * Represents a Book Item in the Library.
 */
public class Book implements Item{
    private final int uID;
    private final int copies;
    private final int copiesAvailable;
    private final String title;
    private final String description;
    private final ArrayList<String> subjects;
    private final Genre genre; 
    private final int authorID;
    private final int pageCount;

    /**
     * A Builder object for a Book Item.
     */
    public static class BookBuilder{
	private final int uID;
	private int copies = 1;
	private int copiesAvailable;
	private String title;
	private String description;
	private ArrayList<String> subjects = new ArrayList<String>();
	private Genre genre; 
	private int authorID;
        private int pageCount; 

	/**
	 * BookBuilder constructor; used to create a Book Item. The title is needed to set the unique ID (the hash code of the title plus "BOOK"). 
	 * @param title Title for the Book.
	 */
	public BookBuilder(String title){
	    this.title = title;
	    this.uID = (title+"BOOK").hashCode();
	}

	/**
	 * Sets the number of copies (and thus the number of available copies).
	 * @param copies The number of copies for the Book.
	 * @return Returns the same BookBuilder instance.
	 */
	public BookBuilder setCopies(int copies){
	    this.copies = copies;
	    this.copiesAvailable = copies;
	    return this;
	}

	/**
	 * Sets the description.
	 * @param description The description for the Book.
	 * @return Returns the same BookBuilder instance.
	 */
	public BookBuilder setDescription(String description){
	    this.description = description;
	    return this;
	}

	/**
	 * Sets the subjects.
	 * @param subjects An array containing each subject.
	 * @return Returns the same BookBuilder instance.
	 */	
	public BookBuilder setSubjects(String... subjects){
	    this.subjects = new ArrayList<>();
	    for(String s: subjects){
		this.subjects.add(s);
	    }
	    return this;
	}

	/**
	 * Sets the genre.
	 * @param genre The genre of the Book.
	 * @return Returns the same BookBuilder instance.
	 */	
	public BookBuilder setGenre(Genre genre){
	    this.genre = genre;
	    return this;
	}

	/**
	 * Sets the author.
	 * @param name The name of the Book's author.
	 * @return Returns the same BookBuilder instance.
	 */	
	public BookBuilder setAuthor(String name){
	    if(name != null)
		this.authorID = name.hashCode();
	    return this;
	}

	/**
	 * Sets the page count.
	 * @param pageCount The number of pages for the Book.
	 * @return Returns the same BookBuilder instance.
	 */	
	public BookBuilder setPages(int pageCount){
	    this.pageCount = pageCount;
	    return this;
	}

	/**
	 * Builds the Book object.
	 * @return Returns the constructed Book.
	 */	
	public Book build(){
	    return new Book(this);
	}
    }

    /**
     * Constructs a Book object.
     * @param b BookBuilder object used to construct the Book.  
     */
    protected Book(BookBuilder b){
	uID = b.uID;
	copies = b.copies;
	copiesAvailable = b.copiesAvailable;
	title = b.title;
	description = b.description;
	subjects = b.subjects;
	genre = b.genre;
	authorID = b.authorID;
	pageCount = b.pageCount;
    }

    /**
     * Constructs a copy of the Book object with a different number of copies available.
     * @param b Book object used to construct a copy of the Book.
     * @param newCopiesAvailable the new number of copies available.
     */
    private Book(Book b, int newCopiesAvailable){
	uID = b.getUID();
        copies = b.getCopies();
	copiesAvailable = newCopiesAvailable;
	title = b.getTitle();
	description = b.getDescription();
	subjects = b.getSubjects();
	genre = b.getGenre();
	authorID = b.getAuthorID();
	pageCount = b.getPageCount();
    }
    
    public boolean isAvailable(){
	if(copiesAvailable > 0)
	    return true;
	return false;
    }

    public Item checkOut() throws CheckOutException{
	if(isAvailable())
	    return new Book(this, copiesAvailable - 1);
        throw new CheckOutException("Could not check out " + title  + "!");
    }

    public Item returnItem() throws ReturnException{
	if(copiesAvailable < copies)
	    return new Book(this, copiesAvailable + 1);
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
     * Gets the author id.
     *@return The author id is returned.
     */
    public int getAuthorID(){
	return authorID;
    }

    public int getArtistID(){
	return getAuthorID();
    }
    
    public int getPageCount(){
	return pageCount;
    }

    @Override
    public String toString(){
	return String.format("%s (Book, 0x%x)", title, uID);
    }
}
