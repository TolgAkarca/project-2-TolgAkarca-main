package library.structures;
import java.util.Random;
import java.util.ArrayList;
public class SkipList<T extends Comparable<? super T>, U>{
    class Node{
	T key;
	U value;
	int level;
        ArrayList<Node> forward;
    }
    //Random object
    //Maximum level
    //Current highest level
    //Root node
    //probability value
}
