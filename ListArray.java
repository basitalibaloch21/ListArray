
import java.util.*;
/**
 * ListArray generic class, this class includes all of the classes and methods needed to instantiate the data structure
 * and perform it's respective functions
 * @author Basit Baloch (basitalibaloch21@gmail.com)
 *
 * @param <T> data type of structure
 */
public class ListArray<T> implements List<T> {
	/**
	 * Node class, this class will be what each node of the ListArray is composed of
	 * @author Basit Baloch
	 *
	 * @param <T> data type
	 */
	private class Node<T>{
		
		private Object miniArray[]; //miniArray of each node
		private Node<T> next; //the next element reference
		
		/**
		 * Constructor, instantiates the node with the passed in array
		 * @param array, passed in array
		 */
		public Node(Object array[]) {
			this.miniArray = array; //initialize the field with the array passed through
		}
		
		/**
		 * toString method, this is called for each node when printing the entire data structure
		 * @return String miniArray, printed miniArray
		 */
		public String toString() {
			if(this.next == null) {
				return Arrays.toString(miniArray);
			}
			else {
				return Arrays.toString(miniArray) + " -> "; //just add a arrow, yay!
			}
		}
		
	}
	
	/**
	 * ListArrayIterator class, this class implements the iterator and will allow us to iterate through
	 * the data structure
	 * @author Basit Baloch
	 *
	 * @param <T> data type of the ListArray
	 */
	private class ListArrayIterator<T> implements Iterator<T>{
		
		private ListArray<T>ListArray; //ListArray object
		private ListArray<T>.Node<T> current; //ListArray node pointer
		private int currentIndex; //ListArray sub-array pointer
		/**
		 * Constructor, will instantiate the iterator with the ListArray passed through
		 * @param list, ListArray we want to iterate through
		 */
		public ListArrayIterator(ListArray<T> list) {
			ListArray = list;
			current = list.head; //set the node pointer to the head
			currentIndex = 0; //set the sub-array pointer to 0
		}
		
		/**
		 * hasNext method, this method will return true if there is a next node or element
		 * in the data structure
		 * @return true/false, returns true or false if there is a next element or element
		 */
		public boolean hasNext() {
			//if the pointer for the miniArray is in bounds return true
			if(currentIndex < ListArray.arraySize) {
				return true;
			}
			//if the pointer is out of bounds but a next node exists, return true
			else if(currentIndex >= ListArray.arraySize && current.next != null) {
				return true;
			}
			//if neither are true we have reached the end of the data structure
			return false;
		}

		/**
		 * next method, this method will return the next element within the data structure
		 * @return data, the data obtained from the node's subarray we are iterating through
		 */
		
		public T next() {
			//recall this method is based off of hasNext, we mustn't worry about cases of null pointers
			T data;
			if(currentIndex >= ListArray.arraySize) {
				current = current.next;
				currentIndex = 0;
			}
			data = (T) current.miniArray[currentIndex];
			currentIndex++;
			return (T) data;
		}

		
	}
	
	private Node<T> head; //head of the ListArray
	private int totalSize; //total size of the data structure the user wants at initialization
	private int arraySize; //size of each sub-array
	
	
	/**
	 * isEqual method, helper method to deter null pointer exceptions
	 * @param o1, object 1 to compare
	 * @param o2, object 2 to compare
	 * @return true/false, returns true if the objects are equal, otherwise false
	 */
	
	private static boolean isEqual(Object o1, Object o2) {
		if(o1 == null && o2 != null) {
			return false;
		}
		else if(o1 != null && o2 == null) {
			return false;
		}
		else {
			return o1 == o2 || o1.equals(o2);
		}
	}
	
	/**
	 * toString method, this method will allow us to print the data structure in it's entirety
	 * @return str, the printed data structure
	 */
	
	public String toString() {
		String str = this.head.toString();
		Node<T> node = this.head;
		//concatenate each node's toString message to the string and return while the next is not null
		while(node.next != null) {
			node = node.next;
			str += node.toString();
		}
		return str;
	}
	
	/**
	 * constructor, this is our constructor with no parameters and will initialize the data structure
	 * to just one node of size 5
	 */
	
	public ListArray() {
		this.head = new Node(new Object[5]); //initialize just the head to be of size 5
		this.arraySize = 5;
	}
	
	/**
	 * constructor, this is our constructor with one parameter, it will initialize the head with one array of size "size"
	 * @param size, passed in size that will be used to instantiate the subarray
	 */
	
