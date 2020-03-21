package comp250assignment4;

//Name: Samir Ladak
//Student Number: 260667874

public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		
		HashNode<K,V> make = new HashNode<K,V>(key, value);
		this.size = this.size + 1;
		if (head == null)
		{
			head = make;
		}
		else 
		{
			make.next = head;
			head = make;
		}
		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE

		HashNode<K,V> current = head;
		int i;
		for (i=0; i<size; i++)
		{
			if (current.getKey().equals(key))
			{
				break;
			}
			current = current.next;
		}
		return current;
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE

		HashNode<K,V> temp = head;
		if (temp == null)
		{
			return null;
		}
		head = head.next;
		size = size - 1;
		return temp;
		
		// ADD CODE ABOVE HERE
		
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE

		HashNode<K,V> current = head;
		HashNode<K,V> temp = null;
		int i;
		if (current == null)
		{
			return null;
		}
		for (i=0; i<size; i++)
		{
			if (current.getKey().equals(key))
			{
				break;
			}
			temp = current;
			current = current.next;
		}
		if (current == head)
		{
			return removeFirst();
		}
		else if (current.getKey().equals(key))
		{
			temp.next = current.next;
			HashNode<K,V> e = new HashNode<K,V>(current.getKey(), current.getValue());
			current = null;
			size = size - 1;
			return e;
		}
		
		// ADD CODE ABOVE HERE
		return null; // removing failed
	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS

	public HashNode<K,V> getHead()
	{
		return this.head;
	}
	
	/*public static void main(String[] args) {
		HashLinkedList<Integer, Integer> list = new HashLinkedList<>();
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);
		list.add(4, 5);
		System.out.println(list.toString());
		System.out.println(list.getListNode(3));
		System.out.println(list.removeFirst());
		System.out.println("Daddy attempts to code");
		System.out.println(list.removeFirst());
		System.out.println("Show Daddy some love");
		System.out.println(list.removeFirst());
		System.out.println("Daddy needs coffee");
		System.out.println(list.removeFirst());
		System.out.println("Daddy had a coffee boat race");
		System.out.println(list.removeFirst());
		System.out.println("Code me Daddy");
		System.out.println(list.removeFirst());
		System.out.println(list.remove(2));
		System.out.println(list.toString());
	}*/
	
	//ADD YOUR HELPER METHODS ABOVE THIS


}

