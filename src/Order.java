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
import java.util.Comparator;
import java.util.Date;

public class Order {
	private Customer customer;
	private String date;
	private Painting orderedPainting;
	private int shippingSpeed;
	private Date priorityDate;
	private boolean shipped;
	
	/**CONSTRUCTORS*/
	
	/**
	 * Creates a new Order with no given information
	 * Assigns default values to all the data members
	 */
	public Order() {
		this.customer = null;
		this.date = "";
		this.orderedPainting = null;
		this.shippingSpeed = 0;
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
	public Order(Customer cust, String date, Painting painting, int speed, boolean ship) {
		this.customer = cust;
		this.date = date;
		this.orderedPainting = painting;
		this.shippingSpeed = speed;
		this.shipped = ship;
		try {
			this.priorityDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").parse(date);
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
	public Date getPriorityDate() {
		return priorityDate;
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
	public Painting getOrderContents() {
		return orderedPainting;
	}
	
	/**
	 * Accesses the shipping speed
	 * @return the shipping speed the customer chose
	 */
	public int getShippingSpeed() {
		return shippingSpeed;
	}
	
	/**MUTATORS*/
	
	/**
	 * Updates the customer
	 * @param cust customer
	 */
	public void setCustomer(Customer cust) {
		this.customer = cust;
	}
	
	/**
	 * Updates the order date
	 * @param date the current date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Updates the order date
	 * @param painting painting
	 */
	public void setOrderContents(Painting painting) {
		this.orderedPainting = painting;
	}
	
	/**
	 * Updates the shipping speed
	 * @param speed chosen shipping speed
	 */
	public void setShippingSpeed(int speed) {
		this.shippingSpeed = speed;
	}
	
	public boolean isShipped()
	{
		return shipped;
	}
	
	public void setShipStatus(boolean status)
	{
		shipped = status;
	}
	
	public void ship()
	{
		shipped = true;
	}
	
	@Override public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if (!(o instanceof Order)) {
			return false;
		} else {
			Order p = (Order) o;
			if (this.date != p.getDate()) {
				return false;
			}
			return true;
		}
	}
	
	
	@Override
	public String toString() 
	{
		String shippingStatus = "Not yet shipped.";
		if(isShipped())
			shippingStatus = "Shipped!";
		String ship = "";
		if(shippingSpeed == 1)
		{
			ship = "Standard";
		}
		else if(shippingSpeed == 2)
		{
			ship = "Rushed";
		}
		else if(shippingSpeed == 3)
		{
			ship = "Overnight";
		}
		return  "\n" + orderedPainting.getTitle() + "\nCustomer: " + customer.getFirstName() + " " + customer.getLastName() + "\nDate: " + date
				+ "\n" + customer.getAddress() + "\nShipping speed: " + ship + "\n" + "Shipping Status: " + shippingStatus + "\n";
	}
	
}

class PriorityComparator implements Comparator<Order> {
    /**
   * Compares the two order by priority of the order
   * @param order1 the first Order
   * @param order2 the second Order
   */
   @Override public int compare(Order order1, Order order2) {
      return order2.getPriorityDate().compareTo(order1.getPriorityDate());
   }
}