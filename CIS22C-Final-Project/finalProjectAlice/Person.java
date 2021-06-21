/**
 * Person.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

public abstract class Person {
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	
	public Person() {
		userName = "undefine";
		password= "undefine";
		firstName = "undefine";
		lastName = "undefine";
	}
	
	public Person(String userName, String password, String firstName, String lastName) {
		this.userName= userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public int hashCode() {
		String key = userName + password;
		int sum = 0;
		for(int i = 0; i< key.length(); i++)
			sum += (int) key.charAt(i);
		return sum;		
	}
}