/**
 * Employee.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

import java.text.DecimalFormat;

public class Employee extends Person {
	private Store store;
	
	
	public Employee(String userName, String password) {
		super(userName, password, "", "");
	}

	public Employee(String userName, String password, String firstName, String lastName) {
		super(userName, password, firstName, lastName);
	}
	
	public boolean passwordMatch(String anotherPassword) {		
		return anotherPassword.equals(this.getPassword());
	}
	
	public boolean searchCustomer(Customer customer) {
		return store.getCustomers().contains(customer);		
	}
	
	public void displayCustomer() {
		store.getCustomers().printTable();		
	}
	
	public void viewOrders() {
		store.getOrders();		
	}
	
	public void addProduct() {
		store.addPainting();		
	}
	
	public void removeProduct() {
		store.removePainting();		
	}

	@Override
	public String toString() {
		return "Username: " + getUserName() + "\nUser Password: " + getPassword() + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (!(o instanceof Employee))
			return false;
		else {
			Employee c = (Employee) o;
			return this.getUserName().equals(c.getUserName()) && this.getPassword().equals(c.getPassword());
		}
	}
}