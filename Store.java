/**
 * Store.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
import java.util.*;
import java.io.*;

public class Store
{
	private  final int NUM_CUSTOMERS = 7;
	private  final int NUM_ORDERS = 100;
	private PriorityComparator pc;

	private static BST<Painting> painting_name = new BST<>();
	private static BST<Painting> painting_value = new BST<>();

	HashMap<String, Customer> customers;
	HashMap<String, Employee> employees;

	private Heap<Order> ordersStandard;
	private Heap<Order> ordersRushed;
	private Heap<Order> ordersOvernight;

	private List<Order> unshippedOrders;
	private List<Order> shippedOrders;

	private NameComparator nC;
	private ValueComparator vC;

	public Store()
	{
		pc = new PriorityComparator();
		nC = new NameComparator();
		vC = new ValueComparator();
		customers = new HashMap<>();
		employees = new HashMap<>();
		ordersStandard = new Heap<>(NUM_ORDERS, pc);
		ordersRushed = new Heap<>(NUM_ORDERS, pc);
		ordersOvernight = new Heap<>(NUM_ORDERS, pc);

		unshippedOrders = new List<>();
		shippedOrders = new List<>();

		try {
			buildPaintings();
			readCustomersFile();
			readEmployeesFile();
			buildOrders();
		}catch(FileNotFoundException e) {
			System.out.println("Could not find file.");
			e.printStackTrace();
		}
	}

	public void buildPaintings() throws FileNotFoundException {
		String title, artist, description;
		double price;
		int year;

		File file = new File("Paintings.txt");
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

		File file = new File("Customers.txt");
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

		File file = new File("Employees.txt");
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

	public void buildOrders() throws FileNotFoundException {
		String user, password, painting, date;
		int speed;
		boolean ship;

		File file = new File("Orders.txt");
		Scanner input = new Scanner(file);
		while(input.hasNextLine())
		{
			date = input.nextLine();
			user = input.nextLine();
			password = input.nextLine();
			painting = input.nextLine();
			speed = input.nextInt();
			ship = input.nextBoolean();
			if(input.hasNextLine())
				input.nextLine();
			Customer tempCust = customers.get(user);
			Painting tempPaint = painting_name.search(new Painting(painting), nC);
			Order tempOrder = new Order(tempCust, date, tempPaint, speed,ship);
			tempCust.addOrder(tempOrder);
			
			if(speed == 1 && ship!= true)
				ordersStandard.insert(tempOrder);
			else if(speed == 2 && ship!= true)
				ordersRushed.insert(tempOrder);
			else if(speed == 3 && ship!= true)
				ordersOvernight.insert(tempOrder);
			
			if(ship == true)
				shippedOrders.addLast(tempOrder);
			else
				unshippedOrders.addLast(tempOrder);
		}
		input.close();
	}
	public Painting searchPaintingName(Painting painting)
	{
		return painting_name.search(painting, nC);
	}
	public Painting searchPaintingPrice(Painting painting)
	{
		return painting_name.search(painting, vC);
	}
	public void printPaintingsByName()
	{
		painting_name.inOrderPrint();
	}

	public void printPaintingsByValue()
	{
		painting_value.inOrderPrint();
	}

	public Customer searchCustomer(Customer cust)
	{
		return customers.get(cust);
	}
	public boolean containsCustomer(String username)
	{
		return customers.containsKey(username);
	}
	public void displayCustomer(Customer cust)
	{
		System.out.print(customers.get(cust.getUserName()));
	}
	public void displayCustomers()
	{
		System.out.println(customers);
	}
	public void addCustomer(Customer cust) {
		customers.putIfAbsent(cust.getUserName(), cust);
	}

	Customer getCustomer(String username) {
		return customers.get(username);
	}

	public void placeOrder(Order order)
	{
		Customer temp = order.getCustomer();
			if(order.getShippingSpeed() == 3)
			{
				ordersOvernight.insert(order);
			}
			else if(order.getShippingSpeed() == 2)
			{
				ordersRushed.insert(order);
			}
			else if(order.getShippingSpeed() == 1)
			{
				ordersStandard.insert(order);
			}

			Painting tempPainting = order.getOrderContents();
			temp.addPainting(tempPainting);
			Double price = tempPainting.getPrice();
			temp.updateCash(-price);
			unshippedOrders.addLast(order);
			temp.addOrder(order);
			
			String fileName = "Customers.txt";
			File tempFile = new File("tempfileC.txt");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				PrintWriter out = new PrintWriter(writer);
				String currentLine;

				while ((currentLine = reader.readLine()) != null) {
					if (currentLine.equals(temp.getUserName())) {
						out.println(currentLine);
						currentLine = reader.readLine();
						out.println(currentLine);
						currentLine = reader.readLine();
						out.println(currentLine);
						currentLine = reader.readLine();
						out.println(currentLine);
						currentLine = reader.readLine();
						out.println(currentLine);
						currentLine = reader.readLine();
						out.println(temp.getCash());
					} else {
						out.println(currentLine);
					}
				}
				reader.close();
				out.close();

				reader = new BufferedReader(new FileReader(tempFile));
				writer = new BufferedWriter(new FileWriter(fileName));
				out = new PrintWriter(writer);

				while ((currentLine = reader.readLine()) != null) {
					out.println(currentLine);
				}
				reader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	public void shipOrder()
	{
		if(!ordersOvernight.isEmpty())
		{
			Order currentOrder = ordersOvernight.pop();
			Customer currentCust = currentOrder.getCustomer();
			currentCust.addPainting(currentOrder.getOrderContents());
			System.out.println("\nOrder Shipped: " + currentOrder);
			shippedOrders.addLast(currentOrder);
			int index = unshippedOrders.linearSearch(currentOrder);
			unshippedOrders.iteratorToIndex(index);
			unshippedOrders.removeIterator();
			currentOrder.ship();
			
			String fileName = "Orders.txt";
			File tempFile = new File("tempfile.txt");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				PrintWriter out = new PrintWriter(writer);
				String currentLine;

				while ((currentLine = reader.readLine()) != null) {
					if (currentLine.equals(currentOrder.getDate())) {
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
					} else {
						out.println(currentLine);
					}
				}
				reader.close();
				out.close();

				reader = new BufferedReader(new FileReader(tempFile));
				writer = new BufferedWriter(new FileWriter(fileName));
				out = new PrintWriter(writer);

				while ((currentLine = reader.readLine()) != null) {
					out.println(currentLine);
				}
				reader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileName = "Orders.txt";
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
				PrintWriter out = new PrintWriter(writer);
				out.println(currentOrder.getDate());
				out.println(currentCust.getUserName());
				out.println(currentCust.getPassword());
				out.println(currentOrder.getOrderContents().getTitle());
				out.println(currentOrder.getShippingSpeed());
				out.print(currentOrder.isShipped());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(!ordersRushed.isEmpty())
		{
			Order currentOrder = ordersRushed.pop();
			Customer currentCust = currentOrder.getCustomer();
			currentCust.addPainting(currentOrder.getOrderContents());
			System.out.println("\nOrder Shipped: " + currentOrder);
			shippedOrders.addLast(currentOrder);
			int index = unshippedOrders.linearSearch(currentOrder);
			unshippedOrders.iteratorToIndex(index);
			unshippedOrders.removeIterator();
			currentOrder.ship();
			
			String fileName = "Orders.txt";
			File tempFile = new File("tempfile.txt");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				PrintWriter out = new PrintWriter(writer);
				String currentLine;

				while ((currentLine = reader.readLine()) != null) {
					if (currentLine.equals(currentOrder.getDate())) {
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
					} else {
						out.println(currentLine);
					}
				}
				reader.close();
				out.close();

				reader = new BufferedReader(new FileReader(tempFile));
				writer = new BufferedWriter(new FileWriter(fileName));
				out = new PrintWriter(writer);

				while ((currentLine = reader.readLine()) != null) {
					out.println(currentLine);
				}
				reader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileName = "Orders.txt";
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
				PrintWriter out = new PrintWriter(writer);
				out.println(currentOrder.getDate());
				out.println(currentCust.getUserName());
				out.println(currentCust.getPassword());
				out.println(currentOrder.getOrderContents().getTitle());
				out.println(currentOrder.getShippingSpeed());
				out.print(currentOrder.isShipped());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(!ordersStandard.isEmpty())
		{
			Order currentOrder = ordersStandard.pop();
			Customer currentCust = currentOrder.getCustomer();
			currentCust.addPainting(currentOrder.getOrderContents());
			System.out.println("\nOrder Shipped: " + currentOrder);
			shippedOrders.addLast(currentOrder);
			int index = unshippedOrders.linearSearch(currentOrder);
			unshippedOrders.iteratorToIndex(index);
			unshippedOrders.removeIterator();
			currentOrder.ship();
			
			String fileName = "Orders.txt";
			File tempFile = new File("tempfile.txt");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				PrintWriter out = new PrintWriter(writer);
				String currentLine;

				while ((currentLine = reader.readLine()) != null) {
					if (currentLine.equals(currentOrder.getDate())) {
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
						reader.readLine();
					} else {
						out.println(currentLine);
					}
				}
				reader.close();
				out.close();

				reader = new BufferedReader(new FileReader(tempFile));
				writer = new BufferedWriter(new FileWriter(fileName));
				out = new PrintWriter(writer);

				while ((currentLine = reader.readLine()) != null) {
					out.println(currentLine);
				}
				reader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileName = "Orders.txt";
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
				PrintWriter out = new PrintWriter(writer);
				out.println(currentOrder.getDate());
				out.println(currentCust.getUserName());
				out.println(currentCust.getPassword());
				out.println(currentOrder.getOrderContents().getTitle());
				out.println(currentOrder.getShippingSpeed());
				out.print(currentOrder.isShipped());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("No orders to ship.");
	}

	public void viewUnshippedOrders()
	{
		if(unshippedOrders.isEmpty())
			System.out.println("No orders to show.");
		else
		{
			ArrayList<Order> list1 = ordersOvernight.sort();
			ArrayList<Order> list2 = ordersRushed.sort();
			ArrayList<Order> list3 = ordersStandard.sort();
			list1.addAll(list2);
			list1.addAll(list3);
			System.out.println(list1);
		}
	}

	public void viewShippedOrders()
	{
		if(shippedOrders.isEmpty())
			System.out.println("No orders to show.");
		else
			shippedOrders.printNumberedList();
	}

	public void viewUnshippedOrders(Customer cust)
	{
		if(unshippedOrders.isEmpty())
		{
			System.out.println("No orders to show.");
		}
		else
		{
			List<Order> temp = new List<>(); //temp is the list of orders the Customer placed
			unshippedOrders.placeIterator();
			for(int i = 1; i <=unshippedOrders.getLength(); i++)
			{
				Order tempOrder = unshippedOrders.getIterator();
				if(tempOrder.getCustomer().equals(cust))
					temp.addLast(tempOrder);
				unshippedOrders.advanceIterator();
			}
			temp.printNumberedList();
		}
	}

	public void viewShippedOrders(Customer cust)
	{
		if(shippedOrders.isEmpty())
			System.out.println("No orders to show.");
		else
		{
			List<Order> temp = new List<>();
			shippedOrders.placeIterator();
			for(int i = 1; i <= shippedOrders.getLength(); i++)
			{
				Order tempOrder = shippedOrders.getIterator();
				if(tempOrder.getCustomer().equals(cust))
					temp.addLast(tempOrder);
				shippedOrders.advanceIterator();
			}
			if(temp.isEmpty())
				System.out.println("No shipped orders.");
			else
				temp.printNumberedList();
		}
	}
	public void addPainting(Painting painting)
	{
		painting_name.insert(painting, nC);
		painting_value.insert(painting, vC);
	}

	public void removePainting(Painting painting)
	{
		System.out.println("Painting removed: " + painting);
		painting_name.remove(painting, nC);
		painting_value.remove(painting, vC);

	}
}