	public ListArray(int size) {
		this.arraySize = size;
		this.head = new Node(new Object[size]);
	}
	
	/**
	 * constructor, this is our constructor with two parameters, it will initialize the structure to be as close to "total" as possible
	 * in total number of elements across all nodes, and each array will be of size "size"
	 * @param size, passed in size of each subarray
	 * @param total, how many total elements the data structure should be 
	 */
	
	public ListArray(int size, int total) {
		this.arraySize = size;
		this.head = new Node(new Object[size]);
		int arraystoCreate;
		//if there is no remainder, than the total and size divide perfectly to give us a whole number of nodes to create
		if((total%size) == 0) {
			arraystoCreate = (total/size);
		}
		else {
			arraystoCreate = (total/size + 1); //if it is not a whole number, simply create one extra array to compensate for this
		}
		//start at the head and create new elements to fufill arraystoCreate
		Node<T> temp = head;
		for(int i = 1; i<arraystoCreate; i++) {
			temp.next = new Node(new Object[size]);
			temp = temp.next;
		}
	}

	/**
	 * size method, returns the total number of non null elements in the data structure
	 * @return total, total number of non null elements
	 */
	
	public int size() {
		int total = 0;
		//if the data structure only contains the head, call the helper method subElements
		if(this.head.next == null) {
			return subElements(this.head);
		}
		//iterate through and call subElements for each node, concatenating it to total
		else {
			Node<T> temp = this.head;
			while(temp != null) {
				total += subElements(temp);
				temp = temp.next;
			}
			return total;
		}
	}
	
	/**
	 * subElements method, this method will look through the node in the parameter and find the total number of non null elements
	 * @param node, node to look through
	 * @return counter, total number of non null elements
	 */
	
	private int subElements(Node<T> node) {
		int counter = 0;
		for(int i = 0; i<arraySize; i++) {
			if(node.miniArray[i] != null) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * isEmpty method
	 * @return true/false, will return true if the data structure contains all null elements, false otherwise
	 */
	public boolean isEmpty() {
		int size = size();
		if(size == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * contains method, this method will look through the data structure to see if it contains the object passed in through the parameter
	 * @param o, object we are looking for
	 * @return true/false, return true if the object is present, false otherwise
	 */
	
	public boolean contains(Object o) {
		Node<T> node = this.head;
		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				//here we call our helper method to deter any null pointer exceptions when comparing
				if(isEqual(node.miniArray[i], o)) {
					return true;
				}
			}
			node = node.next;
		}
		return false;
	}

	/**
	 * iterator method, this method returns a new iterator to iterate through the data structure
	 * @return new iterator
	 */
	public Iterator<T> iterator() {
		return new ListArrayIterator(this);
	}
	
	/**
	 * toArray method, this method will convert the data structure to an array
	 * @return newArray, all of the non null elements of the data structure in one array
	 */
	public Object[] toArray() {
		
		//if there are no elements present throw null pointer exception
		if(size() == 0) {
			throw new NullPointerException();
		}
		
		//create a new array the same size as the total number of non null elements
		Object newArray[] = new Object[size()];
		int counter = 0;
		Node<T> node = this.head;
		//iterate through and add these elements
		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				if(node.miniArray[i] != null) {
					newArray[counter] = node.miniArray[i];
					counter++;
				}
			}
			node = node.next;
		}
		return newArray;
	}

	/**
	 * add method, this method will append the passed through element in the parameter to the data structure
	 * @return true, will return true if the element is added (which is always since it is appended)
	 */
	public boolean add(T e) {
		int index;
		//if the size is 0, the index we add at is 0
		if(size() == 0) {
			index = 0;
		}
		//append the element to the index after the last element
		else {			
			index = getlastIndex() + 1;
		}
		int nodeIndex = (index / arraySize); //integer division to find the node we iterate to
		int arrayIndex = (index % arraySize); //modulus division to find the sub array index we iterate to
		Node<T> temp = this.head;
		int nodeCounter = 0;
		while(nodeCounter != nodeIndex){
			if(temp.next == null) {
				//create a new node if we go out of bounds
				temp.next = new Node(new Object[arraySize]);
			}
			temp = temp.next;
			nodeCounter++;
		}
		temp.miniArray[arrayIndex] = e;
		return true;
	}
	
	/**
	 * getlastIndex method, this method is a helper method that is only applicable when the head is NOT empty
	 * @return index, last non null element in the data structure
	 */
	
