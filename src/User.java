/**
 * CustomerInterface.java
 * @author Nathan Brin
 * @author Adam Ashkenazi
 * @author Sihan Sun
 * @author Alice Zhang
 * CIS 22C Final Project
 */

package src;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;

public abstract class User {
	protected String userName;
	protected String password;
	protected String firstName;
	protected String lastName;
	final static private int MAX_ATTEMPTS = 10;

	public User(String userName, String password, String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}

	public User() {

	}

	String getUserName() {
		return userName;
	}
	String getPassword() {
		return password;
	}
	String getFirstName() {
		return firstName;
	}
	String getLastName() {
		return lastName;
	}
	
	void setUserName(String userName) {
		this.userName = userName;
	}
	void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	void setLastName(String lastName) {
		this.lastName = lastName;
	}
	void setPassword(String password) {this.password = password; }

	static Customer createCustomerAccount(Scanner input, Store store) throws ArtGalleryException {
		Customer currentCustomer = new Customer();

		System.out.println("Let's create an account for you!");
		System.out.print("Enter your username: ");
		currentCustomer.setUserName(input.nextLine());

		System.out.println("Enter your password. Your password must have: ");
		System.out.println("At least one digit [0-9]\n" +
				"At least one lowercase character [a-z]\n" +
				"At least one uppercase character [A-Z]\n" +
				"At least one special character [*.!@#$%^&:;<>,.?/~_+-=|\\]\n" +
				"At least 8 characters in length, but no more than 32.");

		try {
			String password = input.nextLine();
			checkForValidInput("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{4,12}$", password, "password", input);
			currentCustomer.setPassword(password);
			System.out.print("Enter your first name: ");
			String firstName = input.nextLine();
			checkForValidInput("[A-Za-z]{3,10}", firstName, "First Name", input);
			currentCustomer.setFirstName(firstName);
			System.out.print("Enter your last name: ");
			String lastName = input.nextLine();
			checkForValidInput("[A-Za-z]{3,10}", lastName, "Last Name", input);
			currentCustomer.setLastName(lastName);
			System.out.print("Enter your Email: ");
			String email = input.nextLine();
			checkForValidInput("^[A-Za-z0-9+_.-]+@(.+)$", email, "Email", input);
			currentCustomer.setEmail(email);
			System.out.print("Enter your address: ");
			String address = input.nextLine();
			checkForValidInput("^[ \\w]{3,}([A-Za-z]\\.)?([ \\w]*\\#\\d+)?(\\r\\n| )[ \\w]{3,},\\x20[A-Za-z]{2}\\x20\\d{5}(-\\d{4})?$", address, "Address", input);
			currentCustomer.setAddress(address);
			System.out.print("Enter the amount of cash to fund your account: ");
			String cash = input.nextLine();
			checkForValidInput("([0-9]*)\\.([0-9]*)", cash, "Cash Amount", input);
			currentCustomer.setCash(Double.parseDouble(cash));

			if (store.customers.containsKey(currentCustomer.getUserName())) {
				throw new ArtGalleryException("Customer already exists in database");
			}

			System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
		} catch (ArtGalleryException e) {
			System.err.println(e.getMessage());
		}
		return currentCustomer;
	}

	static <T extends User> T login(Scanner input, HashMap<String, T> userDatabase) throws ArtGalleryException {
		System.out.print("Username: ");
		String username = input.nextLine();

		if (!userDatabase.containsKey(username)) {
			throw new ArtGalleryException("\nCouldn't find user in system. Please restart the application and create an account.");
		}

		System.out.print("Password: ");
		String password = input.nextLine();

		T currUser = userDatabase.get(username);
		int counter = 0;
		while (!currUser.getPassword().equals(password) && counter < MAX_ATTEMPTS) {
			int remainingAttempts = MAX_ATTEMPTS - counter;
			System.out.println("\nIncorrect password. You have " + remainingAttempts + " more attempts.");
			password = input.nextLine();
			counter++;
		}

		if (!currUser.getPassword().equals(password)) {
			throw new ArtGalleryException("Incorrect password exceeded number of attempts");
		}

		System.out.println("\nWelcome " + currUser.firstName + " " + currUser.lastName + " !");
		return currUser;
	}

	static Customer loginAsCustomer(Scanner input, Store store) throws ArtGalleryException {
		return login(input, store.customers);
	}

	static Employee loginAsEmployee(Scanner input, Store store) throws ArtGalleryException {
		return login(input, store.employees);
	}

	private static void checkForValidInput(String regex, String input, String inputName, Scanner ioInput) throws ArtGalleryException {
		boolean validInput = Pattern.matches(regex, input);
		int counter = 0;
		while (!validInput && counter < MAX_ATTEMPTS) {
			System.out.println(inputName + " is invalid. Please try again.");
			input = ioInput.nextLine();
			validInput = Pattern.matches(regex, input);
			counter++;
		}

		if (!validInput) {
			throw new ArtGalleryException("Invalid input. Too many times attempted.");
		}
	}
}

