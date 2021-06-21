/**
 * Customer.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Customer extends Person{
	private String email;
	private String address;
	private double cash;

	private BST<Painting> myPaintingsByTitle = new BST<>();
	private BST<Painting> myPaintingsByValue = new BST<>();
	
	public Customer(String userName, String password) {
		super(userName, password, null, null);
	}
	
	public Customer(String firstName, String lastName, String email) {
		super(null, null, firstName, lastName);
		this.email = email;
	}
			
	public Customer(String userName, String password, String firstName, String lastName, 
			String email, String address, double cash) {
		super(userName, password, firstName, lastName);
		this.email = email;
		this.address = address;
		this.cash = cash;
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
	
	public boolean passwordMatch(String anotherPassword) {		
		return anotherPassword.equals(this.getPassword());
	}
			
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Order placeOrder(Painting painting) {
		Order newOrder;
		NameComparator c = new NameComparator();
		ValueComparator v = new ValueComparator();

		myPaintingsByTitle.insert(painting, c);
		myPaintingsByValue.insert(painting, v);
		
		cash -= painting.getPrice();
		newOrder = new Order(this, new Date(), painting, 0);
		
		return newOrder;
	}
		
	public void setCash(double cash) {
		this.cash = cash;
	}
	
	public void updateCash(double cash) {
		this.cash += cash;
	}
	
	public void printPaintingByTitle() {
		myPaintingsByTitle.inOrderPrint();
	}
	
	public void printPaintingByValue() {
		myPaintingsByValue.inOrderPrint();
	}
	
	public boolean hasPainting() {
		return !myPaintingsByTitle.isEmpty();
	}
	
	
	@Override
	public String toString() {
		return "Customer Name: " + this.getFirstName() + " " + this.getLastName() + "\n" +
				"UserName: " + this.getUserName() + "\n" +
				"Email: " + email +  "\n" +
				"Address: " + address +  "\n" +
				"Total Cash: $" + new DecimalFormat("###,###,###.##").format(cash)+ "\n";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (!(o instanceof Customer))
			return false;
		else {
			Customer c = (Customer) o;
			// if userName not provided check these things
			if(this.getUserName() == null || c.getUserName() == null) {
				return this.getFirstName().equals(c.getFirstName()) &&
					  this.getLastName().equals(c.getLastName()) &&
					  email.equals(c.email);
			}
			return this.getUserName().equals(c.getUserName()) && this.getPassword().equals(c.getPassword());
		}
	}
}
