package lab06;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simplified version of a Priority Queue. It only supports accessing the maximum element in the queue.
 * @author Dominick Wliey
 *
 * @param <E> the type of elements contained in the simple priority queue.
 */
public class SimplePriorityQueue<E> implements PriorityQueue<E>, Iterable<E> {
	
	private E[] backup;
	private int size;
	private Comparator<? super E> cmp;
	
	/**
	 * Creates a SimplePriorityQueue that sorts elements upon insertion. Elements are sorted based on the 
	 * order defined by the comparator. 
	 * @param cmp - the comparator that defines the order of elements in the queue
	 */
	@SuppressWarnings("unchecked")
	public SimplePriorityQueue(Comparator<? super E> cmp) {
		this.backup = (E[]) new Object[5];
		this.size = 0;
		this.cmp = cmp;
	}
	
	/**
	 * Creates a SimplePriorityQueue that sorts elements upon insertion. Elements are sorted based on the 
	 * element type's natural order of sorting.  
	 */
	@SuppressWarnings("unchecked")
	public SimplePriorityQueue() {
		this.backup = (E[]) new Object[5];
		this.size = 0;
		this.cmp = null;
	}
	
	/**
	 * Finds the element in the queue with the highest priority. If the queue is empty, a NoSuchElementException is thrown.
	 * @return the highest priority element
	 */
	@Override
	public E findMax() throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException("Priority Queue is empty! Add items first!");
		return backup[size-1];
	}
	
	/**
	 * Deletes the element in the queue with the highest priority. If the queue is empty, a NoSuchElementException is thrown.
	 * @return the highest priority element
	 */
	@Override
	public E deleteMax() throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException("Priority Queue is empty! Add items first!");
		
		E temp = backup[size-1];
		backup[size-1] = null;
		size--;
		return temp;
	}
	
	/**
	 * Adds a new item to the Priority Queue. The item is added in such a way that keeps the queue
	 * sorted. For example, if there are two elements of varying importance and a new element is 
	 * added to the queue whose level of priority is between the two existing elements, that
	 * element is inserted in the middle of the queue. 
	 * 
	 * @param item - the item to add
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(E item) {
		// Add the item if the queue is empty.
		if (size == 0) {
			backup[0] = item;
			size++;
			return;
		}
		
		// Compare the two items and add it to the queue if it only has one element.
		if (size == 1) {
			if (compareObjects(backup[0], item) > 0) {
				backup[1] = backup[0];
				backup[0] = item;
			}
			else
				backup[1] = item;
			size++;
			return;
		}
		
		// If the backup array is too small, double its length.
		if (size >= backup.length) {
			E[] arr = (E[]) new Object[size * 2];
			for (int i = 0; i < size; i++) {
				arr[i] = backup[i];
			}
			backup = arr;
		}
		
		// Add the actual item to the array.
		int searchedIndex = binarySearch(item);
		for (int i = size; i > searchedIndex; i--) {
			backup[i] = backup[i-1];
		}
		size++;
		backup[searchedIndex] = item;
	}
	
	/**
	 * Inserts a collection of items into the Priority Queue. These are added in such a way that ensures the
	 * Priority Queue remains sorted. See insert method for an example.
	 * 
	 * @param coll - the collection of items to add.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertAll(Collection<? extends E> coll) {
		E[] arr = (E[]) coll.toArray();
		for (int i = 0; i < coll.size(); i++)
			insert(arr[i]);
	}
	
	/**
	 * Searches the Priority Queue for the item the user is looking for.
	 * 
	 * @param item - the item to search for
	 * @return the result of the comparison between the item at its index and the user's item, or 
	 * false of the item is null.
	 */
	@Override
	public boolean contains(E item) {
		E targetItem = backup[binarySearch(item)];
		if (targetItem == null)
			return false;
		return backup[binarySearch(item)].equals(item);
	}
	/**
	 * Gets the Priority Queue's size
	 * @return the size of the Priority Queue
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Checks whether the Priority Queue is empty. 
	 * @return true if the Priority Queue has a size of 0.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes all of the elements from the queue. This is an irreversible action, 
	 * so be careful when using this method.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		E[] emptyArr = (E[]) new Object[5];
		backup = emptyArr;
		size = 0;
		
	}
	
	private int binarySearch(E value) {
		int high = size - 1, low = 0;
		int mid = (high + low) / 2;
		
		while (low <= high) {
			mid = (high + low) / 2;
			if (backup[mid].equals(value))
				return mid;
			else if (compareObjects(backup[mid], value) > 0)
				high = mid - 1;
			else
				low = mid + 1;
			
		}
		
		if (high >= 0 && compareObjects(value, backup[high]) >= 0)
			return high + 1;
		return mid;
	}
	
	@SuppressWarnings("unchecked")
	private int compareObjects(E o1, E o2) {
		if (cmp != null)
			return cmp.compare(o1, o2);
		return ((Comparable<? super E>)o1).compareTo(o2);
	}

	@Override
	public Iterator<E> iterator() {
		return new PQIterator();
	}
	
	protected class PQIterator implements Iterator<E> {
		
		private int index;
		private boolean canRemove;
		
		public PQIterator() {
			index = 0;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public E next() {
			canRemove = true;
			if (!hasNext())
				throw new NoSuchElementException();
			return backup[index++];
		}
		
		@Override
		public void remove() {
			if (!canRemove)
				throw new IllegalStateException();
			canRemove = false;
			for (int i = index; i < size-1; i++) {
				backup[i] = backup[i+1];
			}
			deleteMax();
			index--;
		}
	}
}
