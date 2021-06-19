import java.text.DecimalFormat;

public class Employee extends Person {
	private String userName;
	private String password;
	
	public Employee(String userName, String password, String firstName, String lastName) {
		super(userName, password, firstName, lastName);
	}
	
	public Employee(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	public void addProduct()
	{
		
	}
	
	public void removeProduct()
	{
		
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
	@Override
	public int hashCode() {
		String key = getUserName() + getPassword();
		int sum = 0;
		for (int i = 0; i < key.length(); i++)
			sum += (int) key.charAt(i);
		return sum;
	}
}