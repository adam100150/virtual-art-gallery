/**
 * HashTable.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

import java.util.ArrayList;

public class HashTable<T> {
    
    private int numElements;
    private ArrayList<List<T> > Table;
    

    /**
     * Constructor for the hash 
     * table. Initializes the Table to
     * be sized according to value passed
     * in as a parameter
     * Inserts size empty Lists into the
     * table. Sets numElements to 0
     * @param size the table size
     */
    public HashTable(int size) {
    	numElements = 0;
    	Table = new ArrayList<>(size);
    	for(int i=0; i< size; i++) {
    		List<T> newList = new List<>();
    		Table.add(newList);
    	}  
    }
       
    /**Accessors*/
    
    /**
     * returns the hash value in the Table
     * for a given Object 
     * @param t the Object
     * @return the index in the Table
     */
    private int hash(T t) {
        int code = t.hashCode();
        if(Table.size() == 0) {
        	return -1;
        }
        return code % Table.size(); //size of the arrayList
    }
    
    /**
     * counts the number of elements at this index
     * @param index the index in the Table
     * @precondition 0 <=  index < Table.length
     * @return the count of elements at this index
     * @throws IndexOutOfBoundsException
     */
    public int countBucket(int index) throws IndexOutOfBoundsException{
    	if(index < 0 || index >= Table.size()) {
    		throw new IndexOutOfBoundsException("countBucket():"
    				+ "index is outside bounds of the table");
    	}
        return Table.get(index).getLength(); //
    }
    
    /**
     * returns total number of elements in the Table
     * @return total number of elements
     */
    public int getNumElements() {
        return numElements;
    }
    
    /**
     * Accesses a specified element in the Table
     * @param t the element to search for
     * @return the element stored in the Table, 
     * or null if this Table does not contain t. 
     * @precondition t != null
     * @throws NullPointerException when t is null
     */
    public T get(T t) throws NullPointerException{
    	if(t == null) {
    		throw new NullPointerException("The table does not contain t");	
    	}
    	int bucket = hash(t);
    	Table.get(bucket).placeIterator();
    	for(int i = 0; i < Table.get(bucket).getLength();i++) {
    		if(t.equals(Table.get(bucket).getIterator())) {
    			return Table.get(bucket).getIterator();
    		}
    		Table.get(bucket).advanceIterator();
    	}
      	int j;
    	for(int i=0; i<Table.size(); i++) {
    		j=Table.get(i).linearSearch(t);
    		if(j != -1) {
    			Table.get(i).iteratorToIndex(j);
    			if(Table.get(i).getIterator().equals(t)) {
    				return Table.get(i).getIterator();
    			}
    		}
    	}
    	return null;       
    }
    
    /**
     * Determines whether a specified key is in 
     * the Table
     * @param t the element to search for
     * @return  whether the element is in the Table 
     * @precondition t != null
     * @throws NullPointerException when t is null
     */
    public boolean contains(T t) throws NullPointerException{
    	if(t == null) {
    		throw new NullPointerException("Element cannot be null");
    	}
    	int bucket = hash(t);
    	Table.get(bucket).placeIterator();
    	for(int i = 0; i < Table.get(bucket).getLength();i++) {
    		if(Table.get(bucket).getIterator().equals(t)) {
    			return true;
    		}
    		Table.get(bucket).advanceIterator();
    	}
    	//if regular key didn't find it
    	//check all element in all bucket to see if one of them matches t
    	int j;
    	for(int i=0; i<Table.size(); i++) {
    		j=Table.get(i).linearSearch(t);
    		if(j != -1) {
    			Table.get(i).iteratorToIndex(j);
    			if(Table.get(i).getIterator().equals(t)) {
    				return true;
    			}
    		}
    	}
    	
    	return false;    	
    }
    
     
    /**Mutators*/
    
    /**
     * Inserts a new element in the Table
     * at the end of the chain in the bucket
     * @param t the element to insert
     * @precondition t != null
     * @throws NullPointerException when t is null
     */
    public void insert(T t) throws NullPointerException{  
      	if(t == null) {
    		throw new NullPointerException("Element to insert cannot be null");
    	}
      	int bucket = hash(t);
      	Table.get(bucket).addLast(t);
      	numElements++;  
    }  
     
     
    /**
     * removes the key t from the Table
     * calls the hash method on the key to
     * determine correct placement
     * has no effect if t is not in
     * the Table or for a null argument          
     * @param t the key to remove
     * @throws NullPointerException when t is null
     */
    public void remove(T t) throws NullPointerException {
     	if(t == null) {
    		throw new NullPointerException("Remove element cannot be null");
    	}
     	int bucket = hash(t);
     	Table.get(bucket).placeIterator();
     	while(!Table.get(bucket).offEnd()) {
     		if(Table.get(bucket).getIterator().equals(t)) {
     			Table.get(bucket).removeIterator1();
     			numElements--;
     			return;     			
     		}
     		Table.get(bucket).advanceIterator();
     	}
    }
    
    /**
     * Clears this hash table so that it contains no keys.
     */
    public void clear() {
    	Table.clear();
        
    }

    /**Additional Methods*/

    /**
     * Prints all the keys at a specified
     * bucket in the Table. Each key displayed
     * on its own line, with a blank line 
     * separating each key
     * Above the keys, prints the message
     * "Printing bucket #<bucket>:"
     * Note that there is no <> in the output
     * @param bucket the index in the Table
     */
    public void printBucket(int bucket) {
    	Table.get(bucket).placeIterator();
    	while(!Table.get(bucket).offEnd()) {   
    		System.out.println(Table.get(bucket).getIterator().toString());
    		Table.get(bucket).advanceIterator(); 
    	}   	    
    }
        
    /**
     * Prints the first key at each bucket
     * along with a count of the total keys
     * with the message "+ <count> -1 more 
     * at this bucket." Each bucket separated
     * with two blank lines. When the bucket is 
     * empty, prints the message "This bucket
     * is empty." followed by two blank lines
     */
    public void printTable(){
    	for(int i = 0; i < Table.size(); i++) {   		
    		printBucket(i);
    	}        
     }
    
    /**
     * Starting at the first bucket, and continuing
     * in order until the last bucket, concatenates
     * all elements at all buckets into one String
     */
    @Override public String toString() {
    	String string = "";
    	for(int i = 0; i < Table.size(); i++) { 
    		Table.get(i).placeIterator();
        	while(!Table.get(i).offEnd()) { 
        		string += Table.get(i).getIterator().toString();
        		Table.get(i).advanceIterator();
        	}
    	  }  		
    	return string;        
    }   
}