package cs211;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

/**
 * Junit tests for Junit v4 (aka, org.junit )
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HiggsTestv4 {
	
	@Rule public TestName name = new TestName();

    TestTable ht;   // handle for the test subject
	

	/** 
	 * Adds testing methods to students defined class to work with these JUnit tests.   Might 
	 * need to change the visibility of the students variables to "protected" in 
	 * order to get this to work.  
	 */
	public class TestTable extends QuadraticHashTable {

		
	    /** constructor should delegate to the students constructor. Assumes
	     * the student's implementation has a no-argument default constructor **/
		public TestTable() {
		    super();    //call student's constructor...with correct parameters to match student
		}
		
	    /** constructor should delegate to the students constructor. Assumes 
	     * the student's implementation has a one argument constructor that
		 * accepts a handle on a hashing helper for computing the index of 
		 * the base slot using the hashing algorithm.
	     **/
		public TestTable(Hasher hasherHelper) {
		    super(hasherHelper);    //call student's constructor...with correct parameters to match student
		}		

		
	    /** 
	     * getter method for testing to return a handle on the students hash table. 
	     */
		public Bucket[] table() {
			return table;      // change to match the students array instance variable
		}


	    /** 
	     * Dumps/outputs the students table to console so we can see the 
	     * hash table during testing.  If the other methods above works, this 
	     * method need not be altered
	     */ 
		public void dump(String msg) {
			
			HtBucket[] arr = table();
			
			System.out.println("\n\n"+name.getMethodName()+"----[ "+msg +" ]----");
			System.out.println("table has "+arr.length+" slots.");
			for (int i = 0; i< arr.length; i++) {
				if (arr[i] != null)
					System.out.println(String.format("[%2d] %s -> %s ", i, arr[i].getKey(), arr[i].getValue()));
				else
					System.out.println(String.format("[%2d] - ", i));
			}
			System.out.println("\n\n----  ----");
			
		}
		
	    /** Returns the location (index) of the item in the students hashtable using
	     * an exhaustive search....just for testing. 
	     * If the other methods  above works, this method need not be altered.  
	     * Returns -1 if the key is not found in any of the entries.
	     */
		public int indexOf(String key) {
			
			HtBucket[] arr = table();
			
			for(int i=0; i<arr.length; i++) {
				if (arr[i] == null) continue;
				if (key.equals(arr[i].getKey())) return i; 
			}
			
			return -1;
		}
		
	}
	


	
	
    /**
	 * A helper testing function for checking that certain strings
	 * are found among the table bucket keys.
	 * @param keys
	 * @param strs
	 */
	private void assertPresent(Object[] keys, String ...strs) {
		List<String> expected = new ArrayList<>(Arrays.asList(strs));
		
		for (Object k : keys) {
			if (k == null) Assert.fail("null found in keys");
			assertTrue("unexpected key ["+k+"]",expected.contains(k));
			expected.remove(k);
		}
		assertTrue("extra keys found", expected.isEmpty());
	}

	
	
	/**
	 * Private helper testing method for checking for prime by
	 * checking for divisors larger than 1 not including number.
	 * 
	 * @author https://www.baeldung.com/java-prime-numbers 
	 * @param number
	 * @return true if number is prime
	 */
	private boolean isPrime(int number) {
	    return number > 1 
	      && IntStream.rangeClosed(2, (int) Math.sqrt(number))
	      .noneMatch(n -> (number % n == 0));
	}

	
	/** 
	 * Runs BEFORE each an every test in order to create a new hash table object.  
	 * We call the TestTable constructor which will call the student's 
	 * constructor.
	 */
	@Before
	public void setUp() throws Exception {
		
		ht = new TestTable(new PaStringHasher() );
	}
	
	
	/* -------------------------- clear ----------------------------- */
	
	@Test
	public void test_clear_afterPuts() {
		
		assertPresent(ht.keys());  // no items with keys present
		
		ht.put("jan","january");
		assertEquals(1,ht.size());
	
		ht.put("feb","february");
		assertEquals(2,ht.size());
		
		// not all slots are empty
		assertFalse(Arrays.stream(ht.keys()).allMatch(Objects::isNull));
		
		// keys jan and feb are found amoung keys
		assertTrue(Arrays.stream(ht.keys()).anyMatch("jan"::equals));
		assertTrue(Arrays.stream(ht.keys()).anyMatch("feb"::equals));

		// now lets clear;  should return to completely empty all  nulls
		ht.clear();
		
		assertTrue(Arrays.stream(ht.keys()).allMatch(Objects::isNull));
		
		// with no keys present
		assertPresent(ht.keys());
		
		// and size is zero
		assertEquals(0,ht.size());
		
				
	}
	
	
	/* -------------------------- get ----------------------------- */
	
	@Test
	public void test_get_keyFound_andNotFound() throws Exception {
	
		ht.put("jan","january");
		ht.put("feb","february");
		
		ht.dump("after insert jan,feb");
		
		// assertTrue(Arrays.stream(ht.keys()).anyMatch("jan"::equals));
	
		assertEquals("january", ht.get("jan"));
		assertEquals("february", ht.get("feb"));
		
		assertNull(ht.get("mar"));
		
	}
	
	
	
	@Test
	public void test_get_keyFound_andNotFound_equals() throws Exception {
	
		ht.put("jan","january");
		ht.put("feb","february");
		
		ht.dump("after insert jan,feb");
	
		String jankey = new StringBuffer().append('j').append('a').append('n').toString();
		assertEquals("january", ht.get(jankey));
		
	}
	
	
	
	@Test
	public void test_get_whenTableIsEmpty() {
		Object obj = ht.get("foo");
		assertNull(obj);
	}
	
	/* -------------------------- expand ----------------------------- */
	
	@Test
	public void test_expand_withManyItems() throws Exception {
	
		int n1 = ht.table().length;
		assertTrue(isPrime(n1));
		assertTrue("N not congruent to 3 mod 4", n1%4==3);
		
		ht.put("jan","january");
		ht.put("feb","february");
		ht.put("mar","march");
		ht.put("apr","april");
		ht.put("may","may");
		ht.put("jun","june");
		ht.put("jul","july");
		ht.put("aug","august");
		ht.put("sep","september");
		ht.put("oct","october");
		ht.put("nov","november");
		ht.put("dec","december");
		ht.put("tx","texas");
		ht.put("ok","oklahoma"); 
		
		ht.dump("before expansion");
		
		ht.put("ca","california");       // <-- should expand here
		ht.dump("after expansion");
		
		ht.put("wa","washington");
		ht.put("or","oregon");
		
		ht.dump("after insert all keys");
		
		int n2 = ht.table().length;
		assertTrue(isPrime(n2));
		assertTrue("N not congruent to 3 mod 4", n2%4==3);
		assertTrue("table not expanded", n2>n1);
		assertEquals(31,n2);
		
	}
	
	
	/* -------------------------- isEmpty ----------------------------- */ 
	
	@Test
	public void test_isEmpty_afterPutsAndClear() {
		
		assertTrue(ht.isEmpty());
		
		ht.put("jan","january");
		assertEquals(1,ht.size());
		assertFalse(ht.isEmpty());
	
		ht.put("feb","february");
		assertEquals(2,ht.size());
		assertFalse(ht.isEmpty());
		
		ht.clear();
		
		assertEquals(0,ht.size());
		assertTrue(ht.isEmpty());

	}
	
	
	@Test
	public void test_isEmpty_afterPutsAndRemoved() {
		
		assertTrue(ht.isEmpty());
		
		ht.put("jan","january");
		assertEquals(1,ht.size());
		assertFalse(ht.isEmpty());
	
		ht.put("feb","february");
		assertEquals(2,ht.size());
		assertFalse(ht.isEmpty());
		
		ht.dump("isEmpty after insert jan,feb");
		assertTrue("jan not found", ht.indexOf("jan")>=0 );
		assertTrue("feb not found", ht.indexOf("feb")>=0 );
		
		ht.remove("jan");
		assertEquals(1,ht.size());
		assertFalse(ht.isEmpty());
		
		ht.remove("feb");
		assertEquals(0,ht.size());
		assertTrue(ht.isEmpty());

		ht.dump("isEmpty after remove jan,feb...should be empty");
		assertFalse("jan still after remove", ht.indexOf("jan")>=0);
		assertFalse("feb present after remove ", ht.indexOf("feb")>=0);

	}
	
	@Test
	public void test_isEmpty_whenTableIsEmpty() {
		assertTrue("should be empty when new", ht.isEmpty());
	}
	
	
	/* -------------------------- keys ----------------------------- */
	@Test
	public void test_keys_whenTableIsEmpty() {
		String[] emptyKeys = new String[0];
		assertArrayEquals(emptyKeys, ht.keys());
	}
	
	@Test
	public void test_keys_whenThreeInsertedAndRemoved() throws Exception {
	
		ht.put("jan","january");
		ht.put("feb","february");
		ht.put("mar","march");
		
		ht.dump("after insert jan,feb,mar");
		
		assertPresent(ht.keys(),"jan","feb","mar");
		
		ht.remove("jan");
		ht.remove("feb");
		
		assertPresent(ht.keys(),"mar");
		
		ht.remove("mar");
		
		assertPresent(ht.keys());
		
	}
	
	/* -------------------------- put ----------------------------- */

	@Test
	public void test_put_afterVACATED() {
		
		ht.put("jan","january");
		ht.put("feb","february");
		

		int idx = ht.indexOf("jan");
		assertNotEquals(-1,idx);  // should be present in a slot

		ht.remove("jan");
		
		// "jan" bucket should not be found now
		assertEquals(-1, ht.indexOf("jan"));
		ht.dump("after inserting jan,feb and removing jan...only feb remains...jan was at "+idx);
		
		// now reinsert jan
		ht.put("jan","january");
		int idx2 = ht.indexOf("jan");
		
		// should be in same/original slot
		assertEquals("jan not landing is same slot",idx,idx2);
		
		ht.dump("after inserting jan,feb and re-inserting jan...jan was at "+idx);
	}

	@Test
	public void test_put_whenTableIsEmpty()  {
		
		assertTrue("table should be empty", ht.isEmpty());
		
		ht.put("jan","january");
		
		ht.dump("after insert jan");
		
		assertPresent(ht.keys(),"jan");
		assertPresent(ht.values(),"january");
		
		// or like this....
		
		assertTrue(Arrays.stream(ht.keys()).anyMatch("jan"::equals));
		
		// and this
		
		int idx = ht.indexOf("jan");
		assertEquals("january",ht.table()[idx].getValue());
		
	}

	@Test
	public void test_put_with_Collisions() {
		
		ht.put("jan","january1");
		ht.put("feb","february");
		

		int idx = ht.indexOf("jan");  	// find and remember where "jan" bucket went
		assertNotEquals(-1,idx);		// better be there

		// replace with dummy ... faking a future collision 
		ht.table()[idx] = new Bucket("DUMMY","DUMMY");
				
		assertEquals(-1, ht.indexOf("jan"));	// jan should be missing again
		// ht.dump("after inserting jan,feb and removing jan...only feb remains...jan was at "+idx);
		
		// now reinsert jan ... should be collision with DUMMY; quadratic
		// probing should place in very next slot
		ht.put("jan","january2");
		int idx2 = ht.indexOf("jan");
		assertEquals(idx+1,idx2); // should be located right next door
		
		ht.dump("after inserting with single collision at "+idx);
		
		// replace with dummy again to cause another collision and reinsert jan again.
		ht.table()[idx2] = new Bucket("DUMMY2","DUMMY2");
		ht.put("jan","january3");	
		int idx3 = ht.indexOf("jan");   // where did it land?
	
		// this time, jan should be 4 away from base to the left, with wrap 
		// around in table
		assertEquals("invalid second probe index",(idx-4+ht.table().length)%ht.table().length,idx3);
		
		
		ht.dump("after two collisions from base index "+idx);
		
	}
	
	/* -------------------------- remove ----------------------------- */

	@Test
	public void test_remove_whenTableIsEmpty() throws Exception {
		Object obj = ht.remove("jan");
		assertNull("remove should return when table is empty",obj);
	}
	
	
	@Test
	public void test_remove_withManyItems() throws Exception {
	
		ht.put("jan","january");
		ht.put("feb","february");
		ht.put("mar","march");
		ht.put("apr","april");
		ht.put("may","may");
		ht.put("jun","june");
		ht.put("jul","july");
		ht.put("aug","august");
		ht.put("sep","september");
		ht.put("oct","october");
		ht.put("nov","november");
		ht.put("dec","december");
		
		ht.dump("after insert all keys");
		
		ht.remove("feb");
		ht.dump("after insert all keys, remove feb");
		
		ht.remove("mar");
		ht.dump("after insert all keys, remove mar");
		
		assertPresent(ht.keys(),"jan","apr","may","jun","jul","aug","sep","oct","nov","dec");
		
		
	}
	
	
	/* -------------------------- size ----------------------------- */
	
	@Test
	public void test_size_afterPuts() {
		
		ht.put("jan","january");
		assertEquals(1,ht.size());
	
		ht.put("feb","february");
		assertEquals(2,ht.size());
		
	}
	
	
	@Test
	public void test_size_afterPutsAndRemoves() {
		
		ht.put("jan","january");
		assertEquals(1,ht.size());
	
		ht.put("feb","february");
		assertEquals(2,ht.size());
		
		ht.remove("jan");
		assertEquals(1,ht.size());
		
		ht.remove("feb");
		assertEquals(0,ht.size());
		
		ht.dump("after removing all items");
		
	}
	
	@Test
	public void test_size_whenTableIsEmptySizeIsZero() {
		assertEquals(0,ht.size());
	}
	
	/* -------------------------- values ----------------------------- */

	@Test
	public void test_values_whenTableIsEmpty() {
		Object[] empty = new Object[0];
		assertArrayEquals(empty, ht.values());
	}

	
	@Test
	public void test_values_whenThreeInsertedAndRemoved() throws Exception {
	
		ht.put("jan","january");
		ht.put("feb","february");
		ht.put("mar","march");
		
		ht.dump("after insert jan,feb,mar");
		
		assertPresent(ht.values(),"january","february","march");
		
		ht.remove("jan");
		ht.remove("feb");
		
		assertPresent(ht.values(),"march");
		
		ht.remove("mar");
		
		assertPresent(ht.values());
		ht.dump("after removed jan,feb,mar");
		
		
	}

	
	/* -------------------------- hashing helper ----------------------------- */
	
	
	@Test
	public void testHashingHelper() throws Exception {
		
		PaStringHasher hh = new PaStringHasher();
				
		System.out.println("hash(cat)="+hh.hashKey("cat", 23));
		System.out.println("hash(act)="+hh.hashKey("act", 23));
		System.out.println("hash(tac)="+hh.hashKey("tac", 23));
		
		Assert.assertNotEquals(hh.hashKey("cat", 23), hh.hashKey("act", 23));
		
	}
	


	
	
	
	
	
	
	

}