	private int getlastIndex() {
		Node<T> node = this.head;
		int counter = 0;
		int index = 0;

		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				if(node.miniArray[i] != null) {
					index = counter;
				}
				counter++;
			}
			node = node.next;
		}
		return index;
	}

	/**
	 * remove method, this method will return true if we manage to remove the object passed in, false otherwise
	 * @param o, object we want to remove
	 * @return true/false, true if removed, false if we are not able to (it is not in the data structure)
	 */
	
	public boolean remove(Object o) {
		Node<T> node = this.head;
		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				if(isEqual(node.miniArray[i], o)) {
					node.miniArray[i] = null;
					shiftLeft(i, node);
					return true;
				}
			}
			node = node.next;
		}
		return false;
	}
	/**
	 * shiftLeft method, this is a helper method that is called anytime we remove an element, it will shift the elements in the data structure
	 * to fill the gap that is left from the removed element
	 * @param index, index we want to shift at
	 * @param node, node we want to shift at 
	 */
	private void shiftLeft(int index, Node<T> node) {
		for(int i = index; i<arraySize; i++) {
			if(i+1 >= arraySize) {
				if(node.next != null) {
					node.miniArray[i] = node.next.miniArray[0]; //grab the first element from the next node and set it to the last of current
					node.next.miniArray[0] = null; //set the first element from the next node to null
					shiftLeft((0), node.next); //recursively call the function again
				}
				else {
					//if we arrive out of bounds and the next element is null, this means we have finished shifting
					removeEmptyNodes(this.head); //call the method to clear empty nodes here, since this is the stop case for recursion
					return;
				}
			}
			else {
				//if the element in front is null, stop shifting
				if(node.miniArray[i+1] == null) { 
					removeEmptyNodes(this.head); //call the method to clear empty nodes here, since this is the stop case for recursion
					return;
				}
				node.miniArray[i] = node.miniArray[i+1];
				node.miniArray[i+1] = null;
			}
		}
	}
	
	/**
	 * removeEmptyNodes method, helper function used when shifting that will remove any nodes that become empty
	 * @param node, node to iterate through
	 */
	private void removeEmptyNodes(Node<T> node) {
		Node<T> temp = node; //current node
		
		//if the head is empty, we must traverse the structure and find the new head
		if(node == this.head && subElements(node) == 0) {
			while(node.next != null) {
				node = node.next;
				if(subElements(node) != 0) {
					this.head = node;
					removeEmptyNodes(node);
				}
			}
		}
		boolean flag = false; //flag that will tell us if a node is empty or not
		//traverse the data structure until we find a node that is not empty
		while(flag == false && node.next != null) {
			node = node.next;
			if(subElements(node) != 0) {
				flag = true;
			}
		}
		//if the flag is still false, this means there are no more nodes that contain elements
		if(flag == false) {
			temp.next = null; //set the node that isnt empty next reference to null, it is now the last node
			return;
		}
		//if the flag is true, this means that we have found a node with elements, set the next reference and call the method again
		else if(flag == true) {
			temp.next = node;
			removeEmptyNodes(node);
		}
		
	}

	/**
	 * addAll method, this method will take as an input a collection and add all elements from it into the data structure
	 * @return flag, will be true or false depending on if any elements were added or not
	 */
	
	public boolean addAll(Collection<? extends T> c) {
		boolean flag = false;
		for(T e: c) {
			this.add(e);
			flag = true;
		}
		return flag;
	}

	/**
	 * addAll method, this method has two inputs, a collection, and a index to begin adding all elements from the collection to the data structure at
	 * @return flag, will be true or false depnding on if any elements were added or not
	 */
	
	public boolean addAll(int index, Collection<? extends T> c) {
		int indexAdd = index;
		boolean flag = false;
		for(T e: c) {
			this.add(indexAdd, e);
			indexAdd++;
			flag = true;
		}
		return flag;
	}

	/**
	 * clear method, this method will clear the data structure
	 */
	public void clear() {
		//set the next reference to null, and clear all elements of the subarray
		this.head.next = null;
		for(int i = 0; i<arraySize; i++) {
			this.head.miniArray[i] = null;
		}
		
	}

	/**
	 * get method, this method will traverse the data structure to attain the element at the specified index
	 * @param index, index to obtain from
	 * @return the element
	 */
	public T get(int index) {
		int nodeIndex = (index / arraySize); //which node contains this index
		int arrayIndex = (index % arraySize); //the miniarray index
		Node<T> node = this.head;
		int counter = 0;
		
		//go to the node that we want to get from
		while(counter < nodeIndex) {
			if(node.next == null) {
				//if the index is out of bounds, throw exception
				throw new IndexOutOfBoundsException();
			}
			node = node.next;
			counter++;
		}
		//return index
		return (T) node.miniArray[arrayIndex];
	}

	/**
	 * set method, this method will overwrite at the index passed through with the element passed through
	 * @param index, index to go to
	 * @param element, element to write at the index
	 * @return temp, the element that we have overwritten
	 */
	public T set(int index, T element) {
		int nodeIndex = (index / arraySize); //which node contains this index
		int arrayIndex = (index % arraySize); //the miniarray index
		Node<T> node = this.head;
		int counter = 0;
		
		//go to the node that we want to write into
		while(counter < nodeIndex) {
			if(node.next == null) {
				//if the index is out of bounds, throw an exception
				throw new IndexOutOfBoundsException();
			}
			node = node.next;
			counter++;
		}
		T temp = (T) node.miniArray[arrayIndex];
		node.miniArray[arrayIndex] = element;
		return temp;
	}

	/**
	 * add method, this method will insert the passed in element at the specified index, and shift over the elements
	 * @param index, specified index to add to
	 * @param element, element to add at specified index
	 */
	public void add(int index, T element) {
		int nodeIndex = (index / arraySize); //which node contains this index
		int arrayIndex = (index % arraySize); //the miniarray index
		Node<T> node = this.head;
		int counter = 0;
		//traverse to the node that we want to add into
		while(counter < nodeIndex) {
			if(node.next == null) {
				node.next = new Node<T>(new Object[arraySize]);
			}
			node = node.next;
			counter++;
		}
		//if the element is not null, then we recursively call this method again and shift all elements until we hit the null case
		if(node.miniArray[arrayIndex] != null) {
			T temp = (T) node.miniArray[arrayIndex]; //store the element at the index in temp before it is overwritten
			node.miniArray[arrayIndex] = element;
			add(index+1, temp); //take the temporary element and pass it through again with index+1 to shift
		}
		else {
			//if element is null, then we stop recursion here
			node.miniArray[arrayIndex] = element;
			return;
		}
	}

	/**
	 * remove method, this method will remove an element at the specified index
	 * @param index, index we want to remove from
	 * @return temp, return the element we have removed
	 */
	
	public T remove(int index) {
		int nodeIndex = (index / arraySize); //which node contains this index
		int arrayIndex = (index % arraySize); //the miniarray index
		Node<T> node = this.head;
		int counter = 0;
		
		//go to the node that we want to remove from
		while(counter < nodeIndex) {
			if(node.next == null) {
				
				throw new IndexOutOfBoundsException();
			}
			node = node.next;
			counter++;
		}
		T temp = (T)node.miniArray[arrayIndex];
		node.miniArray[arrayIndex] = null;
		shiftLeft(arrayIndex, node);
		return temp;
	}

	/**
	 * indexOf method, this method will find the index of the object being passed through
	 * @param o, object to look for
	 * @return index, index of object
	 */
	public int indexOf(Object o) {
		Node<T> node = this.head;
		int index = 0;
		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				if(isEqual(node.miniArray[i], o)) {
					return index;
				}
				index++;
			}
			node = node.next;
		}
		//if we have traversed the entire structure, then the object is not present, throw exception
		throw new IndexOutOfBoundsException();
	}

	/**
	 * lastIndexOf, this method will return the last occurance of the element being passed in
	 * @param o, the object to look for the last occurance of
	 * @return index, index of last occurance
	 */
	
	public int lastIndexOf(Object o) {
		Node<T> node = this.head;
		int index = 0;
		int counter = 0;
		boolean flag = false;
		while(node != null) {
			for(int i = 0; i<arraySize; i++) {
				if(isEqual(node.miniArray[i], o)) {
					index = counter;
					flag = true;
				}
				counter++;
			}
			node = node.next;
		}
		if(flag = false) {
			throw new IndexOutOfBoundsException(); //if the object is not present throw exception
		}
		//otherwise return last index
		return index;
	}
	
	
	/**
	 * subList method, this method will return a new ListArray from the fromIndex to the toIndex
	 * @return newList 
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		//if the indices are off, throw an out of bounds exception
		if(fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		//if the data structure is empty, throw an null pointer exception
		else if(this.isEmpty() == true) {
			throw new NullPointerException();
		}
		
		List<T> newList = new ListArray<>(arraySize);
		int counter = 0;
		for(int i = fromIndex; i <=toIndex; i++) {
			newList.add(this.get(i));
		}
		//increment to the node that contains fromIndex
		return newList;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

}
