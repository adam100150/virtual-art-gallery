import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
	//private T[] heap;
	private ArrayList<T> heap;
	private Comparator<T> c;

    public Heap(int size, Comparator<T> c){
    	this.c = c;
	    heap = new ArrayList<>(size);
    }
    
    private int left(int i) {
    	return i * 2;  	
    }
    
    private int right(int i) {
    	return i * 2 + 1;
    }
    
    private int parent(int i) {
    	return i / 2;
    }
    
    
    public void maxHeapify(int i) {  //heapify down 
    	int l = left(i);
    	int r = right(i);
    	int maxIndex = i;
    	if(l  < heap.size() && c.compare(heap.get(l), heap.get(i)) == 1){ //
    		maxIndex = l;
    	}
    	
    	if(r  < heap.size() && c.compare(heap.get(r), heap.get(maxIndex)) == 1){
    		maxIndex = r;
    	}
    	
    	if(i != maxIndex) {    		
    		swap(i, maxIndex);
    		maxHeapify(maxIndex);
    	}    	
    }
    
    public void swap(int i, int j) {
    	T temp = heap.get(i);
    	heap.set(i, heap.get(j));
    	heap.set(j, temp);
    }
    
    public void heapifyUp(int i) {
    	int p = parent(i);
    	if(p >= 0 && c.compare(heap.get(i), heap.get(p)) == 1) {
    		swap(i, p);
    		heapifyUp(p);
    	}
    }
    
    public void buildHeap() { 
    	for(int i = (heap.size()/2); i >= 0; i--) {
    		maxHeapify(i);
    	}
    }  
    
    public void insert(T newValue) {
    	heap.add(newValue);
    	heapifyUp(heap.size());
    	
    }
    
    public T pop() {
    	swap(0, heap.size());
    	T temp = heap.get(heap.size());
    	heap.remove(heap.size());
    	maxHeapify(0);
    	return temp; // return the max value    	
    }
    
    public ArrayList<T> sort() {
    	ArrayList<T> newArray = new ArrayList<>();
    	
    	while(!heap.isEmpty()) {
    		newArray.add(pop());
    	}
    	for(int i = 0; i < newArray.size(); i++) {
    		insert(newArray.get(i));
    	}
    	return newArray;   	  	
    } 
    
    public String toString() {
    	return heap.toString();
    }
}