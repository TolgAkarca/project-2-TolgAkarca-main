import library.SL_Library;
import library.items.*;
import library.people.*;
import library.exceptions.*;
import library.structures.*;
public class SL_Driver{
    public static void main(String[] args){        
	try{
	    Staff admin = new Staff("Kurt","Godel","kGod","incompleteness");
	    SL_Library lib = new SL_Library(admin);
	    String s = "a";
	    for(int i = 0; i < 4096; i++){//creating dummy Items to fill the list with
		lib.addItem(admin, new Book.BookBuilder(s).build());
		s+="a";
	    }
	    var sl = lib.getCatalogue(admin);
	    for(int i = 0; i < 1_000_000; i++)
	        sl.get(0x7FFFFFFF); //Trying to grab a non-existent element
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

}
