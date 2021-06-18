package finalProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import lab6Data.Customer;


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
		
		   //File file1 = new File("mutual_funds.txt");
		   //File file2 = new File("customers.txt");	
	      
	      try {
	    	  availablePainting();
	    	  readCustomersFile();
	    	  staffs();
	      }catch(FileNotFoundException e) {
	    	  System.out.println(e);
	    	  return;
	      }     
	      //userChoice(funds, accountValue, accountName);
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
			System.out.println("Available paintings: \n");
			paintings.printTable();
			System.out.println("Available paintings: \n");
			
			Painting(title, artist);
			
		}
		
		public void employeeMenu(Employee employee) {
			
		}
		
		 
		

}
