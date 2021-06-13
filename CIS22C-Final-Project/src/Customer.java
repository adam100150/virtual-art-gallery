import java.text.DecimalFormat;

public class Customer extends Person{
	private String email;
	private String address;
	private String city;
	private String state;
	private int zip;
	private double cash;
	
	private BST<String> artTitle = new BST<> ();
	private BST<String> artAuthor = new BST<> ();
	private BST<Double> artPrice = new BST<>();
	private List<Order> orders = new List<> ();
	
	public Customer() {
		super();
		email = "undefine";
		address = "undefine";
		city = "undefine";
		state = "undefine";
		zip = 0;
		cash = 0.0;
	}
	
	public Customer(String email, String address, String city, String state, int zip, double cash) {
		super();
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.cash = cash;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	public int getZip() {
		return zip;
	}
	public double getCash() {
		return cash;
	}
	
	public boolean passwordMatch( String anotherPassword) {
		return this.getPassword().compareTo(anotherPassword)==0;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public void updateCash(double cash) {
		this.cash += cash;
	}
	public void viewArtAuthor() {
		artAuthor.inOrderPrint();
	}
	public void viewArtTitle() {
		artTitle.inOrderPrint();
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
	@Override 
	public int hashCode() {
		String key = address + city + state;
		int sum = 0;
		for(int i = 0; i< key.length(); i++)
			sum += (int) key.charAt(i);
		return sum;
		
	}
}
