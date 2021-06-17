/**
 * @author Adam Ashkenazi
 * Final Project
 */


import java.util.Comparator;

public class Order {
	private Customer customer;
	private String date;
	private List<Painting> orderContents;
	private int shippingSpeed;
	private int priority;
	
	/**CONSTRUCTORS*/
	
	/**
	 * Creates a new Order with no given information
	 * Assigns default values to all the data members
	 */
	public Order() {
		this.customer = null;
		this.date = "";
		this.orderContents = null;
		this.shippingSpeed = 0;
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
	public Order(Customer cust, String date, List<Painting> content, int speed) {
		this.customer = cust;
		this.date = date;
		this.orderContents = content;
		this.shippingSpeed = speed;
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
	public String getDate() {
		return date;
	}
	
	/**
	 * Accesses the order contents
	 * @return the paintings the customer ordered
	 */
	public List<Painting> getOrderContents() {
		return orderContents;
	}
	
	/**
	 * Accesses the shipping speed
	 * @return the shipping speed the customer chose
	 */
	public int getShippingSpeed() {
		return shippingSpeed;
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
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Updates the order date
	 * @param the date
	 */
	public void setOrderContents(List<Painting> contents) {
		this.orderContents = contents;
	}
	
	/**
	 * Updates the shipping speed
	 * @param the chosen shipping speed
	 */
	public void setShippingSpeed(int speed) {
		this.shippingSpeed = speed;
	}
	
}

class PriorityComparator implements Comparator<Order> {
    /**
   * Compares the two order by priority of the order
   * @param order1 the first Order
   * @param order2 the second Order
   */
   @Override public int compare(Order order1, Order order2) {
      if (order1.getPriority() > order2.getPriority()) {
    	  return 1;
      } else if (order1.getPriority() < order2.getPriority()) {
    	  return -1;
      } else {
    	  return 0;
      }
   }
}
