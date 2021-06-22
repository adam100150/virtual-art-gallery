import java.io.*;
import java.util.*;
import java.text.*;

/**
 * CustomerInterface.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

public class CustomerInterface {

	public static void main(String[] args) 
	{
		final int NUM_EMPLOYEES = 3;	
	    HashTable<Employee> employees = new HashTable<>(NUM_EMPLOYEES);
		Store store = new Store();
		
		String userName, password, firstName, lastName, email, address;
		try {
	      File file = new File("Employees.txt");
	      Scanner eInput = new Scanner(file);
	      while(eInput.hasNextLine()) {
	    	  userName = eInput.nextLine();
	    	  password = eInput.nextLine();
	    	  firstName = eInput.nextLine();
	    	  lastName = eInput.nextLine();
	  
	    	  if(eInput.hasNextLine()) {
	    		  eInput.nextLine();    		  
	    	  }  	  
	    	  employees.insert(new Employee(userName, password, firstName, lastName));  
	      }     
	      eInput.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Could not find file.");
			e.printStackTrace();
		}
		
		int intInput;
		char userInput;
		String stringInput = "";
		Customer currentCustomer;
		Employee currentEmployee;
		Painting currentPainting;
		Order currentOrder;
		double cash;
		String fileName = "";
		
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to One-To-One Painting Replicas! \n");
		
		System.out.println("Would you like to login as a Customer or an Employee?");
		System.out.println("(1) Customer");
		System.out.println("(2) Employee");
		intInput = input.nextInt();
		input.nextLine();
		
		if(intInput == 1)
		{
			System.out.println("Please enter your login info. If logging in as a guest, please create an account. ");
			System.out.print("Username: ");
			userName = input.nextLine();
			System.out.print("\nPassword: ");
			password = input.nextLine();
			currentCustomer = new Customer(userName, password);
			if(store.containsCustomer(currentCustomer))
			{
				currentCustomer = store.searchCustomer(currentCustomer);
				System.out.println("\nWelcome, " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + "!\n\n");
			}
			else
			{
				System.out.println("We don't have your account on file...\n");
				System.out.println("Let's create an account for you!");
				System.out.print("Enter your first name: ");
				firstName = input.nextLine();			
				System.out.print("Enter your last name: ");
				lastName = input.nextLine();
				System.out.print("Enter your Email: ");
				email = input.nextLine();
				System.out.print("Enter your address: ");
				address = input.nextLine();
				System.out.print("Enter the amount of cash to fund your account: ");
				cash = input.nextDouble();
				input.nextLine();
				System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
				currentCustomer.setFirstName(firstName);
				currentCustomer.setLastName(lastName);
				currentCustomer.setEmail(email);
				currentCustomer.setAddress(address);
				currentCustomer.setCash(cash);
				store.addCustomer(currentCustomer);
				fileName = "Customers.txt";
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
					PrintWriter out = new PrintWriter(writer);
					out.println();
					out.println(userName);
					out.println(password);
					out.println(firstName + " " + lastName);
					out.println(email);
					out.println(address);
					out.print((int)cash);
					out.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
				printDefaultCustomer();
				userInput = input.next().charAt(0);
				
				while(userInput != 'X'){
					if (userInput == 'A')
					{
						System.out.println("Would you like to search for a painting by Title or by price?");
						System.out.println("(1) Title");
						System.out.println("(2) Price");
						intInput = input.nextInt();
						input.nextLine();
						
						if(intInput == 1)
						{
							System.out.println("Enter the name of the painting you are looking for.");
							stringInput = input.nextLine();
							currentPainting = store.searchPaintingName(new Painting(stringInput));
							if(currentPainting != null)
							{
								System.out.println(currentPainting);
							}
							else
							{
								System.out.println("Sorry we do not have that painting at the moment.");
							}
						}
						else if(intInput == 2)
						{
							System.out.println("Enter the price of the painting you are looking for.");
							cash = input.nextDouble();
							input.nextLine();
							currentPainting = store.searchPaintingPrice(new Painting(cash));
							if(currentPainting != null)
							{
								System.out.println(currentPainting);
							}
							else
							{
								System.out.println("Sorry we do not have that painting at the moment.");
							}
						}
						else
						{
							System.out.println("Invalid choice.");
						}
					}
					else if(userInput == 'B')
					{
						System.out.println("Would you like to view our paintings ordered by Title or by Price?");
						System.out.println("(1) Title");
						System.out.println("(2) Price");
						intInput = input.nextInt();
						input.nextLine();
						
						if(intInput == 1)
						{
							store.printPaintingsByName();
						}
						else if(intInput == 2)
						{
							store.printPaintingsByValue();
						}
						else
						{
							System.out.println("Invalid choice.");
						}
					}
					else if(userInput == 'C')
					{
						System.out.println("What is the title of the painting that you would like to purchase?");
						stringInput = input.nextLine();
						stringInput = input.nextLine();
						currentPainting = store.searchPaintingName(new Painting(stringInput));
						if(currentPainting != null)
						{
							System.out.println("The painting you are ordering is: ");
							System.out.println(currentPainting);
							System.out.println("What kind of shipping would you like?");
							System.out.println("(1) Standard");
							System.out.println("(2) Rush");
							System.out.println("(3) Overnight");
							intInput = input.nextInt();
							input.nextLine();
							String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
							if(intInput == 1)
							{
								System.out.println("Painting ordered! You can expect your painting within the next 5-10 business days.\n");
								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, intInput);
								store.placeOrder(currentOrder);
							}
							else if(intInput == 2)
							{
								System.out.println("Painting ordered! You can expect your painting within the next 2-3 business days.\n");
								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, intInput);
								store.placeOrder(currentOrder);
							}
							else if(intInput == 3)
							{
								System.out.println("Painting ordered! You can expect your painting to be shipped overnight.\n");
								currentOrder = new Order(currentCustomer, timeStamp, currentPainting, intInput);
								store.placeOrder(currentOrder);
							}
							fileName = "Orders.txt";
							try {
								BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
								PrintWriter out = new PrintWriter(writer);
								out.println();
								out.println(userName);
								out.println(password);
								out.println(timeStamp);
								out.println(currentPainting.getTitle());
								out.print(intInput);
								out.close();
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							System.out.println("Sorry we do not have that painting at the moment.");
						}
					}
					else if(userInput == 'D')
					{
						System.out.println("Would you like to view your shipped or unshipped orders?");
						System.out.println("(1) Shipped");
						System.out.println("(2) Unshipped");
						intInput = input.nextInt();
						input.nextLine();
						
						if(intInput == 1)
						{
							store.viewShippedOrders(currentCustomer);
							System.out.println();
						}
						else if(intInput == 2)
						{
							store.viewUnshippedOrders(currentCustomer);
							System.out.println();
						}
						else
						{
							System.out.println("Invalid choice.");
						}
					}
					else
					{
						System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
					}
					printDefaultCustomer();
					userInput = input.next().charAt(0);
				}
				System.out.println("\nGoodbye!");
				input.close();
		}
		else if(intInput == 2)
		{
			System.out.println("Please enter your login info.");
			System.out.print("Username: ");
			userName = input.nextLine();
			System.out.print("\nPassword: ");
			password = input.nextLine();
			currentEmployee = new Employee(userName, password);
			if(employees.contains(currentEmployee))
			{
				currentEmployee = employees.get(currentEmployee);
				System.out.println("\nWelcome, " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName() + "!\n\n");
				
				printDefaultEmployee();
				userInput = input.next().charAt(0);
				while(userInput != 'X'){
					if (userInput == 'A')
					{
						input.nextLine();
						System.out.println("\nEnter the name of the customer you would like to search for:");
						System.out.println("\nFirst name: ");
						firstName = input.nextLine();
						System.out.println("Last name: ");
						lastName = input.nextLine();
						System.out.println("Enter the username of the customer: ");
						userName = input.nextLine();
						System.out.println("Enter the password of the customer: ");
						password = input.nextLine();
						currentCustomer = new Customer(userName, password);
						currentCustomer = store.searchCustomer(currentCustomer);
						if(currentCustomer == null)
						{
							System.out.println("Could not find that customer.\n");
						}
						else
							System.out.println(currentCustomer);
					}
					else if(userInput == 'B')
					{
						store.displayCustomers();
					}
					else if(userInput == 'C')
					{
						store.viewUnshippedOrders();
					}
					else if(userInput == 'E')
					{
						System.out.println("Would you like to see the products listed by Title or price?");
						System.out.println("(1) Title");
						System.out.println("(2) Price");
						intInput = input.nextInt();
						input.nextLine();
						if(intInput == 1)
						{
							store.printPaintingsByName();
						}
						else if (intInput == 2)
						{
							store.printPaintingsByValue();
						}
						else
						{
							System.out.println("Invalid choice.");
						}
					}
					else if(userInput == 'D')
					{
						store.shipOrder();
					}
					else if(userInput == 'F')
					{
						input.nextLine();
						String title, artist, description;
						int year;
						double price;
						System.out.println("Wonderful! A new painting to the collection.");
						System.out.println("What is the name of the painting?");
						title = input.nextLine();
						System.out.println("Who is the artist?");
						artist = input.nextLine();
						System.out.println("What year was this masterpeice created?");
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
						
						fileName = "Paintings.txt";
						try {
							BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
							PrintWriter out = new PrintWriter(writer);
							out.println();
							out.println();
							out.println(title);
							out.println(artist);
							out.println(year);
							out.println(price);
							out.print(description);
							out.close();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
					else if(userInput == 'G')
					{
						input.nextLine();
						System.out.println("What is the name of the painting to be removed?");
						String title = input.nextLine();
						currentPainting = new Painting(title);
						currentPainting = store.searchPaintingName(currentPainting);
						
						store.removePainting(currentPainting);
						
						fileName = "Paintings.txt";
						File tempFile = new File("temptxtfile.txt");
						try 
						{	
						BufferedReader reader = new BufferedReader(new FileReader(fileName));
						BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
						PrintWriter out = new PrintWriter(writer);
						String currentLine;
						
						while((currentLine = reader.readLine()) != null)
						 {
							if(currentLine.equals(title) || currentLine.equals(currentPainting.getArtist()) || currentLine.equals(String.valueOf(currentPainting.getYear())) || currentLine.equals(String.valueOf(currentPainting.getPrice())) || currentLine.equals(currentPainting.getDescription()))
							{
								reader.readLine();
							}
							else
							{
								out.println(currentLine);
							}
						 }
						File actualFile = new File(fileName);
						tempFile.renameTo(actualFile);
						actualFile.delete();
						reader.close();
						out.close();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
					}
					printDefaultEmployee();
					userInput = input.next().charAt(0);
				}
				System.out.println("\nGoodbye!");
				input.close();
			}
			else
			{
				System.out.println("Invalid login. Goodybe.");
			}
		}
		
	}
	
	public static void printDefaultEmployee()
	{
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
	
	public static void printDefaultCustomer()
	{
		System.out.println("Please select from the following options: \n");
		System.out.println("A. Search for a painting");
		System.out.println("B. List available paintings ");
		System.out.println("C. Place an Order ");
		System.out.println("D. View purchases ");
		System.out.println("X. Exit \n");
		System.out.print("Enter your choice: ");
	}
}