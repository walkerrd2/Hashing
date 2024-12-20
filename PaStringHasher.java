package cs211;

import cs211.Hasher;

/**
 * This class will implement the Hasher interface.
 * The PaStringHasher class will hash a String key
 * and will produce the base slot index value in the
 * proper range for a given table size.
 */

public class PaStringHasher implements Hasher {
    int hash = 0; //handle on hash

    // This method is implemented from the Hasher interface.
    // Will loop through and hash String key.
    @Override
    public int hashKey(String key, int N) {
        for(int i = 0; i < key.length(); i++){
            hash = (31*hash+key.charAt(i))%N; //31 is from 27 (initial size of table) plus 4. 31 new prime number
        }
        return hash;
    }
}
