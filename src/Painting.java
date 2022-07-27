/*Painting.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan
 * @artist Alice
 * CIS 22C Final Lab
 */
package src;
import java.text.DecimalFormat;
import java.util.Comparator;

public class Painting 
{
	private final String title;
	private final String artist;
	private final int year;
	private double price;
	private String description;
	
	/**
	* Constructor that assigns all values
	* @param title the title
	* @param artist the artist
	* @param year the year
	* @param price the price
	* @param description the description
	*/
	public Painting(String title, String artist, int year, double price, String description)
	{
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.price = price;
		this.description = description;
	}
	
	/**
	* Two-argument constructor that assigns
	* a name and artist and empty values to everything else
	* @param title for title
	* @param artist for artist
	*/
	public Painting(String title, String artist)
	{
		this.title = title;
		this.artist = artist;
		year = 0;
		price = 0.0;
		description = "";
	}
	
	/**
	* One-argument constructor that assigns
	* a title
    * @param title the title
    */
	public Painting(String title)
	{
		this.title = title;
		this.artist = "";
		year = 0;
		price = 0.0;
		description = "";
	}
	
	/**
	* One-argument constructor that assigns
	* a price
    * @param price the price
    */
	public Painting(double price)
	{
		this.title = "";
		this.artist = "";
		year = 0;
		this.price = price;
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
	* Accesses the painting artist
	* @return the artist
	*/
	public String getArtist()
	{
		return artist;
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
	* <artist>
	* <year>
	* Price: $<price>
	* <description>
	* <new line>
	*/
	@Override public String toString()
	{
		DecimalFormat df = new DecimalFormat("##.00");
		return title + "\n" + artist + "\n" + year + "\n" + "Price: $" + df.format(price) + "\n" + description + "\n\n";
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
			if (this.title != p.getTitle() || this.artist != p.getArtist()) {
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
		String key = title + artist;
		int sum = 0;
		for (int i = 0; i < key.length(); i++){
			sum += (int)key.charAt(i);
		}
		return sum;
	}
}

class NameComparator implements Comparator<Painting> {
    /**
   * Compares the two Paintings by name of the Painting
   * uses the String compareTo method to make the comparison
   * @param first the first painting
   * @param second the second painting
   */
   @Override public int compare(Painting first, Painting second) {
      return first.getTitle().compareTo(second.getTitle());
   }
}  //end class NameComparator

class ValueComparator implements Comparator<Painting> {
	   /**
	   * Compares the two paintings by price
	   * uses the static Double compare method to make the
	   * comparison
	   * @param first the first Painting
	   * @param second the second Painting
	   */
	   @Override public int compare(Painting first, Painting second)
	   {
	      return Double.compare(first.getPrice(), second.getPrice());
	   }
	}
