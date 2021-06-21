package finalProject;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Comparator<Integer> c = Comparator.<Integer>naturalOrder();
		
		Heap<Integer> h = new Heap<>(50,c);
		
		h.insert(30);
		h.insert(10);
		h.insert(2);
		h.insert(-8);
		h.pop();		
		
		ArrayList<Integer> newList = h.sort();
		System.out.println(newList);
				
		System.out.println(h);		
	}

}
