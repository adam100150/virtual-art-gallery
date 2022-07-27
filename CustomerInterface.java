import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CustomerInterface.java
 * @author  Nathan Brin
 * @author Adam Ashkenazi
 * @author Sihan Sun
 * @author Alice Zhang
 * CIS 22C Final Project
 */

public class CustomerInterface {

	public static void main(String[] args)
	{
		Store store = new Store();
		String userName, password, firstName, lastName, email, address;

		char userInput;
		String stringInput;
		Painting currentPainting;
		Order currentOrder;
		double cash;
		String fileName;

		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to One-To-One Painting Replicas! \n");

		System.out.println("Would you like to login as a Guest, Customer or an Employee or Create a new Account?");
		System.out.println("(1) Login as a Customer");
		System.out.println("(2) Create new Customer Account");
		System.out.println("(3) Login as an Employee");
		System.out.println("(4) Log in as Guest");
		
		userInput = input.nextLine().charAt(0);
		User currUser;
		switch (userInput) {
			case '1':
				try {
					currUser = User.loginAsCustomer(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
				}
				break;
			case '2':
				try {
					currUser = User.createCustomerAccount(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
				}
				break;
			case '3':
				try {
					currUser = User.loginAsEmployee(input, store);
				} catch (ArtGalleryException e) {
					System.err.println(e.getMessage());
				}
				break;
			case '4':
				System.out.println("Welcome! \n");
				break;
			default:
				System.out.println("Invalid choice");
				break;
		}

//		if(loginIntInput == 1 || loginIntInput == 2) //Customer interface
//		{
//			printDefaultCustomer();
//			userInput = input.next().charAt(0);
//
//			while(userInput != 'X'){
//				if (userInput == 'A')
//				{
//					System.out.println("Would you like to search for a painting by Title or by price?");
//					System.out.println("(1) Title");
//					System.out.println("(2) Price");
//					int titleOrPrice = input.nextInt();
//					input.nextLine();
//
//					if(titleOrPrice == 1)
//					{
//						System.out.println("Enter the name of the painting you are looking for.");
//						stringInput = input.nextLine();
//						currentPainting = store.searchPaintingName(new Painting(stringInput));
//						if(currentPainting != null)
//						{
//							System.out.println(currentPainting);
//						}
//						else
//						{
//							System.out.println("Sorry we do not have that painting at the moment.");
//						}
//					}
//					else if(titleOrPrice == 2)
//					{
//						System.out.println("Enter the price of the painting you are looking for.");
//						cash = input.nextDouble();
//						input.nextLine();
//						currentPainting = store.searchPaintingPrice(new Painting(cash));
//						if(currentPainting != null)
//						{
//							System.out.println(currentPainting);
//						}
//						else
//						{
//							System.out.println("Sorry we do not have that painting at the moment.");
//						}
//					}
//					else
//					{
//						System.out.println("Invalid choice.");
//					}
//				}
//				else if(userInput == 'B')
//				{
//					System.out.println("Would you like to view our paintings ordered by Title or by Price?");
//					System.out.println("(1) Title");
//					System.out.println("(2) Price");
//					int titleOrPriceInput = input.nextInt();
//					input.nextLine();
//
//					if(titleOrPriceInput == 1)
//					{
//						store.printPaintingsByName();
//					}
//					else if(titleOrPriceInput == 2)
//					{
//						store.printPaintingsByValue();
//					}
//					else
//					{
//						System.out.println("Invalid choice.");
//					}
//				}
//				else if(userInput == 'C')
//				{
//					System.out.println("What is the title of the painting that you would like to purchase?");
//					input.nextLine();
//					stringInput = input.nextLine();
//					currentPainting = store.searchPaintingName(new Painting(stringInput));
//
//					if(currentPainting != null)
//					{
//						System.out.println("The painting you are ordering is: ");
//						System.out.println(currentPainting);
//						System.out.println("What kind of shipping would you like?");
//						System.out.println("(1) Standard 5-10 business days");
//						System.out.println("(2) Rush 2-3 business days");
//						System.out.println("(3) Overnight");
//
//						int speedIntInput = input.nextInt();
//						input.nextLine();
//
//						if (currentCustomer.getCash() < currentPainting.getPrice()) {
//							System.out.println("Customer doesn't have enough money in account");
//							System.out.println("Money left: " + currentCustomer.getCash());
//						} else {
//							String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
//							if (speedIntInput == 1) {
//								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, loginIntInput, false);
//								store.placeOrder(currentOrder);
//								System.out.println("Painting ordered! You can expect your painting within the next 5-10 business days.\n");
//							} else if (speedIntInput == 2) {
//								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, loginIntInput, false);
//								store.placeOrder(currentOrder);
//								System.out.println("Painting ordered! You can expect your painting within the next 2-3 business days.\n");
//							} else if (speedIntInput == 3) {
//								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, loginIntInput, false);
//								store.placeOrder(currentOrder);
//								System.out.println("Painting ordered! You can expect your painting to be shipped overnight.\n");
//							}
//							fileName = "Orders.txt";
//							try {
//								BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
//								PrintWriter out = new PrintWriter(writer);
//								out.println();
//								out.println(timeStamp);
//								out.println(currentCustomer.getUserName());
//								out.println(currentCustomer.getPassword());
//								out.println(currentPainting.getTitle());
//								out.println(speedIntInput);
//								out.print(false);
//								out.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else
//					{
//						System.out.println("Sorry we do not have that painting at the moment.");
//					}
//				}
//				else if(userInput == 'D')
//				{
//					System.out.println("Would you like to view your shipped or unshipped orders?");
//					System.out.println("(1) Shipped");
//					System.out.println("(2) Unshipped");
//					int shippedIntInput = input.nextInt();
//					input.nextLine();
//
//					if(shippedIntInput == 1)
//					{
//						store.viewShippedOrders(currentCustomer);
//						System.out.println();
//					}
//					else if(shippedIntInput == 2)
//					{
//						store.viewUnshippedOrders(currentCustomer);
//						System.out.println();
//					}
//					else
//					{
//						System.out.println("Invalid choice.");
//					}
//				}
//				else
//				{
//					System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
//				}
//				printDefaultCustomer();
//				userInput = input.next().charAt(0);
//			}
//			System.out.println("\nGoodbye!");
//			input.close();
//		}
//		if(loginIntInput == 4){ //Guest interface
//			printDefaultGuest();
//			userInput = input.next().charAt(0);
//
//			while(userInput != 'X') {
//				if (userInput == 'A') {
//					System.out.println("Would you like to search for a painting by Title or by price?");
//					System.out.println("(1) Title");
//					System.out.println("(2) Price");
//					int titleOrPrice = input.nextInt();
//					input.nextLine();
//
//					if (titleOrPrice == 1) {
//						System.out.println("Enter the name of the painting you are looking for.");
//						stringInput = input.nextLine();
//						currentPainting = store.searchPaintingName(new Painting(stringInput));
//						if (currentPainting != null) {
//							System.out.println(currentPainting);
//						} else {
//							System.out.println("Sorry we do not have that painting at the moment.");
//						}
//					} else if (titleOrPrice == 2) {
//						System.out.println("Enter the price of the painting you are looking for.");
//						cash = input.nextDouble();
//						input.nextLine();
//						currentPainting = store.searchPaintingPrice(new Painting(cash));
//						if (currentPainting != null) {
//							System.out.println(currentPainting);
//						} else {
//							System.out.println("Sorry we do not have that painting at the moment.\n");
//						}
//					} else {
//						System.out.println("Invalid choice.");
//					}
//				} else if (userInput == 'B') {
//					System.out.println("Would you like to view our paintings ordered by Title or by Price?");
//					System.out.println("(1) Title");
//					System.out.println("(2) Price");
//					int titleOrPriceInput = input.nextInt();
//					input.nextLine();
//
//					if (titleOrPriceInput == 1) {
//						store.printPaintingsByName();
//					} else if (titleOrPriceInput == 2) {
//						store.printPaintingsByValue();
//					} else {
//						System.out.println("Invalid choice.");
//					}
//				}
//				else
//					System.out.println("\nInvalid Choice. Please enter A or B or X to exit.\n");
//				printDefaultGuest();
//				userInput = input.next().charAt(0);
//			}
//			System.out.println("\nGoodbye!");
//			input.close();
//		}
//
//		if(loginIntInput == 3) //Employee Interface
//		{
//				printDefaultEmployee();
//				userInput = input.next().charAt(0);
//
//				while(userInput != 'X'){
//					if (userInput == 'A')
//					{
//						input.nextLine();
//						System.out.println("\nEnter the name of the customer you would like to search for:");
//						System.out.println("\nFirst name: ");
//						firstName = input.nextLine();
//						System.out.println("Last name: ");
//						lastName = input.nextLine();
//						System.out.println("Enter the username of the customer: ");
//						userName = input.nextLine();
//						System.out.println("Enter the password of the customer: ");
//						password = input.nextLine();
//						currentCustomer = new Customer(userName, password);
//						currentCustomer = store.searchCustomer(currentCustomer);
//						if(currentCustomer == null)
//						{
//							System.out.println("Could not find that customer.\n");
//						}
//						else
//							System.out.println(currentCustomer);
//					}
//					else if(userInput == 'B')
//					{
//						store.displayCustomers();
//					}
//					else if(userInput == 'C')
//					{
//						store.viewUnshippedOrders();
//					}
//					else if(userInput == 'E')
//					{
//						System.out.println("Would you like to see the products listed by Title or price?");
//						System.out.println("(1) Title");
//						System.out.println("(2) Price");
//						loginIntInput = input.nextInt();
//						input.nextLine();
//						if(loginIntInput == 1)
//						{
//							store.printPaintingsByName();
//						}
//						else if (loginIntInput == 2)
//						{
//							store.printPaintingsByValue();
//						}
//						else
//						{
//							System.out.println("Invalid choice.");
//						}
//					}
//					else if(userInput == 'D')
//					{
//						store.shipOrder();
//					}
//					else if(userInput == 'F')
//					{
//						input.nextLine();
//						String title, artist, description;
//						int year;
//						double price;
//						System.out.println("Wonderful! A new painting to the collection.");
//						System.out.println("What is the name of the painting?");
//						title = input.nextLine();
//						System.out.println("Who is the artist?");
//						artist = input.nextLine();
//						System.out.println("What year was this masterpeice created?");
//						year = input.nextInt();
//						input.nextLine();
//						System.out.println("How much is corporate charging for this painting?");
//						price = input.nextDouble();
//						input.nextLine();
//						System.out.println("What is this painting about?");
//						description = input.nextLine();
//						currentPainting = new Painting(title, artist, year, price, description);
//						System.out.println("New Painting: ");
//						System.out.print(currentPainting);
//
//						store.addPainting(currentPainting);
//
//						fileName = "Paintings.txt";
//						try {
//							BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
//							PrintWriter out = new PrintWriter(writer);
//							out.println();
//							out.println(title);
//							out.println(artist);
//							out.println(year);
//							out.println(price);
//							out.print(description);
//							out.close();
//						}
//						catch(IOException e)
//						{
//							e.printStackTrace();
//						}
//
//					}
//					else if(userInput == 'G')
//					{
//						input.nextLine();
//						System.out.println("What is the name of the painting to be removed?");
//						String title = input.nextLine();
//						currentPainting = new Painting(title);
//						currentPainting = store.searchPaintingName(currentPainting);
//						if (currentPainting == null) {
//							System.out.println("Sorry that painting does not exist.");
//						} else {
//							store.removePainting(currentPainting);
//
//							fileName = "Paintings.txt";
//							File tempFile = new File("temptxtfile.txt");
//							try {
//								BufferedReader reader = new BufferedReader(new FileReader(fileName));
//								BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//								PrintWriter out = new PrintWriter(writer);
//								String currentLine;
//
//								while ((currentLine = reader.readLine()) != null) {
//									if (currentLine.equals(title)) {
//										reader.readLine();
//										reader.readLine();
//										reader.readLine();
//										reader.readLine();
//										reader.readLine();
//									} else {
//										out.println(currentLine);
//									}
//								}
//								reader.close();
//								out.close();
//
//								reader = new BufferedReader(new FileReader(tempFile));
//								writer = new BufferedWriter(new FileWriter(fileName));
//								out = new PrintWriter(writer);
//
//								while ((currentLine = reader.readLine()) != null) {
//									out.println(currentLine);
//								}
//								reader.close();
//								out.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else
//					{
//						System.out.println("\nInvalid menu option. Please enter A-G or X to exit.\n");
//					}
//					printDefaultEmployee();
//					userInput = input.next().charAt(0);
//				}
//				System.out.println("\nGoodbye!");
//				input.close();
		}

	public static void printDefaultGuest() {
		System.out.println("Please select from the following options: \n");
		System.out.println("A. Search for a painting");
		System.out.println("B. List available paintings ");
		System.out.println("X. Exit \n");
		System.out.print("Enter your choice: ");
	}

	public static void printDefaultEmployee() {
		System.out.println("Please select from the following options: \n");
		System.out.println("A. Search for a customer");
		System.out.println("B. Display current customers ");
		System.out.println("C. View orders by priority ");
		System.out.println("D. Ship an order ");
		System.out.println("E. List database of products ");
		System.out.println("F. Add a product ");
		System.out.println("G. Remove a product ");
		System.out.println("X. Exit \n");
		System.out.print("Enter your choice: ");
	}

	public static void printDefaultCustomer() {
		System.out.println("Please select from the following options: \n");
		System.out.println("A. Search for a painting");
		System.out.println("B. List available paintings ");
		System.out.println("C. Place an Order ");
		System.out.println("D. View purchases ");
		System.out.println("X. Exit \n");
		System.out.print("Enter your choice: ");
	}
}
