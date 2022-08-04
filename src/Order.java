/**
 * Order.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
package src;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Order {
	private Customer customer;
	private String orderDate;
	private Painting orderedPainting;
	Shipping shippingSpeed;
	private Date shippingDate;
	boolean shipped;
	final static int NUM_LINES_OF_ORDER = 4;
	
	/**CONSTRUCTORS*/
	
	/**
	 * Creates a new Order with no given information
	 * Assigns default values to all the data members
	 */
	public Order() {
		this.customer = null;
		this.orderDate = "";
		this.orderedPainting = null;
		this.shippingSpeed = null;
		shipped = false;
	}
	
	
	/**
	 * Creates a new Order when all information is known
	 * @param cust the Customer
	 * @param date the date of the order
	 * @param speed the shipping speed chose by the Customer
	 * Assigns cust to customer
	 * Assigns date to date
	 * Assigns content to orderContents
	 * Assigns speed to shippingSpeed
	 */
	public Order(Customer cust, String date, Painting painting, Shipping speed, boolean shipped) {
		this.customer = cust;
		this.orderDate = date;
		this.orderedPainting = painting;
		this.shippingSpeed = speed;
		this.shipped = shipped;
		try {
			this.shippingDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(shippingDate);
			c.add(Calendar.DATE, speed.days);
		} catch (ParseException e){
			e.printStackTrace();
		}
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
	 * Accesses the customer
	 * @return the customer that made the order
	 */
	public Date getShippingDate() {
		return shippingDate;
	}
	
	/**
	 * Accesses the order contents
	 * @return the paintings the customer ordered
	 */
	public Painting getOrderContents() {
		return orderedPainting;
	}
	
	/**MUTATORS*/
	@Override public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if (!(o instanceof Order)) {
			return false;
		} else {
			Order order = (Order) o;
			return getOrderContents().equals(order.getOrderContents());
		}
	}
	
	
	@Override
	public String toString() 
	{
		String shippingStatus = "Not yet shipped.";
		if(shipped)
			shippingStatus = "Shipped!";

		return  "\n" + orderedPainting.getTitle() + "\nCustomer: " + customer.getFirstName() + " " + customer.getLastName() + "\nDate: " + orderDate
				+ "\n" + customer.getAddress() + "\nShipping speed: " + shippingSpeed + "\n" + "Shipping Status: " + shippingStatus + "\n";
	}
	
}

class PriorityComparator implements Comparator<Order> {
    /**
   * Compares the two order by priority of the order
   * @param order1 the first Order
   * @param order2 the second Order
   */
   @Override public int compare(Order order1, Order order2) {
      return order2.getShippingDate().compareTo(order1.getShippingDate());
   }
}