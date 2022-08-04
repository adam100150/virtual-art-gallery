/**
 * Store.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package src;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Store {
	private  final int NUM_ORDERS = 100;
	private PriorityComparator pc;
	private NameComparator nC;
	private ValueComparator vC;

	static BST<Painting> painting_name = new BST<>();
	static BST<Painting> painting_value = new BST<>();

	HashMap<String, Customer> customers;
	HashMap<String, Employee> employees;

	static Heap<Order> orders;

	private List<Order> shippedOrders;

	public Store() {
		pc = new PriorityComparator();
		nC = new NameComparator();
		vC = new ValueComparator();
		customers = new HashMap<>();
		employees = new HashMap<>();
		orders = new Heap<>(NUM_ORDERS, pc);
		shippedOrders = new List<>();

		try {
			buildPaintings();
			readCustomersFile();
			readEmployeesFile();
			buildOrders();
		} catch(FileNotFoundException e) {
			System.out.println("Could not find file.");
			e.printStackTrace();
		}
	}

	void buildPaintings() throws FileNotFoundException {
		String title, artist, description;
		double price;
		int year;

		File file = new File("src/text-files/Paintings.txt");
		Scanner input = new Scanner(file);
		while(input.hasNextLine()) {
			title = input.nextLine();
			artist = input.nextLine();
			year = input.nextInt();
			input.nextLine();
			price = input.nextFloat();
			input.nextLine();
			description = input.nextLine();
			if(input.hasNextLine()) {
				input.nextLine();
			}
			painting_name.insert(new Painting(title, artist, year, price, description), nC);
			painting_value.insert(new Painting(title, artist, year, price, description), vC);
		}
		input.close();
	}

	void readCustomersFile() throws FileNotFoundException{
		String userName, password, firstName, lastName, email, address;
		double cash;

		File file = new File("src/text-files/Customers.txt");
		Scanner input = new Scanner(file);
		while(input.hasNextLine()) {
			userName = input.nextLine();
			password = input.nextLine();
			firstName = input.next();
			lastName = input.next();
			input.nextLine();

			email = input.nextLine();
			address = input.nextLine();
			cash = input.nextDouble();


			if(input.hasNextLine()) {
				input.nextLine();
			}
			customers.putIfAbsent(userName, new Customer(userName, password, firstName, lastName, email, address, cash));
		}
		input.close();
	}

	void readEmployeesFile() throws FileNotFoundException{
		String username, password, firstName, lastName;

		File file = new File("src/text-files/Employees.txt");
		Scanner input = new Scanner(file);
		while(input.hasNextLine()) {
			username = input.nextLine();
			password = input.nextLine();
			firstName = input.nextLine();
			lastName = input.nextLine();

			if(input.hasNextLine()) {
				input.nextLine();
			}

			employees.putIfAbsent(username, new Employee(username, password, firstName, lastName));
		}
		input.close();
	}

	void buildOrders() throws FileNotFoundException, IllegalArgumentException {
		String user, painting, date;
		Shipping speed;
		boolean ship;

		File file = new File("src/text-files/Orders.txt");
		Scanner input = new Scanner(file);
		while(input.hasNextLine()) {
			date = input.nextLine();
			user = input.nextLine();
			painting = input.nextLine();
			speed = Shipping.valueOf(input.nextLine());
			ship = input.nextBoolean();
			if(input.hasNextLine())
				input.nextLine();

			Customer tempCust = customers.get(user);
			Painting tempPaint = painting_name.search(new Painting(painting), nC);
			Order tempOrder = new Order(tempCust, date, tempPaint, speed,ship);

			if(ship) {
				shippedOrders.addLast(tempOrder);
			} else {
				orders.insert(tempOrder);
			}
		}
		input.close();
	}

	Painting searchPaintingName(String name) {
		Painting currPainting = new Painting(name);
		return painting_name.search(currPainting, nC);
	}

	Painting searchPaintingPrice(double price) {
		Painting currPainting = new Painting(price);
		return painting_name.search(currPainting, vC);
	}

	void printPaintingsByName() {
		painting_name.inOrderPrint();
	}

	void printPaintingsByValue() {
		painting_value.inOrderPrint();
	}

	void placeOrder(Order order) {
		orders.insert(order);

		Customer orderCustomer  = order.getCustomer();
		orderCustomer.updateCash(-order.getOrderContents().getPrice());

		String fileName = "src/test-files/Orders.txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
			PrintWriter out = new PrintWriter(writer);
			out.println();
			out.println(order.getShippingDate());
			out.println(order.getCustomer().userName);
			out.println(order.getOrderContents().getTitle());
			out.println(order.shippingSpeed);
			out.println(order.shipped);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void shipOrder() {
		Order currOrder = orders.pop();
		shippedOrders.addLast(currOrder);
		currOrder.shipped = true;

		Customer currCustomer = currOrder.getCustomer();
		Painting currPainting = currOrder.getOrderContents();
		currPainting.owner = currCustomer;

		String filename = "src/text-files/Orders.txt";
		String tempFilename = "src/text-files/TempOrders.txt";
		File orderFile = new File(filename);
		File tempOrderFile = new File(tempFilename);

		/*
			This segment of the code copies the file contents to a temporary file and modifies only
			the required lines
		 */

		try {
			tempOrderFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Scanner input = new Scanner(orderFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilename, false));
			PrintWriter out = new PrintWriter(writer);
			String date;
			while (input.hasNextLine()) {
				date = input.nextLine();
				out.println(date);
				Date currDate = null;
				try {
					currDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").parse(date);
				} catch (ParseException p) {
					p.printStackTrace();
				}
				if (currOrder.getShippingDate().equals(currDate)) {
					for (int i = 0; i < Order.NUM_LINES_OF_ORDER - 1; i++) { // Copy the order as it is
						out.println(input.nextLine());
					}
					out.println(true);  // Change the shipped info to true
					input.nextLine();
				} else { // Copy the order as it is to the temp file
					for (int i = 0; i < Order.NUM_LINES_OF_ORDER; i++) {
						out.println(input.nextLine());
					}
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Copy the correct contents from the temporary file to the Orders file
		try {
			String correctContent = Utils.readContentsAsString(tempOrderFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
			PrintWriter out = new PrintWriter(writer);
			out.print(correctContent);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tempOrderFile.delete();
	}

	public void viewUnshippedOrders() {
		for (var ord : orders) {
			Order order = (Order) ord;
			if (!order.shipped) {
				System.out.println(order);
			}
		}
	}

	public void viewShippedOrders()
	{
		for (var ord : orders) {
			Order order = (Order) ord;
			if (order.shipped) {
				System.out.println(order);
			}
		}
	}

	public void viewUnshippedOrders(Customer cust) {
		for (var ord : orders) {
			Order order = (Order) ord;
			if (!order.shipped && order.getCustomer().equals(cust)) {
				System.out.println(order);
			}
		}
	}

	public void viewShippedOrders(Customer cust)
	{
		for (var ord : orders) {
			Order order = (Order) ord;
			if (order.shipped && order.getCustomer().equals(cust)) {
				System.out.println(order);
			}
		}
	}
	public void addPainting(Painting painting)
	{
		painting_name.insert(painting, nC);
		painting_value.insert(painting, vC);
	}

	//TODO: Add painting to painting database

	public void removePainting(Painting painting) throws ArtGalleryException {
		System.out.println("Painting removed: " + painting);
		if (painting_name.search(painting, nC) == null) {
			throw new ArtGalleryException("Painting not found");
		}
		painting_name.remove(painting, nC);
		painting_value.remove(painting, vC);
	}

	//TODO: Remove painting from database
}