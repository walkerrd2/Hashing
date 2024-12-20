package cs211;

/**
 * Classes that implement this interface must have methods that
 * look exactly like these methods.  The interface describe the 
 * bare minimum for a hash table using string keys storing Objects.
 * 
 */
public interface Cs211Hashtable {
	
	public final int INITIAL_N = 23;  // initial size of hashtable
	
	
	/**
	 * This method returns the number of items in the hash table not
	 * including VACATED buckets. This is not the same as the capacity of the
	 * table.
	 * 
	 * @return the number of active items
	 */
	public int size();
	
	
	/**
	 * This method checks if the hash table is empty of active items
	 * 
	 * @return returns true if the table has not active items 
	 * or false otherwise
	 */
	public boolean isEmpty();
	
	
	/**
	 * This method makes and returns the array of current values in 
	 * the hash table.  The array should be just large enough to
	 * hold the bucket values with no null slots. Does not include any
	 * VACATED buckets.
	 * 
	 * @return returns an array of all bucket values in the hash table
	 */
	public Object[] values();
	
	
	/**
	 * This method makes and returns the array of current keys in 
	 * the hash table (not including VACATED buckets).  The array 
	 * should be just large enough to hold the keys with no null slots.
	 * 
	 * @return returns an array of all bucket keys in the hash table
	 */
	public String[] keys();
	
	
	/**
	 * Given the specified key, finds the bucket in the collection 
	 * with a matching key and returns the associated value. 
	 * Or returns null if an bucket with a matching key is not found.
	 * 
	 * @param key identifies the bucket
	 * @return handle on the associated element or null if not successful
	 */
	public Object get(String key);
	
	
	/**
	 * Checks to see if table needs expanded and does so first.
	 * <p>
	 * Locates where the supplied key should be stored.  If 
	 * an bucket with the same key already exists, then replaces
	 * the associated bucket value.  If another bucket with a different
	 * key occupies this slot (a collision), then uses  
	 * probing until an empty or available (VACATED) slot is found.  
	 * Creates an stores an new bucket at the at the desired slot.
	 * 
	 * @param key
	 * @param value
	 * @return the bucket value added if successful or null if not
	 */
	public Object put(String key, Object value);
	
	
	/**
	 * Finds the associated bucket and removes it  
	 * from the table by replacing the bucket with the VACATED constant. 
	 * 
	 * @param key
	 * @return the bucket value removed from the hash table if successful
	 * or null otherwise
	 */
	public Object remove(String key);
	
	
	/**
	 * Sets all table bucket handles in the hash table to null.  An empty
	 * table consists of nothing but null handles. 
	 */
	public void clear();
	
	
	/**
	 * Expands the table to the next appropriate size table array
	 * and rehashes all existing items into the new empty table.  This is 
	 * called by the put algorithm when needed to keep the hashtable 
	 * efficient.
	 */
	public void expand();
	
	
}
