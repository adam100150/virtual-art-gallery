/**
 * Employee.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
public class Employee extends User {

	
	public Employee(String userName, String password, String firstName, String lastName) {
		super(userName, password, firstName, lastName);
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