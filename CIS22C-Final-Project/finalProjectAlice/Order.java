/**
 * Order.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;

public class Order {
	private Customer customer;
	private Date date;
	private Painting painting;
	private int priority;
	private boolean shipped;
	
	/**CONSTRUCTORS*/
	
	/**
	 * Creates a new Order with no given information
	 * Assigns default values to all the data members
	 */
	public Order() {
		this.customer = null;
		this.date = null;
		this.painting = null;
	}
	
	
	/**
	 * Creates a new Order when all information is known
	 * @param cust the Customer
	 * @param date the date of the order
	 * @param content the content of the order
	 * @param speed the shipping speed chose by the Customer
	 * Assigns cust to customer
	 * Assigns date to date
	 * Assigns content to orderContents
	 * Assigns speed to shippingSpeed
	 */
	public Order(Customer cust, Date date, Painting painting, int speed) {
		this.customer = cust;
		this.date = date;
		this.painting = painting;
		this.shipped = false;
	}
	
	/**ACCESORS*/
	
	/**
	 * Accesses the customer
	 * @return the customer that made the order
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Accesses the date
	 * @return the date of the order
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Accesses the order contents
	 * @return the paintings the customer ordered
	 */
	public Painting getOrderContent() {
		return painting;
	}
	
	/**
	 * Accesses the shipping speed
	 * @return the shipping speed the customer chose
	 */
	
	public void setShipped(boolean bool) {
		this.shipped = bool;		
	}
	
	public boolean getShipped() {
		return shipped;
	}
	
	public void setPriority(int priorityNum) {
		this.priority = priorityNum;		
	}
	
	/**
	 * Accesses the priority
	 * @return the priorityOrder
	 */
	public int getPriority() {
		return priority;
	}
	
	/**MUTATORS*/
	
	/**
	 * Updates the customer
	 * @param the customer
	 */
	public void setCustomer(Customer cust) {
		this.customer = cust;
	}
	
	/**
	 * Updates the order date
	 * @param the date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Updates the order date
	 * @param the date
	 */
	public void setOrderContents(Painting painting) {
		this.painting = painting;
	}
	
	@Override 
	public String toString()
	{
		return "\nCustomer information:\n" + customer + 
				"Ordered Painting:\n" + painting + "\n" +
				"Date: " + date + "\n" + 
		   	    "Priority: " + priority + "\n" + 
				"Shipped: " + shipped+ "\n";
	}	
}

class PriorityComparator implements Comparator<Order> {
    /**
   * Compares the two order by priority of the order
   * @param order1 the first Order
   * @param order2 the second Order
   */
   @Override public int compare(Order order1, Order order2) {
	   //compare priority first, big priority means more important
	   int priorityOrder = -Integer.compare(order1.getPriority(), order2.getPriority());
	   if(priorityOrder != 0) {
		   return priorityOrder;
	   }
	   //if the priority is 0 means priority are equal, compare date to decide who go first
	   return -order1.getDate().compareTo(order2.getDate());
   }
}


