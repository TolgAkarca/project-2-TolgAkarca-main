package library;
import library.items.*;
import library.people.*;
import library.exceptions.*;
import library.structures.*;
import java.util.ArrayList;
/**
 *Class which represents a Library. The library contains Staff, Members, Artists, and Items. (This library uses a skip list to store data.)
 *
 */
public class SL_Library{
    private final SkipList<Integer, Item> catalogue;
    private final SkipList<Integer, Staff> staffList;
    private final SkipList<Integer, Member> memberList;
    private final SkipList<Integer, Artist> artistList;

    /**
     *Constructor for creating a SL_Library. A staff member must be supplied to guarantee the library can be altered.
     *@param admin used to initialize the SL_Library
     */
    public SL_Library(Staff admin){
	//-(Math.log(4096) / Math.log(.25)) => 6
	catalogue = new SkipList<>(6); 
	staffList = new SkipList<>(6);
	staffList.insert(admin.getStaffID(), admin);
	memberList = new SkipList<>(6);
        artistList = new SkipList<>(6);
    }

    /**
     *Method which validates given staff member
     *@param staff used to check if provided Staff object exists in the SL_Library.
     *@return if staff is validated, true is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     */
    public boolean validateStaff(Staff staff) throws InvalidPermissionsError{
	Staff s = staffList.get(staff.getStaffID());
	if(s != null){
	    return s.equals(staff);
	}
	throw new InvalidPermissionsError(staff.getName() + " does not have valid permissions!");
    }

    /**
     *Method which validates given member
     *@param member used to check if provided Member object exists in the SL_Library.
     *@return if member is validated, true is returned.
     *@throws InvalidPermissionsError if member has invalid permissions
     */
    public boolean validateMember(Member member) throws InvalidPermissionsError{
	Member m = memberList.get(member.getMemberID());
	if(m != null){
	    return m.equals(member);
	}
	throw new InvalidPermissionsError(member.getName() + " does not have valid permissions!");
 
    }

    
    /**
     *Method which adds an Item to the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param item Item to be added.
     *@return if the item is added, true is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     *@throws DuplicateError if item with the same uID exists in the SL_Library
     */
    public boolean addItem(Staff staff, Item item) throws InvalidPermissionsError, DuplicateError{
        validateStaff(staff);
	   
	int uID = item.getUID();
        if(catalogue.get(uID) != null){//Cannot add duplicates!
	    throw new DuplicateError(uID + " is a duplicate!");
	}
	catalogue.insert(uID, item);
	return true; 
    }
    
    /**
     *Method which adds an Artists to the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param artist Artist to be added.
     *@return if the artist is added, true is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     *@throws DuplicateError if artist with the same artistID exists in the SL_Library
     */
    public boolean addArtist(Staff staff, Artist artist)throws InvalidPermissionsError, DuplicateError{
        validateStaff(staff);
        int artistID = artist.getArtistID();
        if(artistList.get(artistID) != null){//Cannot add duplicates!
	    throw new DuplicateError(artistID + " is a duplicate!");
	}
	artistList.insert(artistID, artist);
	return true;
    }
    
    /**
     *Method which adds a Staff to the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param oStaff Staff to be added.
     *@return if the staff is added, true is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     *@throws DuplicateError if staff with the same staffID exists in the SL_Library
     */
    public boolean addStaff(Staff staff, Staff oStaff) throws InvalidPermissionsError, DuplicateError{
        validateStaff(staff);
	int staffID = oStaff.getStaffID();
	if(staffList.get(staffID) != null){//Cannot add duplicates!
	    throw new DuplicateError(staffID + " is a duplicate!");
	}
	staffList.insert(staffID, oStaff);
	return true;
    }

    /**
     *Method which adds a Member to the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param member Member to be added.
     *@return if the artist is added, true is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     *@throws DuplicateError if member with the same memberID exists in the SL_Library
     */
    public boolean addMember(Staff staff, Member member)throws InvalidPermissionsError, DuplicateError{
        validateStaff(staff);
	int memberID = member.getMemberID();
	if(memberList.get(memberID) != null){//Cannot add duplicates!
	    throw new DuplicateError(memberID + " is a duplicate!");
	}
	memberList.insert(memberID, member);
	return true;
    }


    /**
     *Checks out the Item from the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param member Member who is receiving the copy.
     *@param item Item to be added.
     *@return if the book can be checked out, true is returned; if the book cannot be checked out, false is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     */
    public boolean checkOutItem(Staff staff, Member member, Item item)throws InvalidPermissionsError{
        validateStaff(staff);
	validateMember(member);
	Item foundItem = catalogue.get(item.getUID());
	Member foundMember = memberList.get(member.getMemberID());
	if(foundItem == null || foundMember == null)
	    return false;
	try{
	    Item newItem =  foundItem.checkOut();
	    Member newMember = foundMember.checkOut(foundItem.getUID()); 
	    memberList.insert(member.getMemberID(), newMember);
	    catalogue.insert(item.getUID(), newItem);
	}catch(CheckOutException e){
	    System.out.println(e);
	    return false;
	}
	return true;
    }

