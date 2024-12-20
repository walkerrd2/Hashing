package cs211;

/**
 * Objects that implement this interface can be used for buckets in our hashtable.
 * 
 * @author mahiggs
 *
 */
public interface HtBucket {

	Object getValue();

	String getKey();

	void setValue(Object value);

	void setKey(String key);

}