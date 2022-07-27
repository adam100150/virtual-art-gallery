/**
 * HashTable.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package src;
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
    	Table = new ArrayList<List<T>>();
        for (int i = 0; i < size; i++) {
        	Table.add(new List<T>());
        }
        numElements = 0;
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
        return code % Table.size();
    }

	/**
	 * Counts the number of elements at this index
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): index is out of bounds");
		} else {
			return Table.get(index).getLength();
		}
	}

	/**
	 * Returns total number of elements in the Table
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
		if (t == null) {
			throw new NullPointerException("get(): t is null");
		} else {
			int index = hash(t);			
			List<T> temp = Table.get(index);
			if (temp == null){
				return null;
			}

			int listIndex = temp.linearSearch(t);				
			if (listIndex != -1) {
				temp.iteratorToIndex(listIndex);
				return temp.getIterator();
			}
			return null;
		}
	}

    /**
     * Determines whether a specified key is in
     * the Table
     * @param t the element to search for
     * @return  whether the element is in the Table
     * @precondition t != null
     * @throws NullPointerException when t is null
     */
	 /**
     * Determines whether a specified key is in
     * the Table
     * @param t the element to search for
     * @return  whether the element is in the Table
     * @precondition t != null
     * @throws NullPointerException when t is null
     */
    public boolean contains(T t) throws NullPointerException{
        if (t == null) {
        	throw new NullPointerException("contains(): t is null");
        } else {
        	int index = hash(t);
        	List<T> temp = Table.get(index);
			if (temp == null)
			{
				return false;
			}
        	return temp.linearSearch(t) != -1;
        }
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
		if (t == null) {
			throw new NullPointerException("insert(): t is null");
		} else {
			int index = hash(t);			
			Table.get(index).addLast(t);
			numElements++;
		}
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
	public void remove(T t) {
		if (t == null) {
			throw new NullPointerException("remove(): t is null");
		} else {
			int index = hash(t);
			List<T> temp = Table.get(index);
			if (temp == null) {
				return;
			}

			int listIndex = temp.linearSearch(t);
			if (listIndex == -1) {
				return;
			}			

			temp.iteratorToIndex(listIndex);
			temp.removeIterator();
		}
	}

	/**
     * Clears this hash table so that it contains no keys.
     */
    public void clear() {
    	for (int i = 0; i < Table.size(); i++) {
    		Table.set(i, new List<T>());
    	}
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
	 * @throws IndexOutOfBoundsException
	 */
	public void printBucket(int bucket) throws IndexOutOfBoundsException {
		if (bucket < 0 || bucket > Table.size()) {
			throw new IndexOutOfBoundsException("printBucket(): index is out of bounds");
		} else {
			List<T> temp = Table.get(bucket);
			System.out.println("Printing bucket #" + bucket + ":\n");
			System.out.println(temp);			
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
		for (int i = 0; i < Table.size(); i++) {
			List<T> temp = Table.get(i);
			System.out.println("Bucket: " + i);
			if (temp.isEmpty()){
				System.out.println("This bucket is empty.\n");
			} else {
				System.out.println(temp.getFirst());
				System.out.println("+ " + (temp.getLength()-1) + " more at this bucket\n");
				System.out.println();
			}
		}
	}

	/**
	 * Starting at the first bucket, and continuing
	 * in order until the last bucket, concatenates
	 * all elements at all buckets into one String
	 */
	@Override public String toString() {
		String s = "";
		if (numElements == 0) {
			return s;
		}
		
		for (int i = 0; i < Table.size(); i++) {
			s += Table.get(i).toString();			
		}
		return s;
	}
}