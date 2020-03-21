package comp250assignment4;

//Name: Samir Ladak
//Student Number: 260667874

import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		this.numBuckets = numBuckets;
		entryCount = 0;
		int i;
		this.buckets = new ArrayList<>();
		for (i=0; i<this.numBuckets; i++)
		{
			HashLinkedList<K,V> e = new HashLinkedList<K,V>();
			buckets.add(e);
		}
		
		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE
		
		if (value == null)
		{
			throw new NullPointerException();
		}
		
		int hash = hashFunction(key);
		HashLinkedList<K,V> e = buckets.get(hash);
		if (e.getListNode(key) != null)
		{
			HashNode<K,V> toReturn = e.remove(key);
			e.add(key, value);
			return toReturn.getValue();
		}
		else 
		{
			e.add(key, value);
			entryCount = entryCount + 1;
			ArrayList<K> keys = keys();
			double numKeys = keys.size();
			if (numBuckets*MAX_LOAD_FACTOR <= numKeys)
			{
				rehash();
			}
		}
		
		//  ADD YOUR CODE ABOVE HERE
		return null;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		
		int hash = hashFunction(key);
		HashLinkedList<K,V> e = buckets.get(hash);
		HashNode<K,V> check = e.getHead();
		int i = 0;
		for (i=0; i<e.size(); i++)
		{
			if (check.getKey().equals(key))
			{
				break;
			}
			else
			{
				check = check.next;
			}
		}
		if (i == e.size())
		{
			return null;
		}
		else if (check.getKey().equals(key))
		{
			return check.getValue();
		}
		
		//  ADD YOUR CODE ABOVE HERE

		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE

		int hash = hashFunction(key);
		HashLinkedList<K,V> e = buckets.get(hash);
		HashNode<K,V> toRemove = e.getHead();
		int i = 0;
		for (i=0; i<e.size(); i++)
		{
			if (toRemove.getKey().equals(key))
			{
				break;
			}
			else
			{
				toRemove = toRemove.next;
			}
		}
		if (i == e.size())
		{
			return null;
		}
		else if (toRemove.getKey().equals(key))
		{
			V toReturn = toRemove.getValue();
			e.remove(key);
			entryCount = entryCount -1;
			return toReturn;
		}

		//  ADD  YOUR CODE ABOVE HERE

		return(null);
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
		
		int oldSize = numBuckets;
		numBuckets = oldSize*2;
		ArrayList<HashLinkedList<K,V>> replacement = new ArrayList<HashLinkedList<K,V>>();
		int x;
		for (x=0; x<numBuckets; x++)
		{
			HashLinkedList<K,V> e = new HashLinkedList<K,V>();
			replacement.add(e);
		}
		int i;
		for (i=0; i<oldSize; i++)
		{
			if (buckets.get(i) != null)
			{
				HashLinkedList<K,V> e = buckets.get(i);
				int j;
				HashNode<K,V> change = e.getHead();
				for (j=0; j < e.size(); j++)
				{
					replacement.get(hashFunction(change.getKey())).add(change.getKey(), change.getValue());
					change = change.next;
				}
			}
		}
		buckets = replacement;
		
		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE BELOW HERE
		
		int i;
		for (i=0; i<numBuckets; i++)
		{	
			HashLinkedList<K,V> e = buckets.get(i);
			HashNode<K,V> toAdd = e.getHead();
			int j;
			for (j=0; j<e.size(); j++)
			{
				listKeys.add(toAdd.getKey());
				toAdd = toAdd.next;				
			}
		}
		return listKeys;

		//   ADD YOUR CODE ABOVE HERE
		
	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE

		int i;
		for (i=0; i<buckets.size(); i++)
		{
			HashLinkedList<K,V> e = buckets.get(i);
			HashNode<K,V> toAdd = e.getHead();
			int j;
			for (j=0; j<e.size(); j++)
			{
				listValues.add(toAdd.getValue());
				toAdd = toAdd.next;
			}
		}
		return listValues;
		
		//   ADD YOUR CODE ABOVE HERE

	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;

		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE

			int i;
			for (i=0; i<buckets.size(); i++)
			{
				HashLinkedList<K,V> e = buckets.get(i);
				HashNode<K,V> toAdd = e.getHead();
				int j;
				for (j=0; j<e.size(); j++)
				{
					allEntries.add(toAdd.getKey(), toAdd.getValue());
					toAdd = toAdd.next;
				}
			}
			
			//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}

