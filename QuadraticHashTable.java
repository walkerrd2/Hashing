package cs211;

import cs211.Bucket;
import cs211.Cs211Hashtable;
import cs211.Hasher;

import java.util.Arrays;

/**
 * This class implements the Cs211HashTable interface using
 * quadratic probing. This is where all the methods to probe
 * the hash table will be created.
 */
public class QuadraticHashTable implements Cs211Hashtable {

    // Instance variables for our Bucket class, VACATED handle, Hasher class, and initial table value
    protected Bucket[] table;
    public final Bucket VACATED = new Bucket("vacated", -1);
    protected Hasher myHelper;
    public int NVal = INITIAL_N;

    // First constructor: no argument and initializes Hasher myHelper
    public QuadraticHashTable(){
        this.myHelper = new PaStringHasher();
        this.table = new Bucket[NVal];
    }

    // Second constructor: one argument, initilizes Bucket[] table and myHelper
    public QuadraticHashTable(Hasher helper){
        this.table = new Bucket[NVal];
        myHelper = helper;
    }

    // This method will return the size of the hash table.
    // The size will be determined if a bucket is not null and not vacated.
    @Override
    public int size() {
        int size = 0;
        for(int i =0; i<table.length; i++){
            if(table[i] != null && table[i] != VACATED){
                size++;

            }
        }
        return size;
    }

    // This method will loop through table to see if a bucket is empty.
    // If a bucket is not null and not empty, return will false, otherwise,
    // the bucket is empty
    @Override
    public boolean isEmpty() {
        for(int i = 0; i < table.length; i++){
            if(table[i] != null && table[i] != VACATED){
                return false;
            }
        }
        return true;
    }

    // This method will loop through has table and if
    // bucket value is not null and not vacated, the bucket value will be stored
    // at specific index in the temporary array. Method will then return the array
    // after iteration is finished looping
    @Override
    public Object[] values() {
        Object[] array = new Object[size()]; //Temporary array from Object class that takes in size() method as its size
        int idx = 0; //idx will be used as our temporary array's index
        for(int i = 0; i < table.length; i++){
            if(table[i] != null && table[i] != VACATED){
                array[idx] = table[i].getValue();
                idx++;
            }
        }
        return array;
    }

    // Like the values() method, this method serves the same purpose, except
    // it is iterating and returning String (keys) instead of values.
    @Override
    public String[] keys() {
        String[] arr = new String[size()]; // Temporary array
        int index = 0; // temporary array's index
        for(int i = 0; i < table.length; i++){
            if(table[i] != null && table[i] != VACATED){
                arr[index] = table[i].getKey();
                index++;
            }
        }
        return arr;
    }

    // This method will get value of String key. If the index of that key
    // is not null and not vacated, the value is return, otherwise null
    @Override
    public Object get(String key) {
        int idx = findSlot(key, false);
        return table[idx] != null && table[idx] != VACATED ? table[idx].getValue():null;
    }

    // This method will take in argument String key and Object value. First
    // it will check if the load factor is greater than 0.60, if so
    // table will be expanded. Then, the index will be set to find slot of the given
    //key. The method will return the table of the index and set it to new Bucket with
    // new key and the value. The key and value will be put into that new bucket
    @Override
    public Object put(String key, Object value) {
        if(loadFactor()>0.60){
            expand();
        }
        int idx = findSlot(key,true);
        return table[idx] = new Bucket(key,value);
    }

    // This method will remove key from bucket by finding the slot in which the
    // key is stored in. If the index in the table isn't null and isn't vacated,
    // it will return the value from that slot and then set that slot to vacated
    // and return the value. The method will return null.
    @Override
    public Object remove(String key) {
        int idx = findSlot(key,false);
        if(table[idx] != null && table[idx] != VACATED){
            Object value = table[idx].getValue();
            table[idx] = VACATED;
            NVal--;
            return value;
        }
        return null;
    }

    // This method will clear the whole hash table
    @Override
    public void clear() {
        for(int i = 0; i < table.length; i++){
            table[i]=null;
        }
    }

    // This method will expand the hash table.
    // The new size will be set to the next prime number (31). Then, the current table
    // will be set to a new bucket with the new size.
    @Override
    public void expand() {
        int resize = nextPrimeNum(table.length+4);
        Bucket[] currentTable = table;
        table = new Bucket[resize];
        NVal = 0;
        for(Bucket bucket : currentTable){
            if(bucket != null && bucket != VACATED){
                put(bucket.getKey(), bucket.getValue());
            }
        }
    }

    // This method will iterate through the hash table
    // and will find the slot index of the key it is
    // searching for.
    public int findSlot(String key, boolean insert){
        int hash = myHelper.hashKey(key, table.length);
        int idx = hash;
        int i = 0;
        while(table[idx] != null && (table[idx] == VACATED || !table[idx].getKey().equals(key))){
            idx = (hash+(int)Math.pow(-1,i+1)*(i*i)) % table.length;
            if(idx<0){
                idx+= table.length;
            }
            i++;
        }
        return idx;
    }

    // Method will calculate the load factor
    public double loadFactor(){
        return (double)NVal/ table.length;
    }

    // This method will check and see if the number passed
    // is prime or not.
    public boolean isPrime(int num){
        if(num<=1){
            return false;
        }
        for(int i = 2; i <= Math.sqrt(num); i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    // This method will calculate the next prime number
    // and will return that number.
    public int nextPrimeNum(int start){
        int num = start % 4 == 3 ? start : start+(3-start%4);
        while (!isPrime(num)){
            num+=4;
        }
        return num;
    }



}
