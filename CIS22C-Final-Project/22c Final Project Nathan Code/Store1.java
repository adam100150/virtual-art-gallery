import java.util.*;
import java.io.*;

public class Store1 
{
	private  final int NUM_CUSTOMERS = 7;
	//private  final int NUM_EMPLOYEES = 3;		
	private  final int NUM_ORDERS = 100;
	private PriorityComparator pc;
	
	public static BST<Painting> painting_name;
	public  static BST<Painting> painting_value;
	public  static HashTable<Customer> customers; 
	private Heap<Order> orders; 
	//private HashTable<Employee> employees; ------- I think this should be in our main class
	
	private List<Order> unshippedOrders;
	private List<Order> shippedOrders;
	
	private NameComparator nC;
	private ValueComparator vC;
	
	public Store1()
	{
		 pc = new PriorityComparator();
		 nC = new NameComparator();
		 vC = new ValueComparator();
		 customers = new HashTable<>(NUM_CUSTOMERS);
		 orders = new Heap<>(NUM_ORDERS, pc);
		//employees = new HashTable<>(NUM_EMPLOYEES);
		 
		 try {
	    	  buildPaintings();
	    	  readCustomersFile();
	    	  //staffs();
	      }catch(FileNotFoundException e) {
	    	  System.out.println(e);
	    	  return;
	      }     
		 
	}
	
	public void buildPaintings() throws FileNotFoundException {
		String title, artist, description;
		  double price;
	      int year;
	   
	      File file = new File("painting.txt");
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
	
	public void readCustomersFile() throws FileNotFoundException{
		 
		  String userName, password, firstName, lastName, email, address;
		  double cash;
		     
	      File file = new File("customers.txt");
	      Scanner input = new Scanner(file);
	      while(input.hasNextLine()) {
	    	  userName = input.nextLine();
	    	  firstName = input.next();
	    	  lastName = input.next();
	    	  input.nextLine();   	  
	    	  
	    	  email = input.nextLine();
	    	  password = input.nextLine();
	    	  address = input.nextLine();    	  
	    	  cash = input.nextFloat();
	    	  input.nextLine();

	       	  
	    	  if(input.hasNextLine()) {
	    		  input.nextLine();    		  
	    	  }    	  
	    	  customers.insert(new Customer(userName, password, firstName, lastName, email, address, cash));  
	   
	      }     
	      input.close();	
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
	
	public void displayCustomer(Customer cust)
	{
		System.out.print(customers.get(cust));
	}
	
	public void placeOrder(Order order)
	{
		orders.insert(order);
		orders.buildHeap();
		Customer temp = order.getCustomer();
		Painting tempPainting = order.getOrderContents();
		Double price = tempPainting.getPrice();
		temp.updateCash(-price);
		
		unshippedOrders.addLast(order);
	}
	
	public void shipOrder()
	{
		Order currentOrder = orders.pop();
		Customer currentCust = currentOrder.getCustomer();
		currentCust.addPainting(currentOrder.getOrderContents());
		
		shippedOrders.addLast(currentOrder);
		unshippedOrders.removeFirst();
	}
	
	public void viewUnshippedOrders()
	{
		unshippedOrders.printNumberedList();
	}
	
	public void viewShippedOrders()
	{
		shippedOrders.printNumberedList();
	}
	
	public void addPainting(Painting painting)
	{
	  painting_name.insert(painting, nC);  
   	  painting_value.insert(painting, vC);
	}
	
	public void removePainting(Painting painting)
	{
		painting_name.remove(painting, nC);
	   	painting_value.remove(painting, vC);

	}
}
