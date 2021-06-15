/*Painting.java
 * @author Nathan Brin
 * @author Adam Ashkenazi
 * @author Sihan
 * @author Alice
 * CIS 22C lab 6
 */

import java.text.DecimalFormat;

public class Painting 
{
	private final String title;
	private final String author;
	private final String idNumber;
	private final int year;
	private double price;
	private String description;
	
	/**
	* Constructor that assigns all values
	* @param title the title
	* @param author the author
	* @param idNumber the idNumber
	* @param year the year
	* @param price the price
	* @param description the description
	*/
	public Painting(String title, String author, String idNumber, int year, double price, String description)
	{
		this.title = title;
		this.author = author;
		this.idNumber = idNumber;
		this.year = year;
		this.price = price;
		this.description = description;
	}
	
	/**
	* Two-argument constructor that assigns
	* a name and author and empty values to everything else
	* @param Title for title
	* @param Author for author
	*/
	public Painting(String title, String author)
	{
		this.title = title;
		this.author = author;
		idNumber = "";
		year = 0;
		price = 0.0;
		description = "";
	}
	
	/**
	* One-argument constructor that assigns
	* an ID # and empty values to everything else
    * @param idNumber the idNumber
    */
	public Painting(String idNumber)
	{
		this.idNumber = idNumber;
		this.title = "";
		this.author = "";
		year = 0;
		price = 0.0;
		description = "";
	}

	/**
	* Accesses the painting title
	* @return the title
	*/
	public String getTitle()
	{
		return title;
	}
	
	/**
	* Accesses the painting Author
	* @return the author
	*/
	public String getAuthor()
	{
		return author;
	}
	
	/**
	* Accesses the painting ID #
	* @return the ID number
	*/
	public String getId()
	{
		return idNumber;
	}
	
	/**
	* Accesses the year the painting was made
	* @return the year
	*/
	public int getYear()
	{
		return year;
	}
	
	/**
	* Accesses the painting price
	* @return the price
	*/
	public double getPrice()
	{
		return price;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	/**
	* Updates the price
	* @param price is the new price
	*/
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	/**
	* Updates the description
	* @param description is the new description
	*/
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	* Creates a String of the painting information
	* in the format:
	* <title>
	* <author>
	* <idNumber>
	* <year>
	* Price: $<price>
	* <description>
	* <new line>
	*/
	@Override public String toString()
	{
		DecimalFormat df = new DecimalFormat("##.00")
		return title + "\n" + author + "\n" + idNumber + "\n" + year + "\n" + "Price: $" + df.format(price) + "\n" + description + "\n";
	}
	
	/**
	 * Compares this Painting to
	 * another Object for equality
	 * @param o another Object
	 * (Painting or otherwise)
	 * @return whether o is a Painting
	 * and has the same idNumber as this Painting
	 */
	@Override public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if (!(o instanceof Painting)) {
			return false;
		} else {
			Painting p = (Painting) o;
			if (this.idNumber != p.getId()) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Returns a consistent hash code for
	 * each Painting by summing the Unicode values
	 * of each character in the key
	 * Key = idNumber
	 * @return the hash code
	 */
	@Override public int hashCode() {
		String key = idNumber;
		int sum = 0;
		for (int i = 0; i < key.length(); i++){
			sum += (int)key.charAt(i);
		}
		return sum;
	}
}

