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
		
		PriorityComparator c = new PriorityComparator();
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
			
			System.out.println("Choose: 1:login customer, 2: login employee: ");
			choice = userInput.next().charAt(0);
			if(choice == '1') {
				loginCustomer();
			}
			if(choice == '2') {
				loginEmployee();
			}			
		}
		
		public void loginCustomer() {
			Scanner userInput = new Scanner(System.in);
			System.out.println("Welcome to Art Gallery!\n");
			   System.out.print("Please enter your user name: ");
			   String userName = userInput.nextLine();
			   System.out.print("Please enter your password: ");
			   String password = userInput.nextLine();
			   System.out.println();
			   
			   Customer newCustomer;
			   newCustomer = new Customer(userName, password);
			   newCustomer = customers.get(newCustomer);
			   String firstName, lastName, email, address;
			   double cash;
			   
			   if(newCustomer == null) {
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
				   firstName = newCustomer.getFirstName();
				   lastName = newCustomer.getLastName();	   
			   }
			   System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
			   customerMenu(newCustomer);
			
		}
		
		public void loginEmployee() {
			Scanner userInput = new Scanner(System.in);
			System.out.println("Welcome to Art Gallery!\n");
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
			  			customer.printPaintingByTitle();	
			  		}
			  		
			  		else if(choice == 'D'|| choice == 'd') {
			  			customer.printPaintingByValue();
	  			
			  		}
			  		else if(choice == 'X'|| choice == 'x') {
			  			System.out.println("\nGoodbye!");
			  		}
			  		else {
			  			System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
			  		}
				}while(!(choice == 'X' || choice == 'x')); 
		}
					  		

		
			
		public void customerChoiceA(Customer customer) {
			Scanner userInput = new Scanner(System.in);
			System.out.println("\nPlease select from the options below:\n");
			   
			paintings.printTable();	   
			System.out.print("\nEnter the title of the painting to purchase: ");
			String title = userInput.nextLine();

			Painting painting = new Painting(title);	   
			painting = paintings.get(painting);	   
			customer.placeOrder(painting);
			System.out.println("\nYou successfully added the following painting to your order:\n");
			System.out.println(painting);	   
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
				
		
		public void employeeMenu(Employee employee) {
			Scanner userInput = new Scanner(System.in);
			Customer customer;
			char choice;    
				do {
					System.out.println("Please select from the following options:");
					System.out.println();
					System.out.println("A. Search Customer");
			  		System.out.println("B. Display Customers");
			  		System.out.println("C. List Orders");
			  		System.out.println("D. Add  or Remove Art");
			  		System.out.println("X. Exit\n");
			  		
			  		System.out.print("Enter your choice: ");
			  				  
			  		choice = userInput.next().charAt(0);
			  		
			  		if(choice == 'A' || choice == 'a') {			  			
			  			customer = employeeChoiceA(employee);
			  		}
			  		
			  		else if(choice == 'B'|| choice == 'b') {
			  		    employeeChoiceB(employee, customer);
			  		}			  

			  		else if(choice == 'C'|| choice == 'c') {
			  			orders.toString();
			  		}
			  		
			  		else if(choice == 'D'|| choice == 'd') {
			  			addRemoveArt();
	  			
			  		}
			  		else if(choice == 'X'|| choice == 'x') {
			  			System.out.println("\nGoodbye!");
			  		}
			  		else {
			  			System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
			  		}
				}while(!(choice == 'X' || choice == 'x')); 		
			
		}
		
		public Customer employeeChoiceA(Employee employee) {
			Customer customer;
			String firstName, lastName;
			
			Scanner userInput = new Scanner(System.in);
			
			System.out.print("\nEnter Customer's First Name: \n");
			firstName = userInput.nextLine();
			
			System.out.print("\nEnter Customer's Last Name: \n");
			lastName = userInput.nextLine();
			
			customer = new Customer(firstName, lastName);
			customers.get(customer);
			
			return customer;
		}
		
		public void employeeChoiceB(Employee employeem, Customer customer) {			
			char choice;
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Choose: 1: Display Customer's Painting by Titel!");
			System.out.println("Choose: 2: Display Customer's Painting by Value!");
			
			choice = userInput.next().charAt(0);
			if(choice == '1') {
				customer.printPaintingByTitle();
			}
			else if(choice == '2') {
				customer.printPaintingByValue();
			}	
			else {
				   System.out.println("\nInvalid Choice!\n");	  
		   } 			
		}
		
		public void addRemoveArt() {
			char choice;
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Choose: 1: Add New Art to the Gallery!");
			System.out.println("Choose: 2: Remove Art from Gallery!");
			
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
