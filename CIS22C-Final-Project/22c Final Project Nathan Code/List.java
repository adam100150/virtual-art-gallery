/**
 * List.java
 * @author Nathan Brin 
 * @author Adam Ashkenazi
 * CIS 22C, Lab 6
 */


import java.util.NoSuchElementException;

public class List<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;


	/****CONSTRUCTORS****/

	/**
	 * Instantiates a new List with default values
	 * @postcondition a new List object
	 */
	public List() { //default constructor
		length = 0;
		first = null;
		last = null;
		iterator = null;
	}

	/**
	 * Instantiates a new List with the same values as the input list
	 * @param L a doubly linked list
	 * @postcondition a new List object with the save values as the given object
	 */
    public List(List<T> original) {
        if (original == null) {
            return;
        }
        if (original.length == 0) {
            length = 0;
            first = null;
            last = null;
            iterator = null;
        } else {
            Node temp = original.first;
            while (temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }
    }

	/****ACCESSORS****/
	/**
	 * Returns the value stored in the first node
	 * @precondition length != 0
	 * @return the integer value stored at node first
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T getFirst() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("getFirst: "
					+ "List is Empty. No data to access!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * @precondition length != 0
	 * @return the integer value stored at node last
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T getLast() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("getLast: "
					+ "List is Empty. No data to access!");
		}
		return last.data;
	}

	/**
     * Returns whether the list is currently empty
     * @return True if the list is empty, false otherwise
     */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the data of the element pointed to by the iterator
	 * @precondition iterator does not point to null
	 * @return the data of the iterator
	 */
	public T getIterator() {
		if(iterator == null) {
			throw new NoSuchElementException("getIterator: " +
					"Iterator element points to null so you cannot retrieve any data");
		}

		return iterator.data;
	}

	/**
	 * Returns whether the iterator is off the end of the list, i.e. is NULL
	 * @return whether iterator is off end
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/****MUTATORS****/

	/**
	 * Creates a new first element
	 * @param data the data to insert at the
	 * front of the list
	 * @postcondition a new first element
	 */
	public void addFirst(T data) {
		Node N = new Node(data);

		if (first == null) { // edge case: first is pointing to null
			first = last = N;
		}
		else {
			N.next = first;
			first.prev = N;
			first = N;
		}

		length++;
	}

	/**
	 * Creates a new last element
	 * @param data the data to insert at the end of the list
	 * @postcondition a new last element
	 */
	public void addLast(T data) {
		Node N = new Node(data);

		if (last == null) { // edge case: last is pointing to null
			first = last = N;
		} else {
			last.next = N;
			N.prev = last;
			last = N;
		}

		length++;
	}

	/**
	 * Removes the first elements in the list
	 * @precondition list is not empty
	 * @postcondition first element is removed
	 * @throws a NoSuchElementException if list is empty
	 */
	public void removeFirst() throws NoSuchElementException{
		if(isEmpty()) { // LENGTH == 0
			throw new NoSuchElementException("removeFirst: " +
					"First element is null so you cannot remove it");
		}

		if (length == 1) { // Edge case
			first = last = iterator = null;
		}
		else {	// length > 1
			// Edge case
			if (iterator == first) { //edge case involving iterator pointing to first node
				iterator = null;
			}

			// General case
			first = first.next;
			first.prev = null;
		}

		length--;
	}

	/**
	 * Removes the last element in the list
	 * @precondition list is not empty
	 * @postcondition last element is removed
	 * @throws a NoSuchElementException if list is empty
	 */
	public void removeLast() throws NoSuchElementException{
		if(first == null) {
			throw new NoSuchElementException("removeLast: " +
					"Last element is null so you cannot remove it");
		}

		if (length == 1) { // edge case
			first = last = iterator = null;
		}
		else {
			if (iterator == last) { //edge case involving iterator pointing to last node
				iterator = null;
			}

			// General case
			last = last.prev;
			last.next = null;

		}

		length--;
	}

	/**
	 * Moves iterator to the start of the list
	 * @precondition length > 0
	 * @postcondition iterator points to the first element
	 */
	public void placeIterator() {
		if(first == null) {
			throw new NoSuchElementException("placeIterator: " +
					"List is empty");
		}

		iterator = first;
	}

	/**
	 * Moves the iterator up by one node
	 * @precondition iterator != null
	 * @postcondition iterator points to the next element in the list
	 */
	public void advanceIterator() {
		if(iterator == null) {
			throw new NullPointerException("advanceIterator: " +
					"Iterator is off end");
		}

		iterator = iterator.next;
	}

	/**
	 * Moves the iterator down by one node
	 * @precondition iterator != null
	 * @postcondition iterator points to the previous element in the list
	 */
	public void reverseIterator() {
		if(iterator == null) {
			throw new NullPointerException("reverseIterator: " +
					"Iterator is off end");
		}

		iterator = iterator.prev;
	}

	/**
	 * Removes the element that the iterator is pointing to in the list
	 * @precondition iterator != null
	 * @postcondition iterator points to null
	 * @throws a NoSuchElementException if iterator points to null
	 */
	public void removeIterator() throws NoSuchElementException {
		if(iterator == null) {
			throw new NoSuchElementException("removeIterator: " +
					"Iterator element is null so you cannot remove it");
		}
		else {
			if (iterator == first) { //edge case involving iterator pointing to first node
				removeFirst();
			}
			else if (iterator == last){ //edge case involving iterator pointing to last node
				removeLast();
			}
			else { //general case
				iterator.next.prev = iterator.prev;
				iterator.prev.next = iterator.next;
				iterator = null;
				length--;
			}
		}
	}

	/**
	 * Inserts an element after the node currently pointed to by the iterator
	 * @precondition iterator != null
	 * @postcondition a new node is added after the iterator
	 * @throws NullPointerException if pointer is null
	 */
	public void addIterator(T data) throws NullPointerException {
		if(iterator == null) {
			throw new NullPointerException("addIterator: " +
					"Iterator element is null so you cannot add an element after it");
		}

		if (iterator == last) { //edge case
			addLast(data);
		} else { //general case
			Node N = new Node(data);
			N.next = iterator.next;
			iterator.next.prev = N;
			iterator.next = N;
			N.prev = iterator;

			length++;
		}

	}

	/****ADDITIONAL OPERATIONS****/

	/**
	 * Compares this list to another list to see if they contain the same data in the same order
	 * @return true if the lists are equal and false otherwise
	 */
	@SuppressWarnings("unchecked")
	@Override public boolean equals(Object obj) {

		//check if the two variables reference the same place in memory
		if (this == obj) {
			return true;
		}

		// Check if obj is instance of List class
		if (!(obj instanceof List)) {
			return false;
		}

		List<T> L = (List<T>) obj;

		// Check lengths
		if (this.length != L.length) {
			return false;
		}

		Node temp1 = this.first;
		Node temp2 = L.first;


		for (int i = 0; i < length; i++) {
			if (temp1.data != temp2.data) {
				return false;
			}
			temp1 = temp1.next;
			temp2 = temp2.next;
		}

		return true;
	}

	/**
	 * List with each value on its own line
	 * At the end of the List a new line
	 * @return the List as a String for display
	 */
	@Override public String toString() {
		String result = "";
		Node temp = first;

		while(temp != null) {
			result += temp.data + "\n";
			temp = temp.next;
		}

		return result;
	}

	/**
	 * Prints the contents of the linked list to the screen in the format #: <element> followed by a newline
	 */
	 public void printNumberedList() {
		 Node temp = first;

		for (int i = 1; i <= length; i++) {
			System.out.println(i + ". " + temp.data);
			temp = temp.next;
		}
	}

    /**
    * Points the iterator at first
    * and then advances it to the
    * specified index
    * @param index the index where
    * the iterator should be placed
    * @precondition 0 < index <= length
    * @throws IndexOutOfBoundsException
    * when precondition is violated
    */
    public void iteratorToIndex(int index) throws IndexOutOfBoundsException{
        if(index <= 0 || index > length)
        {
          throw new IndexOutOfBoundsException("iteratorToIndex(): iterator out of bounds.");
        }
        placeIterator();
        for(int i = 1; i < index; i++)
        {
          advanceIterator();
        }
    }

    /**
    * Searches the List for the specified
    * value using the linear search algorithm
    * @param value the value to search for
    * @return the location of value in the
    * List or -1 to indicate not found
    * Note that if the List is empty we will
    * consider the element to be not found
    * @postcondition: position of the iterator remains
    * unchanged
    */
    public int linearSearch(T value) {
      Node temp = first;
       if (isEmpty()){
         return -1;
       } else {    	   
         for (int i = 1; i <= length; i++){
           if(temp.data.equals(value)){
             return i;
           }
           temp = temp.next;
         }
         return -1;
       }    
    }

}