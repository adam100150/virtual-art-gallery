/**
 * BST.java
 * @artist Nathan Brin
 * @artist Adam Ashkenazi
 * @artist Sihan Sun
 * @artist Alice Zhang
 * CIS 22C Final Project
 */
package src;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

class Node<T> {
    T data;
    Node<T> left;
    Node<T> right;

    public Node(T data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class BST<T> implements Iterable {

   Node<T> root;

   /***CONSTRUCTORS***/

   /**
   * Default constructor for BST
   * sets root to null
   */
   public BST() {
      root = null;
   }

   /**
   * Copy constructor for BST
   * @param bst the BST of which
   * to make a copy.
   * @param c the way the tree
   * is organized
   */
   public BST(BST<T> bst, Comparator<T> c) {
      if (bst == null){
        return;
      } else {
        copyHelper(bst.root, c);
      }
   }

   /**
   * Helper method for copy constructor
   * @param node the node containing
   * data to copy
   * @param c the way the tree is organized
   */
   private void copyHelper(Node<T> node, Comparator<T> c) {
      if(node == null) {
        return;
      } else {
        insert(node.data, c);
        copyHelper(node.left, c);
        copyHelper(node.right, c);
      }
   }

   /***ACCESSORS***/

   /**
   * Returns the data stored in the root
   * @precondition !isEmpty()
   * @return the data stored in the root
   * @throws NoSuchElementException when
   * precondition is violated
   */
   public T getRoot() throws NoSuchElementException{
	  if(root == null) {
	      throw new NoSuchElementException("getRoot(): BST is Empty.");
	  }
      return root.data;
   }

   /**
   * Determines whether the tree is empty
   * @return whether the tree is empty
   */
   public boolean isEmpty() {
      return root == null;
   }


   /**
    * Returns the current size of the
    * tree (number of nodes)
    * @return the size of the tree
    */
    public int getSize() {
      if(root == null)
      {
        return 0;
      }
       return getSize(root);

    }

    /**
    * Helper method for the getSize method
    * @param node the current node to count
    * @return the size of the tree
    */
    private int getSize(Node<T> node) {
      if(node == null)
      {
        return 0;
      }
      else
      {
        return 1 + getSize(node.left) + getSize(node.right);
      }
    }

   /**
   * Returns the height of tree by
   * counting edges.
   * @return the height of the tree
   */
   public int getHeight() {
      return getHeight(root);
   }

   /**
   * Helper method for getHeight method
   * @param node the current
   * node whose height to count
   * @return the height of the tree
   */
   private int getHeight(Node node)
   {
     if(node == null)
     {
       return -1;
     }
     return 1 + Math.max(getHeight(node.left), getHeight(node.right));
   }

   /**
   * Returns the smallest value in the tree
   * @precondition !isEmpty()
   * @return the smallest value in the tree
   * @throws NoSuchElementException when the
   * precondition is violated
   */
   public T findMin() throws NoSuchElementException{
     if(root == null) {
       throw new NoSuchElementException("findMin(): BST is Empty.");
     }
      return findMin(root);
   }

   /**
   * Recursive helper method to findMin method
   * @param node the current node to check
   * if it is the smallest
   * @return the smallest value in the tree
   */
   private T findMin(Node<T> node)
   {
     if(node.left == null)
     {
       return node.data;
     }
     return findMin(node.left);

   }

   /**
   * Returns the largest value in the tree
   * @precondition !isEmpty()
   * @return the largest value in the tree
   * @throws NoSuchElementException when the
   * precondition is violated
   */
   public T findMax() throws NoSuchElementException
   {
     if(root == null)
     {
       throw new NoSuchElementException("findMax(): BST is Empty.");
     }
      return findMax(root);
   }

   /**
   * Recursive helper method to findMax method
   * @param node the current node to check
   * if it is the largest
   * @return the largest value in the tree
   */
   private T findMax(Node<T> node) {
    if(node.right == null)
     {
       return node.data;
     }
     return findMax(node.right);
   }

   /**
   * Searches for a specified value
   * in the tree
   * @param data the value to search for
   * @param c the Comparator that indicates the way
   * the data in the tree was ordered
   * @return the data stored in that Node
   * of the tree is found or null otherwise
   */
   public T search(T data, Comparator<T> c) {
     if(root == null) {
            return null;
        } else {
            return search(data, root, c);
        }
   }

   /**
   * Helper method for the search method
   * @param data the data to search for
   * @param node the current node to check
   * @param c the Comparator that determines
   * how the BST is organized
   * @return the data stored in that Node
   * of the tree is found or null otherwise
   */
   private T search(T data, Node<T> node, Comparator<T> c) {
      if(node == null){
        return null;
      }
      else {
        if (c.compare(data,node.data) < 0){
          return search(data,node.left, c);
        } else if(c.compare(data,node.data) > 0) {
          return search(data,node.right, c);
        } else {
          return node.data;
        }
      }
   }

   /***MUTATORS***/

   /**
   * Inserts a new node in the tree
   * @param data the data to insert
   * @param c the Comparator indicating
   * how data in the tree is ordered
   */
   public void insert(T data, Comparator<T> c)
   {
      if (root == null) {
           root = new Node(data);
     } else {
         insert(data, root, c);
     }
   }

   /**
   * Helper method to insert
   * Inserts a new value in the tree
   * @param data the data to insert
   * @param node the current node in the
   * search for the correct location to insert
   * @param c the Comparator indicating
   * how data in the tree is ordered
   */
   private void insert(T data, Node<T> node, Comparator<T> c)
   {
      if(c.compare(data,node.data) <= 0)
      {
        if(node.left == null)
        {
          node.left = new Node(data);
        }
        else
        {
          insert(data, node.left, c);
        }
      }
      else
      {
        if(node.right == null)
        {
          node.right = new Node(data);
        }
        else
        {
          insert(data, node.right, c);
        }
      }
   }


   /**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @param c    the Comparator indicating how data in the tree is organized Note:
	 *             updates nothing when the element is not in the tree
	 */
	public void remove(T data, Comparator<T> c) {
		remove(data,root,c);
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @param c    the Comparator indicating how data in the tree is organized
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node<T> node, Comparator<T> c) {
		if(node == null) { 
			return node;
		}else if(c.compare(data, node.data)<0) {
			node.left = remove(data,node.left,c);
		}else if (c.compare(data, node.data)>0){
			node.right = remove(data, node.right, c);
		}else {
			if(node.left ==null &&node.right ==null) { //leaf node
				//Two objects(reference, address, information) are same or not,
				// == only for information same
				if(node.equals(root)) { 
					root = null; // Tree only root 
				}
				//Tell parents that this node has to be cut
				return null; 
			}else if(node.left != null && node.right == null) {
				if(node.equals(root)) { 
					node = node.left;
					root.left = null;
					root = node;
				}
				else {
					node = node.left;
				}
				
				
				
			}else if (node.right != null && node.left == null) {
				if(node.equals(root)) { 
					node = node.right;
					root.right = null;
					root = node;
				}
				else {
					node = node.right;
				}
							
			}else {
				T minimumValue = findMin(node.right);
				node.data = minimumValue;
				node.right = remove(minimumValue, node.right, c);
			}
		}
		return node;
	}



       /***ADDITONAL OPERATIONS***/

   /**
   * Prints the data in pre order
   * to the console followed by a new
   * line
   */
   public void preOrderPrint() {
      preOrderPrint(root);
   }

   /**
   * Helper method to preOrderPrint method
   * Prints the data in pre order
   * to the console followed by a new line
   */
   private void preOrderPrint(Node node) {
     if(node == null)
     {
        return;
     }
     System.out.println(node.data + " ");
     preOrderPrint(node.left);
     preOrderPrint(node.right);
     System.out.println();
   }

   /**
    * Prints the data in sorted order
    * to the console followed by a new line
    */
    public void inOrderPrint() {
       inOrderPrint(root);
    }

    /**
    * Helper method to inOrderPrint method
    * Prints the data in sorted order
    * to the console followed by a new line
    */
    private void inOrderPrint(Node node)
    {
      if(node == null)
      {
        return;
      }
      inOrderPrint(node.left);
      System.out.print(node.data);
      inOrderPrint(node.right);      
    }


   /**
   * Prints the data in post order
   * to the console followed by a new line
   */
   public void postOrderPrint() {
      postOrderPrint(root);
   }

   /**
   * Helper method to postOrderPrint method
   * Prints the data in post order
   * to the console
   */
   private void postOrderPrint(Node node) {
      if(node == null)
      {
        return;
      }
      postOrderPrint(node.left);
      postOrderPrint(node.right);
      System.out.println(node.data + " ");
      System.out.println();
    }

    public BSTIterator<T> iterator() {
       return new BSTIterator<T>(this);
    }

}

class BSTIterator<E> implements Iterator<E> {
    private Queue<E> paintingsInOrder = new LinkedBlockingDeque<E>();
    public BSTIterator(BST currBST) {
        helper(currBST.root);
    }

    void helper(Node<E> currNode) {
        if (currNode == null) {
            return;
        }
        helper(currNode.left);
        paintingsInOrder.add(currNode.data);
        helper(currNode.right);
    }

    @Override
    public boolean hasNext() {
        return paintingsInOrder.isEmpty();
    }

    @Override
    public E next() {
        return paintingsInOrder.poll();
    }
}