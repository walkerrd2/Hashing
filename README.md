This lab focuses on implementing a quadratic probing hash table following the Cs211Hashtable interface. A PaStringHasher is created for polynomial accumulation hashing and a QuadraticHashTable that dynamically resizes when the load factor exceeds 0.60. The table size must always be a prime number congruent to 3 mod 4 to ensure efficient probing.

Key Features:
Open Addressing & Quadratic Probing for collision resolution,
Dynamic Resizing based on load factor,
Bucket-Based Storage for key-value pairs,
JUnit Testing for validation,

The implementation must be from scratch, without using Java’s built-in Hashtable
