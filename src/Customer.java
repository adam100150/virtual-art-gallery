/**
 * Customer.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
package src;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Customer extends User {
	private String email;
	private String address;
	private double cash;

	//Constructor with all values given
	public Customer(String userName, String password, String firstName, String lastName, String email, String address, double cash) {
		super(userName, password, firstName, lastName);
		this.email = email;
		this.address = address;
		this.cash = cash;
	}

	public Customer() {
	}


	public String getEmail() {
		return email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public double getCash() {
		return cash;
	}
		
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	//						fileName = "Paintings.txt";
//						try {
//							BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
//							PrintWriter out = new PrintWriter(writer);
//							out.println();
//							out.println(title);
//							out.println(artist);
//							out.println(year);
//							out.println(price);
//							out.print(description);
//							out.close();
//						}
//						catch(IOException e)
//						{
//							e.printStackTrace();
//						}
	
	public void setCash(double cash) {
		this.cash = cash;
	}
	
	public void updateCash(double cash) {
		this.cash += cash;
	}

	public void printPaintingByTitle() {
		for (var p : Store.painting_name) {
			Painting currPainting = (Painting) p;
			if (currPainting.owner.equals(userName)) {
				System.out.println(currPainting);
			}
		}
	}
	
	public void printPaintingByValue() {
		for (var p : Store.painting_name) {
			Painting currPainting = (Painting) p;
			if (currPainting.owner.equals(userName)) {
				System.out.println(currPainting);
			}
		}
	}

	ArrayList<Order> getOrders() {
		ArrayList<Order> customerOrders = new ArrayList<Order>();
		for (var order : Store.orders) {
			Order currOrder = (Order) order;
			if (currOrder.getCustomer().userName.equals(userName)) {
				customerOrders.add(currOrder);
			}
		}
		return customerOrders;
	}
	
	@Override
	public String toString() {
		String result = "\nName: " + getFirstName() + " " + getLastName() + "\nUserName: " + getUserName() + "\nTotal Cash: $"
				+ new DecimalFormat("###,###,###.##").format(cash)+ "\n";
		if(!getOrders().isEmpty())
			result += "\nOrder History:\n" + getOrders() + "\n";
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (!(o instanceof Customer))
			return false;
		else {
			Customer c = (Customer) o;
			return this.getUserName().equals(c.getUserName()) && this.getPassword().equals(c.getPassword());
		}
	}
	/**
	 * Returns a consistent hash code for
	 * each Customer by summing the Unicode values
	 * of each character in the key
	 * Key = email + password
	 * @return the hash code
	 */
	@Override public int hashCode() {
		String key = getUserName() + getPassword();
		int sum = 0;
		for (int i = 0; i < key.length(); i++){
			sum += (int)key.charAt(i);
		}
		return sum;
	}
}