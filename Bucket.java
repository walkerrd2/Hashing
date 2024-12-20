package cs211;

import cs211.HtBucket;

/**
 * The Bucket class will be implemented from the
 * HtBucket interface. A hash table is an array of Buckets
 * so the methods in this class are simply getter and setter
 * methods to get and set the values and key in each bucket
 * of the hash table.
 */
public class Bucket implements HtBucket {

    // Handles on key and myVal from Object class
    public String key;
    public Object myVal;

    // Constructor and initialize variables
    public Bucket(String key, Object value){
        this.key = key;
        myVal = value;
    }


    // The next 4 methods are getter and setter methods
    // implemented from the HtBucket interface
    @Override
    public Object getValue() {
        return myVal;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setValue(Object value) {
        myVal = value;
    }

    @Override
    public void setKey(String key) {
        this.key=key;
    }
}
