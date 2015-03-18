// Eeshan Londhe, Student ID: 1227852, CSE 373, Winter 2015
// Homework #5, March 1, 2015
//
// ChainingHash.java
//
// This class allows the user to create a Hash Table that handles collisions through chaining.
// The user can store String's and their frequencies in this table.

public class ChainingHash {
		
		private ListNode hashTable[]; // hashTable
		private int keyIndex;
		private ListNode keyNode;
		public static final int DEFAULT_SIZE = 31649; // first prime number after readShakespeare().length
	
		// This class is used to make the hash table of ListNodes
		// Contains a String word and int frequency. Can be used to make a linked node structure.
		private class ListNode {
			private String word;
			private int frequency;
			private ListNode next;
			
			// Pre: Passes in a String word
			// Post: Constructs a ListNode that contains the word passed in.
			public ListNode(String word) {
				this.word = word;
				this.frequency = 1;
			}
		}
	
		// Post: Constructs a ChainingHash table of DEFAULT_SIZE size.
		public ChainingHash(){
			//TODO Implement a default constructor for ChainingHash
			this(DEFAULT_SIZE);
		}
		
		// Pre: Passes in an int startSize
		// Post: Constructs a ChainingHash table of startSize size.
		public ChainingHash(int startSize){
			//TODO Implement a constructor that instantializes the hash array to startSize.
			this.hashTable = new ListNode[startSize];
			this.keyIndex = -1;
			this.keyNode = hashTable[0];
		}

		// Post: Returns the next element of the hash table. Returns null if it is at its end of the table.
		public String getNextKey(){
			//TODO returns the next key in the hash table.
			//You will need external tracking variables to account for this.
			//return hashTable[nextKeyIndex].word;
			if (keyIndex >= hashTable.length) {
				return null;
			}
			if (keyNode != null) {
				if (keyNode.next != null) {
					keyNode = keyNode.next;
				} else {
					keyIndex++;
					if (keyIndex >= hashTable.length) {
						return null;
					}
					while (keyIndex < hashTable.length && hashTable[keyIndex] == null) {
						keyIndex++;
					}
					if (keyIndex >= hashTable.length) {
						return null;
					} else {
						keyNode = hashTable[keyIndex];
					}
				}
			} else {
				keyIndex++;
				if (keyIndex >= hashTable.length) {
					return null;
				}
				while (keyIndex < hashTable.length && hashTable[keyIndex] == null) {
					keyIndex++;
				}
				if (keyIndex >= hashTable.length) {
					return null;
				} else {
					keyNode = hashTable[keyIndex];
				}
			}
			return keyNode.word;
		}
		
		// Pre: Passes in a String keyToAdd
		// Post: Adds the key passed in to the hash table.
		//		 If the key already exists in the table, then it increments the key's counter.
		public void insert(String keyToAdd){
			//TODO Implement insert into the hash table.
			//If keyToAdd is already in the hash table, then increment its count.
			int index = Math.abs(keyToAdd.hashCode() % hashTable.length);
			ListNode temp = hashTable[index];
			if (!contains(keyToAdd)) {
				hashTable[index] = new ListNode(keyToAdd);
				hashTable[index].next = temp;
			} else {
				while (temp != null) {
					if (temp.word.equals(keyToAdd)) {
						temp.frequency++;
						break;
					}
					temp = temp.next;
				}
			}
		}
		
		// Pre: Private helper method for insert(String keyToAdd)
		//		Passes in a String keyToAdd
		// Post: Returns true of key is in the hash table, false otherwise.
		private boolean contains(String keyToAdd) {
			ListNode current = hashTable[Math.abs(keyToAdd.hashCode() % hashTable.length)];
			while (current != null) {
				if (current.word.equals(keyToAdd)) {
					return true;
				}
				current = current.next;
			}
			return false;
		}
		
		// Pre: Passes in a String keyToFind.
		// Post: Returns the number of times the key has been added to the hashTable.
		public int findCount(String keyToFind){
			//TODO Implement findCount such that it returns the number of times keyToFind
			// has been added to the data structure.
			int index = Math.abs(keyToFind.hashCode()) % hashTable.length;
			ListNode current = hashTable[index];
			while (current != null) {
				if (current.word.equals(keyToFind)) {
					return current.frequency;
				}
				current = current.next;
			}
			return 0;
		}
		
		// My Hash Method:
		// Pre: Passes in String keyToHash
		// Post: Returns an int representing the hashCode for the String passed in.
		private int hash(String keyToHash){
			//EXTRA CREDIT: Implement your own String hash function here.
			int hash = 0;
			for(int i = 0; i < keyToHash.length(); i++) {
				hash += Math.abs(keyToHash.charAt(i) ^ keyToHash.length()*(i+1)); // XOR (int)character with string length*(i+1)
			}
			return hash;
		}
}
