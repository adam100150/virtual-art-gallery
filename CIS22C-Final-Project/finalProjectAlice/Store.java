/**
 * Store.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */

package finalProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
	private HashTable<Painting> paintings;		
	private HashTable<Customer> customers;  
	private HashTable<Employee> employees;
	private Heap<Order> orders;
	
	final int NUM_PAINTINGS = 10;
	final int NUM_CUSTOMERS = 7;
	final int NUM_EMPLOYEES = 3;		
	final int NUM_ORDERS = 100;

	public Store() {		
		paintings = new HashTable<>(NUM_PAINTINGS);		
		customers = new HashTable<>(NUM_CUSTOMERS);
		employees = new HashTable<>(NUM_EMPLOYEES);
		
		PriorityComparator c = new PriorityComparator(); // 
		orders = new Heap<>(100, c);		
	      
	      try {
	    	  availablePainting();
	    	  readCustomersFile();
	    	  staffs();
	      }catch(FileNotFoundException e) {
	    	  System.out.println(e);
	    	  return;
	      }     
	      login();
	   }
	
	public HashTable<Customer> getCustomers(){
		return customers;
	}
	
	public Heap<Order> getOrders(){
		return orders;
	}
			
	public void availablePainting() throws FileNotFoundException {
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
	    	  paintings.insert(new Painting(title, artist, year, price, description));  
	      }     
	      input.close();		
	}
	
	 public void readCustomersFile() throws FileNotFoundException{		 
		  String userName, password, firstName, lastName, email, address;
		  double cash;
		     
	      File file = new File("customer.txt");
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
	    	  if(input.hasNextLine()) {
	    		  input.nextLine();    		  
	    	  }   
	    	  if(input.hasNextLine()) {
	    		  input.nextLine();    		  
	    	  }    	  
	    	  customers.insert(new Customer(userName, password, firstName, lastName, email, address, cash));  
	      }     
	      input.close();	
	 }
	 
		public void staffs() throws FileNotFoundException {
			String userName, password, firstName, lastName;
			
		      File file = new File("employee.txt");
		      Scanner input = new Scanner(file);
		      while(input.hasNextLine()) {
		    	  userName = input.nextLine();
		    	  password = input.nextLine();
		    	  firstName = input.nextLine();
		    	  lastName = input.nextLine();
		  
		    	  if(input.hasNextLine()) {
		    		  input.nextLine();    		  
		    	  }  	  
		    	  employees.insert(new Employee(userName, password, firstName, lastName));  
		      }     
		      input.close();	
		}
		
		public void login() {
			char choice;
			Scanner userInput = new Scanner(System.in);
			
			System.out.print("Choose: 1:login customer 2: login employee 3: quit ");
			choice = userInput.next().charAt(0);
			if(choice == '1') {
				loginCustomer();
			}
			if(choice == '2') {
				loginEmployee();
			}
			if(choice == '3') {
				System.out.println("\nGoodbye");
			}
		}
		
		public void loginCustomer() {
			String firstName, lastName, email, address;
			double cash;
	
			Customer newCustomer;
			Scanner userInput = new Scanner(System.in);
			System.out.println("\nWelcome to Art Gallery!\n");
			System.out.print("Please enter your user name: ");
			String userName = userInput.nextLine();
			System.out.print("Please enter your password: ");
			String password = userInput.nextLine();
			System.out.println();			   
			 
			newCustomer = new Customer(userName, password);
					   
		   if(!customers.contains(newCustomer)) {
				   System.out.println("We don't have your account on file...\n");
				   System.out.println("Let's create an account for you!");
				   System.out.print("Enter your first name: ");
				   firstName = userInput.nextLine();
				   System.out.print("Enter your last name: ");
				   lastName = userInput.nextLine();
				   System.out.print("Enter your email: ");
				   email = userInput.nextLine();
				   System.out.print("Enter your address: ");
				   address = userInput.nextLine();
				   System.out.print("Enter your cash: ");
				   cash = userInput.nextFloat();
				   userInput.nextLine();
				   				  				   
				   newCustomer = new Customer(userName, password, firstName, lastName, 
											  email, address, cash);
				   customers.insert(newCustomer);		   
			   }
			   else {
				   newCustomer = customers.get(newCustomer);
				   firstName = newCustomer.getFirstName();
				   lastName = newCustomer.getLastName();	   
			   }
			   System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
			   customerMenu(newCustomer);			
		}
		
		public void loginEmployee() {
			Scanner userInput = new Scanner(System.in);
			System.out.println("Welcome to the Art Gallery!\n");
			   System.out.print("Please enter your user name: ");
			   String userName = userInput.nextLine();
			   System.out.print("Please enter your password: ");
			   String password = userInput.nextLine();
			   System.out.println();
			   
			   Employee newEmployee;
			   newEmployee = new Employee(userName, password);
			   newEmployee = employees.get(newEmployee);
			   String firstName, lastName;
			   
			   if(newEmployee == null) {
				   System.out.println("We don't have your account on file...\n");
				   System.out.println("Let's create an account for you!");
				   System.out.print("Enter your first name: ");
				   firstName = userInput.nextLine();
				   System.out.print("Enter your last name: ");
				   lastName = userInput.nextLine();
				   
				  				   
				   newEmployee = new Employee(userName, password, firstName, lastName);
				   employees.insert(newEmployee);		   
			   }
			   else {
				   firstName = newEmployee.getFirstName();
				   lastName = newEmployee.getLastName();	   
			   }
			   System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
			   employeeMenu(newEmployee);									
		}
		
		public void customerMenu(Customer customer) {			
			Scanner userInput = new Scanner(System.in);
				
			char choice;    
				do {
					System.out.println("Please select from the following options:");
					System.out.println();
					System.out.println("A. Purchase a Painting");
			  		System.out.println("B. Add Cash");
			  		System.out.println("C. Display Your Current Paintings by title");
			  		System.out.println("D. Display Your Current Paintings by value");
			  		System.out.println("E. View Purchases");
			  		System.out.println("X. Exit\n");
			  		
			  		System.out.print("Enter your choice: ");
			  				  
			  		choice = userInput.next().charAt(0);
			  		
			  		if(choice == 'A' || choice == 'a') {			  			
			  			customerChoiceA(customer);
			  		}
			  		
			  		else if(choice == 'B'|| choice == 'b') {
			  			String strDouble = String.format("%.2f", customer.getCash());
			  		    System.out.println("\nYour current cash balance is $" + strDouble + "\n");
			  			customerChoiceB(customer);
			  		}			  

			  		else if(choice == 'C'|| choice == 'c') {
			  			System.out.println("\nDisplay your puchased painting by title: ");
			  			if(!customer.hasPainting()) {
			  				System.out.println("\nYou don't have any painting!\n");
			  			}
			  			else {
			  				customer.printPaintingByTitle();
			  			}
			  		}
			  		
			  		else if(choice == 'D'|| choice == 'd') {
			  			System.out.println("\nDisplay your puchased painting by value: ");
			  			if(!customer.hasPainting()) {
			  				System.out.println("\nYou don't have any painting!\n");
			  			}
			  			else {
			  			customer.printPaintingByValue();
			  			}
			  		}
			  		
			  		else if(choice == 'E'|| choice == 'e') {
			  			customerChoiceE(customer);
			  		}
			  		
			  		else if(choice == 'X'|| choice == 'x') {
			  			System.out.println("\nGoodbye dear customer " + customer.getFirstName() + " !");
			  		}
			  		else {
			  			System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
			  		}
				}while(!(choice == 'X' || choice == 'x')); 
				login();
		}
					  						
		public void customerChoiceA(Customer customer) {
			Order order;
			String date;
			int choooseShippingSpeed;
			
			Scanner userInput = new Scanner(System.in);
			System.out.println("\nPlease select from the options below:\n");
			   
			paintings.printTable();	   
			System.out.print("\nEnter the title of the painting to purchase: ");
			String title = userInput.nextLine();
			System.out.print("\nEnter the artist of the painting to purchase: ");
			String artist = userInput.nextLine();

			Painting painting = new Painting(title, artist); // hashcode key (title + artist)			
			painting = paintings.get(painting);
			if(painting == null) {
				System.out.println("Painting does not exist!");
				return;
			}
			order = customer.placeOrder(painting);
			customer.updateCash(-painting.getPrice());
			
			System.out.println("\nYou successfully added the following painting to your order:\n");
			System.out.println(painting);

			System.out.println("Select shipping type from 1-3: ");
			System.out.println("1: Overnight Shipping");
			System.out.println("2: Rush Shipping");
			System.out.println("3: Standard Shipping");
			System.out.print("\nEnter your choice: ");
			choooseShippingSpeed = userInput.next().charAt(0);
			if(choooseShippingSpeed == '1') {
				order.setPriority(1);
			}
			if(choooseShippingSpeed == '2') {
				order.setPriority(2);
			}	
			if(choooseShippingSpeed == '3') {
				order.setPriority(3);				
			}	
			orders.insert(order); //insert order in the heap
		}
		
		public void customerChoiceB(Customer customer) {
			Scanner userInput = new Scanner(System.in);
			System.out.print("Enter the amount of cash to add: $");
			double nCashToAdd = userInput.nextFloat();
			userInput.nextLine();
			customer.updateCash(nCashToAdd);
			String strDouble = String.format("%.2f", customer.getCash());
			DecimalFormat df = new DecimalFormat("###,##0.00");	   
			if(customer.getCash() < 100) {
				System.out.println("\nYour current cash balance is $" + strDouble + "\n");	 
			  }
			else {	   
				System.out.println("\nYour current cash balance is $" + df.format(customer.getCash()) + "\n");	
			  }  			
		}
		
		public void customerChoiceE(Customer customer) {
			if(!customer.hasPainting()) {
				System.out.println("\nYou do not have any purchase!\n");
				return;
			}
			
			Scanner userInput = new Scanner(System.in);
			System.out.println("\nView Purchases");
			System.out.println("A. View shipped orders");
	  		System.out.println("B. View unshipped orders");
	  		System.out.print("\nEnter your choice: ");
	  		
	  		char choice; 
	  		choice = userInput.next().charAt(0);
	  		boolean shipped = false;
	  		boolean found = false;
			if(choice == 'A' || choice == 'a') {
				shipped = true;
			}
			ArrayList<Order> data = orders.getData();	
			for(int i = 0; i < data.size(); i++) {
				if(data.get(i).getCustomer().equals(customer)) {
					if(data.get(i).getShipped() == shipped) { //if A shipped = true, if B shipped = false
						System.out.println(data.get(i));
						found = true;
					}											
				 }			
			}
			if(found == false) {
				System.out.println("\nNo order shipped yet!");
				System.out.println();
			}
		}
						
		public void employeeMenu(Employee employee) {
			Scanner userInput = new Scanner(System.in);
			Customer customer;
			Order order;
			char choice;    
				do {
					System.out.println("Please select from the following options:");
					System.out.println();
					System.out.println("A. Search Customer");
			  		System.out.println("B. Display Customers");
			  		System.out.println("C. List Orders");
			  		System.out.println("D. Ship an order");
			  		System.out.println("E. Add  or Remove Art");
			  		System.out.println("X. Exit\n");
			  		
			  		System.out.print("Enter your choice: ");
			  				  
			  		choice = userInput.next().charAt(0);
			  		
			  		if(choice == 'A' || choice == 'a') {			  			
			  			employeeChoiceA(employee);
			  		}
			  		
			  		else if(choice == 'B'|| choice == 'b') {
			  			System.out.println("Display Customers' Information!");
			  			customers.printTable();
			  		}			  

			  		else if(choice == 'C'|| choice == 'c') {
			  			if(orders.isEmpty()) {
			  				System.out.println("No orders!");
			  			}
			  			else {
			  				
			  				System.out.println(orders.toString());
			  			}
			  		}
			  		
			  		else if(choice == 'D'|| choice == 'd') { 
			  			order = orders.pop();
			  			order.setShipped(true);
			  		}
			  		
			  		else if(choice == 'E'|| choice == 'e') {
			  			addRemoveArt();
	  			
			  		}
			  		else if(choice == 'X'|| choice == 'x') {
			  			System.out.println("\nGoodbye our staff " + employee.getFirstName() + " !\n");
			  		}
			  		else {
			  			System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
			  		}
				}while(!(choice == 'X' || choice == 'x')); 	
				login();
			
		}
		
		public void employeeChoiceA(Employee employee) {
			Customer customer;
			String firstName, lastName, email;
			char choice;
			
			Scanner userInput = new Scanner(System.in);
			
			System.out.print("\nEnter Customer's First Name: ");
			firstName = userInput.nextLine();
			
			System.out.print("\nEnter Customer's Last Name: ");
			lastName = userInput.nextLine();
			
			System.out.print("\nEnter Customer's email: ");
			email= userInput.nextLine();
			
			customer = new Customer(firstName, lastName, email);
			
			if(customers.contains(customer)) {
				customer = customers.get(customer);
				System.out.println("Customer found: ");
				System.out.println(customer);
			}
			else {
				System.out.println("Customer not exist in our store!");
			}
		}
		
		
		public void addRemoveArt() {
			char choice;
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Choose: 1: Add New Art to the Gallery!");
			System.out.println("Choose: 2: Remove Art from Gallery!");
			System.out.print("Your choice 1 or 2: ");
			
			choice = userInput.next().charAt(0);
			if(choice == '1') {
				addPainting();
			}
			else if(choice == '2') {
				removePainting();
			}	
			else {
				   System.out.println("\nInvalid Choice!\n");	  
		   } 						
		}
				
		public void addPainting() {
			String title, artist, description;
			double price;
		    int year;
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Enter the title of the painting!");	
			title = userInput.nextLine();			
			System.out.println("Enter the artist of the painting!");	
			artist = userInput.nextLine();
			System.out.println("Enter the year of the painting!");	
			year = userInput.nextInt();
			userInput.nextLine();
			System.out.println("Enter the price of the painting!");	
			price = userInput.nextInt();
			userInput.nextLine();
			System.out.println("Enter the description of the painting!");	
			description = userInput.nextLine();
			
			paintings.insert(new Painting(title, artist, year, price, description));  			
		}
		
		public void removePainting() {
			String title, artist;			
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Enter the title of the painting!");	
			title = userInput.nextLine();
			System.out.println("Enter the artist of the painting!");	
			artist = userInput.nextLine();
						
			paintings.remove(new Painting(title, artist));					
		}
}

