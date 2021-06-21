/**
 * List.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */


package finalProject;

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

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new empty List
	 * 
	 * @postcondition a new, empty List created
	 */
	public List() {
		first = last = iterator = null;
		length = 0;
	}

	/**
	 * Instantiates a new List by copying another List
	 * 
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
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

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition  length !=0
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
		}
		return first.data; // 
	}

	/**
	 * Returns the value stored in the last node
	 * @precondition  length !=0
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!");
		}
		return last.data; // why do we return last.data and not last?
	}

	/**
	 * Returns the current length of the list
	 * 
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the iterator
	 * 
	 * @return the length of the list from 0 to n
	 */
	public T getIterator() {
		return iterator.data;
	}

	/**
	 * Returns whether the list is currently empty
	 * 
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	// returns whether or not the iterator is off the end of the list, i.e. null
	// @return whether the iterator is null
	public boolean offEnd() {
		return iterator == null;
	}

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the list
	 * @postcondition a new first node is created
	 */
	public void addFirst(T data) {
		Node N = new Node(data);
		if (length == 0)
			first = last = N;

		else {
			Node temp = first;
			N.next = temp;
			temp.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the list
	 * @postcondition a new last node is created
	 */
	public void addLast(T data) {
		Node N = new Node(data);
		if (length == 0) {
			first = last = N;
		} else {
			Node temp = last;
			N.prev = temp;
			temp.next = N;
			last = N;
		}

		length++;
	}

	/**
	 * removes the element at the front of the list
	 * 
	 * @precondition !length ==0
	 * @postcondition the first node is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) { // precondition
			throw new NoSuchElementException("removeFirst: list is empty. Cannot remove.");
		} else if (length == 1) { // edge case
			first = last = iterator = null;
		} else { // general case
			if (iterator == first) { // edge case
				iterator = null;
			}
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 * 
	 * @precondition !length ==0
	 * @postcondition the last node is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0)
			throw new NoSuchElementException("removeLast():  no element to remove");
		last = last.prev;
		last.next = null;
		length--;
	}

	/**
	 * moves the iterator to the beginning of the list
	 */
	public void placeIterator() {
		iterator = first;
	}

	/**
	 * removes the element currently referenced by the iterator
	 * 
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator is off end
	 * @postcondition iterator will be null
	 */
	public void removeIterator1() throws NullPointerException {
		if (iterator == null) { // precondition
			throw new NullPointerException("removeIterator: iterator is off end.");
		} else if (iterator == first) { // edge case
			removeFirst(); // should set iterator to null in this case
		} else if (iterator == last) { // edge case
			removeLast(); // should set iterator to null in this case
		} else { // general case
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			iterator = null;
			length--;
		}

	}

	/**
	 * add data
	 * 
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator is off end
	 * @postcondition iterator is not last
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("addIterator:" + "Iterator is off end. cannot add.");
		} else if (iterator == last)
			addLast(data);

		else {
			Node N = new Node(data);
			Node temp = iterator;
			Node temp2 = iterator.next;
			N.next = temp2;
			temp.next = N;
			temp2.prev = N;
			N.prev = temp;
		}
	}

	// Advances the iterator by one node in the list
	// @precondition !offEnd()
	// @throws NullPointerException when the precondition is vilated
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: " + "iterator is off the end of the list.");
		}
		iterator = iterator.next;
	}

	// moves the iterator down by one node
	// @precondition !offEnd()
	// @throws NullPointerException when the precondition is vilated
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator: " + "iterator is off the end of the list.");
		}
		iterator = iterator.prev;
	}

	/**
	 * Determines whether two Lists have the same data in the same order accessor
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) { // to check this two memories are the same?
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;// o is an object, this step is to convert o to List L
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { // Lists are same length
					if (!(temp1.data.equals(temp2.data))) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value on its own line At the end of the List a new line
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + " ";
			temp = temp.next;
		}
		return result + "\n";
	}

	/**
	 * prints the contents of the linked list to the screen in the format #:
	 * <element> followed by a newline
	 * 
	 * @return the List as a String for display
	 */
	public String printNumberedList() {
		String result = "";
		Node temp = first;
		for(int i=1; temp != null; i++) {
			result+= i + ". " + temp.data + "\n";
			temp= temp.next;
		}
		return result + "\n";
	}
	//prints a linked list to the console in reverse order from last to first
	//Iteratively (loop)
	public void printReverse() {
		Node temp= last;
		while(temp != null) {
			System.out.println(temp.data+ " ");
			temp= temp.prev;
		}
		System.out.println();
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
	   //fill in here
		if(index <0 && index > length) {
			throw new IndexOutOfBoundsException("iteratorToIndex out of bounds.");
		}
		iterator = first;
		for(int i=0; i< index;i++)
			advanceIterator();
	}

	/**
	* Searches the List for the specified
	* value using the linear  search algorithm
	* @param value the value to search for
	* @return the location of value in the
	* List or -1 to indicate not found
	* Note that if the List is empty we will
	* consider the element to be not found
	* post: position of the iterator remains
	* unchanged
	*/
	public int linearSearch(T value) {
		iterator = first;

		for (int i = 0; i < length; i++) {
			if (iterator.data.equals(value)) {
				return i;
			}
			iterator = iterator.next;
		}
		return -1;
	}
}