    /**
     *Returns checked out Item from the SL_Library
     *@param staff Used to check correct permissions are sent to alter SL_Library.
     *@param member Member who is returning the copy.
     *@param item Item to be returned.
     *@return if the book was successfully returned, true is returned; if the book cannot be returned, false is returned.
     *@throws InvalidPermissionsError if staff has invalid permissions
     */    
    public boolean returnItem(Staff staff, Member member, Item item) throws InvalidPermissionsError{
        validateStaff(staff);
	validateMember(member);
	Item foundItem = catalogue.get(item.getUID());
	Member foundMember = memberList.get(member.getMemberID());
	if(foundItem == null || foundMember == null)
	    return false;
	try{
	    Item newItem = foundItem.returnItem();
	    Member newMember = foundMember.returnItem(foundItem.getUID());
	    catalogue.insert(item.getUID(), newItem);
	    memberList.insert(member.getMemberID(), newMember);
	}catch(ReturnException e){
	    System.out.println(e);
	    return false;
	}
	return true;
    }

    /**
     *Returns the current SL_Library catalogue.
     *@param person Used to check correct permissions are sent to receive catalogue information.
     *@return a copy of the internal catalogue.
     *@throws InvalidPermissionsError if person has invalid permissions
     */    
    public SkipList<Integer, Item> getCatalogue(Person person) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");
	}
	return new SkipList<Integer, Item>(catalogue);
    }

    /**
     *Returns all artists in the current SL_Library catalogue.
     *@param person Used to check correct permissions are sent to receive artist information.
     *@return a copy of the internal artist list.
     *@throws InvalidPermissionsError if person has invalid permissions
     */    
    public SkipList<Integer, Artist> getArtists(Person person) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");	    
	}
	return new SkipList<Integer, Artist>(artistList);
    }

    /**
     *Returns all staff in the SL_Library.
     *@param staff Used to check correct permissions are sent to receive staff information.
     *@return a copy of the internal staff list.
     *@throws InvalidPermissionsError if person has invalid permissions
     */    
    public SkipList<Integer, Staff> getStaff(Staff staff) throws InvalidPermissionsError{
	validateStaff(staff);
	return new SkipList<Integer, Staff>(staffList);
    }

    /**
     *Returns all members in the SL_Library.
     *@param staff Used to check correct permissions are sent to receive member information.
     *@return a copy of the internal member list.
     *@throws InvalidPermissionsError if person has invalid permissions
     */    
    public SkipList<Integer, Member> getMembers(Staff staff) throws InvalidPermissionsError{
        validateStaff(staff);
	return new SkipList<Integer, Member>(memberList);
    }

    /**
     *Returns all items associated with the given Artist in the SL_Library.
     *@param person Used to check correct permissions are sent to receive staff information.
     *@param artist Artist to match with items.
     *@return String listing all items associated with given artist.
     *@throws InvalidPermissionsError if person has invalid permissions
     */    
    public String allArtistItems(Person person, Artist artist) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");	    
	}
        StringBuilder sb = new StringBuilder("Results for " + artist + ": ");
        boolean found = false;
	ArrayList<Integer> keys = catalogue.getKeys();
	for(Integer key : keys){
	    Item i = catalogue.get(key);
	    if(i.getArtistID() == artist.getArtistID()){
		sb.append(i.toString() + ", ");
		found = true;
	    }
	}
	if(!found)
	    return sb.append("~There's nothing here~").toString();
	return sb.substring(0, sb.length()-2);
    }

    /**
     *Returns all items associated with the given Genre in the SL_Library.
     *@param person Used to check correct permissions are sent to receive staff information.
     *@param genre Genre to match with items.
     *@return String listing all items associated with given artist.
     *@throws InvalidPermissionsError if person has invalid permissions
     */
    public String allGenreItems(Person person, Item.Genre genre) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");	    
	}
	StringBuilder sb = new StringBuilder("Results for " + genre + ": ");
        boolean found = false;
	ArrayList<Integer> keys = catalogue.getKeys();
	for(Integer key : keys){
	    Item i = catalogue.get(key);
	    if(i.getGenre() == genre){
		sb.append(i.toString() + ", ");
		found = true;
	    }
	}
	if(!found)
	    return sb.append("~There's nothing here~").toString();
	return sb.substring(0, sb.length()-2);
    }

    /**
     *Returns all items checked out by Member in the SL_Library.
     *@param staff Used to check correct permissions are sent to receive Member information.
     *@param member Member to find checked out items.
     *@return String listing all items checked out by member.
     *@throws InvalidPermissionsError if person has invalid permissions
     */        
    public String memberCheckedOutItems(Staff staff, Member member) throws InvalidPermissionsError{
	validateStaff(staff);
	StringBuilder sb = new StringBuilder("Results for " + member + " items checked out: ");
	boolean found = false;
	int[] checkedOutUIDs = memberList.get(member.getMemberID()).getCheckedOut();
	for(int uid : checkedOutUIDs){
	    if(uid != -1){
		sb.append(catalogue.get(uid) + ", ");
		found = true;
	    }
	}
	if(!found)
	    return sb.append("~There's nothing here~").toString();
	return sb.substring(0, sb.length()-2);
    }

    /**
     *Returns all available items in the SL_Library.
     *@param person Used to check correct permissions are sent to receive available item information.
     *@return String listing all available items.
     *@throws InvalidPermissionsError if person has invalid permissions
     */        
    public String allAvailableItems(Person person) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");	    
	}
	StringBuilder sb = new StringBuilder("Available Titles: ");
        boolean found = false;
	ArrayList<Integer> keys = catalogue.getKeys();
	for(Integer key : keys){
	    Item i = catalogue.get(key);
	    if(i.isAvailable()){
		sb.append(i.toString() + ", ");
		found = true;
	    }
	}
	if(!found)
	    return sb.append("~There's nothing here~").toString();
	return sb.substring(0, sb.length()-2);
    }

    /**
     *Returns all information about the given item.
     *@param person Used to check correct permissions are sent to receive available item information.
     *@param uID unique ID associated with desired item.
     *@return String listing all information present for the given item.
     *@throws InvalidPermissionsError if person has invalid permissions
     */        
    public String getDetailedItemInfo(Person person, int uID) throws InvalidPermissionsError{
	switch (person){
	    case Member m -> validateMember(m);
	    case Staff s -> validateStaff(s);
	    default -> throw new InvalidPermissionsError("null value");	    
	}
	StringBuilder sb = new StringBuilder(String.format("Details for 0x%x: ", uID));
        boolean found = false;
        Item i = catalogue.get(uID);
	if(i == null)
	    return sb.append("~There's nothing here~").toString();
	if(i instanceof Book){
	    Book b = (Book) i;
	    sb.append(String.format("%n\tTitle: %s%n", b.getTitle()));
	    if(artistList.get(b.getAuthorID()) != null) sb.append(String.format("\tAuthor: %s%n", artistList.get(b.getAuthorID()).toString()));
	    if(b.getDescription() != null) sb.append(String.format("\tDescription: %s%n", b.getDescription()));
	    if(b.getGenre() != null) sb.append(String.format("\tGenre: %s%n", b.getGenre().toString()));
	    sb.append(String.format("\tPages:%d%n", b.getPageCount()));
	    sb.append(String.format("\tCopies: (%d/%d)%n", b.getAvailableCopies(), b.getCopies()));
	    if(b.getSubjects() != null) sb.append(String.format("\tSubjects: %s%n", b.getSubjects().toString()));
	    sb.append(String.format("\tUID: 0x%x%n", b.getUID()));
	    
	}else{
	    Movie m = (Movie) i;
	    sb.append(String.format("%n\tTitle: %s%n", m.getTitle()));
	    if(artistList.get(m.getDirectorID()) != null) sb.append(String.format("\tAuthor: %s%n", artistList.get(m.getDirectorID()).toString()));
	    if(m.getDescription() != null) sb.append(String.format("\tDescription: %s%n", m.getDescription()));
	    if(m.getGenre() != null) sb.append(String.format("\tGenre: %s%n", m.getGenre().toString()));
	    int duration = m.getDuration();
	    sb.append("\tRuntime: ");
	    if(duration / 3600 > 0){
		sb.append(String.format("%dhr ",duration / 3600));
		duration %= 3600;
	    }
	    if(duration / 60 > 0){
		sb.append(String.format("%dm ",duration / 60));
		duration %= 60;
	    }
	    sb.append(String.format("%ds%n",duration));
	    sb.append(String.format("\tCopies: (%d/%d)%n", m.getAvailableCopies(), m.getCopies()));
	    if(m.getSubjects() != null) sb.append(String.format("\tSubjects: %s%n", m.getSubjects().toString()));
	    sb.append(String.format("\tUID: 0x%x%n", m.getUID()));

	}
	    return sb.toString();
    }
}
