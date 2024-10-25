package library.structures;
import java.util.Comparator;
import java.util.ArrayList;
/**
 * An ordered linked-list data structure.
 * @param <T> the key data type. Must be Comparable for ordering.
 * @param <U> the value data type.
 */
public class OrderedList<T extends Comparable<? super T>, U>{
    protected class Node{
	private T key;
	private U data;
	private Node next;
	public Node(T key, U data){
	    this.key = key;
	    this.data = data;
	    next = null;
	}
    }

    private Node root;
    private int size = 0;

    /**
     * Inserts the item in the list. If the key already exists then the value is updated.
     * @param searchKey Key associated with new value.
     * @param data New value to be inserted into the list.
     */
    public void insert(T searchKey, U data){
	if(root == null){
	    root = new Node(searchKey, data);
	    size++;
	}else if(root.key.compareTo(searchKey) > 0){
	    Node n = new Node(searchKey, data);
	    n.next = root;
	    root = n;
	    size++;
	}else{
	    Node curr = root;
	    while(curr.next != null && curr.next.key.compareTo(searchKey) <= 0){ //While the next key is less than the searchKey
		curr = curr.next; //Traverse the list
	    }
	    //Two cases: the searchKey already exists --> update; the searchKey does not exist --> insert
	    if(curr.key.compareTo(searchKey) == 0){
		curr.data = data;
	    }else{
		Node n = new Node(searchKey, data);
		n.next = curr.next;
		curr.next = n;
		size++;
	    }
	}
    }

    /**
     * Deletes the item associated with the key from the list.
     * @param searchKey Key of the item to be deleted.
     * @return If the item is removed from the list return the item; if not, returns null.
     */
    public U delete(T searchKey){
	if(root == null){
	    return null;
	}else if(root.key.compareTo(searchKey) == 0){
	    Node curr = root;
	    root = curr.next;
	    size--;
	    return curr.data;
	}else{
	    Node prev = root;
	    Node curr = root;
	    while(curr != null && curr.key.compareTo(searchKey) < 0){ //While the current key is less than or equal to the searchKey
		prev = curr;
		curr = curr.next; //Traverse the list
	    }
	    //Two cases: the searchKey already exists --> update; the searchKey does not exist --> insert
	    if(curr != null && curr.key.compareTo(searchKey) == 0){
		prev.next = curr.next;
		size--;
		return curr.data;
	    }
	}
	return null;
    }

    /**
     * Gets the item associated with the given key.
     * @param searchKey Key for desired value. 
     * @return value at location searchKey. If it does not exist, returns null.
     */    
    public U get(T searchKey){
	if(root == null){
	    return null;
	}else{
	    Node curr = root;
	    while(curr != null && curr.key.compareTo(searchKey) < 0){ //While the current key is less than or equal to the searchKey
		curr = curr.next; //Traverse the list
	    }
	    //Two cases: the searchKey already exists --> update; the searchKey does not exist --> insert
	    if(curr != null && curr.key.compareTo(searchKey) == 0){
		return curr.data;
	    }	    
	}
	return null;
    }

    /**
     * Gets the keys for the list.
     * @return ArrayList{@literal <T>} of keys.
     */
    public ArrayList<T> getKeys(){
	if(root == null)
	    return new ArrayList<>();
	ArrayList<T> retArray = new ArrayList<>();
	Node curr = root;
	int i = 0;
	while(curr != null){
	    retArray.add(curr.key);
	    curr = curr.next;
	}
	return retArray;
    }

    /**
     * Gets the number of elements in the list.
     * @return gives the current size of the list.
     */
    public int size(){
	return size;
    }

    /**
     * Generates an empty OrderedList.
     */    
    public OrderedList(){
	this.root = null;
    }

    /**
     * Generates a shallow copy of an OrderedList.
     * @param l The ordered list to be copied.
     */    
    public OrderedList(OrderedList<T, U> l){
	Node curr = l.root;
        while(curr != null){
	    insert(curr.key, curr.data);
	    curr = curr.next;
	}
    }

    @Override
    public String toString(){
	if(root == null) return "[]";
	StringBuilder sb = new StringBuilder();
	Node curr = root;
	sb.append("[ ");
	while(curr.next != null){
	    sb.append(curr.data.toString()).append(", ");
	    curr = curr.next;
	}
	sb.append(curr.data.toString());
	sb.append("]");
	return sb.toString();
    }

}
