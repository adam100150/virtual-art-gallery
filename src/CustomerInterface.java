package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * CustomerInterface.java
 * @author  Nathan Brin
 * @author Adam Ashkenazi
 * @author Sihan Sun
 * @author Alice Zhang
 * CIS 22C Final Project
 */

public class CustomerInterface {

	static char userInput;
	static Scanner input = new Scanner(System.in);
	static Store store = new Store();
	static User currUser;

	public static void main(String[] args) {
		Painting currentPainting;
		File welcomeFile = new File("src/text-files/menus/WelcomeMenu.txt");
		try {
			System.out.println(Utils.readContentsAsString(welcomeFile));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		userInput = input.nextLine().charAt(0);
		User currUser = null;
		switch (userInput) {
			case '1':
				try {
					currUser = User.loginAsCustomer(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
					exit(0);
				}
				break;
			case '2':
				try {
					currUser = User.createCustomerAccount(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
					exit(0);
				}
				break;
			case '3':
				try {
					currUser = User.loginAsEmployee(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
					exit(0);
				}
				break;
			case '4':
				System.out.println("Welcome! \n");
				break;
			default:
				System.err.println("Invalid choice");
				exit(0);
				break;
		}

		if (currUser == null) {
			printDefaultGuest();
			userInput = input.next().charAt(0);
			switch (userInput) {
				case 'A':
					searchForPaintingsPrice();
				break;
				case 'B':
					searchForPaintingsTitle();
					break;
				case 'C':
					store.printPaintingsByValue();
					break;
				case 'D':
					store.printPaintingsByName();
					break;
				default:
					System.out.println("\nInvalid choice");
					break;
			}
		}

		if (currUser instanceof Customer) {
			printDefaultCustomer();
			Customer currCustomer = (Customer) currUser;
			userInput = input.nextLine().charAt(0);
			switch (userInput) {
				case 'A':
					searchForPaintingsPrice();
					break;
				case 'B':
					searchForPaintingsTitle();
					break;
				case 'C':
					store.printPaintingsByValue();
					break;
				case 'D':
					store.printPaintingsByName();
					break;
				case 'E':
					try {
						store.placeOrder(placeAnOrder(currCustomer));
					} catch (ArtGalleryException e) {
						System.out.println(e.getMessage());
						System.out.println("Would you like to place more money in your account? [Y/N]");
						userInput = input.nextLine().charAt(0);
						if (userInput == 'Y') {
							System.out.println("How much?");
							double amount = Double.parseDouble(input.nextLine());
							currCustomer.updateCash(amount);
						}
					}
					break;
				case 'F':
					viewPurchases(currCustomer);
					break;
				default:
					System.out.println("\nInvalid choice");
					break;
			}
		}

		if (currUser instanceof Employee) {
			printDefaultEmployee();
			userInput = input.nextLine().charAt(0);

			switch (userInput) {
				case 'A':
					System.out.println("Enter the username of the customer: ");
					String username = input.nextLine();
					if (!store.customers.containsKey(username)) {
						System.err.println("Customer not in system");
						exit(0);
					}

					System.out.println(store.customers.get(username));
					break;
				case 'B':
					System.out.println(store.customers);
					break;
				case 'C':
					store.viewShippedOrders();
					break;
				case 'D':
					store.shipOrder();
					break;
				case 'E':
					store.printPaintingsByValue();
					break;
				case 'F':
					store.printPaintingsByName();
					break;
				case 'G':
						String title, artist, description;
						int year;
						double price;
						System.out.println("Wonderful! A new painting to the collection.");
						System.out.println("What is the name of the painting?");
						title = input.nextLine();
						System.out.println("Who is the artist?");
						artist = input.nextLine();
						System.out.println("What year was this masterpiece created?");
						year = input.nextInt();
						input.nextLine();
						System.out.println("How much is corporate charging for this painting?");
						price = input.nextDouble();
						input.nextLine();
						System.out.println("What is this painting about?");
						description = input.nextLine();
						currentPainting = new Painting(title, artist, year, price, description);
						System.out.println("New Painting: ");
						System.out.print(currentPainting);
						store.addPainting(currentPainting);
					break;
				case 'H':
					System.out.println("What is the name of the painting to be removed?");
					String removeTitle = input.nextLine();
					currentPainting = store.searchPaintingName(removeTitle);
					try {
						store.removePainting(currentPainting);
					} catch (ArtGalleryException e) {
						System.out.println(e.getMessage());
					}
					break;
				default:
					System.out.println("\nInvalid choice");
					break;
			}
		}
	}

	static void printDefaultGuest() {
		File guestFile = new File("src/text-files/menus/DefaultGuestMenu.txt");
		try {
			System.out.print(Utils.readContentsAsString(guestFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	static void printDefaultEmployee() {
		File employeeFile = new File("src/text-files/menus/DefaultEmployeeMenu.txt");
		try {
			System.out.print(Utils.readContentsAsString(employeeFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	static void printDefaultCustomer() {
		File guestFile = new File("src/text-files/menus/DefaultCustomerMenu.txt");
		try {
			System.out.print(Utils.readContentsAsString(guestFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	static Order placeAnOrder(Customer currCustomer) throws ArtGalleryException {
		System.out.println("What is the title of the painting that you would like to purchase?");
		input.nextLine();
		String stringInput = input.nextLine();
		Painting currentPainting = store.searchPaintingName(stringInput);

		if (currentPainting == null) {
			throw new ArtGalleryException("Sorry we do not have that painting at the moment.");
		}

		System.out.println("The painting you are ordering is: ");
		System.out.println(currentPainting);
		System.out.println("What kind of shipping would you like?");
		System.out.println("(1) Standard 5-10 business days");
		System.out.println("(2) Rush 2-3 business days");
		System.out.println("(3) Overnight");

		int speedIntInput = input.nextInt();
		input.nextLine();
		if (currCustomer.getCash() < currentPainting.getPrice()) {
			throw new ArtGalleryException("Customer doesn't have enough money in account");
		}

		Shipping shippingSpeed = null;
		switch (speedIntInput) {
			case 1:
				shippingSpeed = Shipping.STANDARD;
				break;
			case 2:
				shippingSpeed = Shipping.RUSHED;
				break;
			case 3:
				shippingSpeed = Shipping.OVERNIGHT;
				break;
			default:
				System.out.println("Invalid input");
				break;
		}

		String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		return new Order(currCustomer, timeStamp, currentPainting, shippingSpeed, false);
	}

	static void viewPurchases(Customer currCustomer) {
		System.out.println("Would you like to view your shipped or unshipped orders?");
		System.out.println("(1) Shipped");
		System.out.println("(2) Unshipped");
		int shippedInput = Integer.parseInt(input.nextLine());

		if(shippedInput == 1) {
			store.viewShippedOrders(currCustomer);
			System.out.println();
		}
		else if(shippedInput == 2) {
			store.viewUnshippedOrders(currCustomer);
			System.out.println();
		}
		else {
			System.out.println("Invalid choice.");
		}
	}

	static void searchForPaintingsPrice() {
		System.out.println("Enter the price of the painting you are looking for: ");
		double paintingPrice = Double.parseDouble(input.nextLine());
		Painting currentPainting = store.searchPaintingPrice(paintingPrice);
		if (currentPainting != null) {
			System.out.println(currentPainting);
		} else {
			System.out.println("Sorry we do not have that painting at the moment.");
		}
	}

	static void searchForPaintingsTitle() {
		System.out.println("Enter the name of the painting you are looking for: ");
		String paintingName = input.nextLine();
		Painting currentPainting = store.searchPaintingName(paintingName);
		if (currentPainting != null) {
			System.out.println(currentPainting);
		} else {
			System.out.println("Sorry we do not have that painting at the moment.");
		}
	}
}