
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class Customer extends Person{
	private String email;
	private String address;
	private double cash;
	
	//private BST<Painting> paintings;
	private BST<Painting> myPaintingsByTitle = new BST<>();
	private BST<Painting> myPaintingsByValue = new BST<>();
	
	
	
	
	
	//printPaintingByArtist
	//printPaintingByValue
	
	/*
	private BST<String> artTitle = new BST<> ();
	private BST<String> artist = new BST<> ();
	private BST<Double> artValue = new BST<>();
	private List<Order> orders = new List<> ();
	*/	
	
	public Customer(String userName, String password) {
		super(userName, password, "", "");

		//email = "undefine";
		//address = "undefine";
		//city = "undefine";
		//state = "undefine";
		//zip = 0;
		//cash = 0.0;
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
	
	/*
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	public int getZip() {
		return zip;
	}
	*/
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
	
	/*
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	*/
	
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
			return this.getFirstName().equals(c.getFirstName()) && this.getLastName().equals(c.getLastName())
				&& this.getUserName().equals(c.getUserName())&& this.address.equals(c.address);
		}
	}
	
	/*
	@Override 
	public int hashCode() {
		String key = address + city + state;
		int sum = 0;
		for(int i = 0; i< key.length(); i++)
			sum += (int) key.charAt(i);
		return sum;		
	}
	*/
}