/**
 * Customer.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
import java.text.DecimalFormat;

public class Customer extends Person{
	private String email;
	private String address;
	private double cash;
	
	private BST<Painting> myPaintingsByTitle = new BST<>();
	private BST<Painting> myPaintingsByValue = new BST<>();
	
	
	public Customer(String userName, String password) {
		super(userName, password, "", "");

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
	
	public void addPainting(Painting painting) 
	{
		NameComparator c = new NameComparator();
		ValueComparator v = new ValueComparator();

		myPaintingsByTitle.insert(painting, c);
		myPaintingsByValue.insert(painting, v);	
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
	
	
	@Override
	public String toString() {
		return "Name: " + getFirstName() + " " + getLastName() + "\nUserName: " + getUserName() + "\nTotal Cash: $"
				+ new DecimalFormat("###,###,###.##").format(cash)+ "\n";
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