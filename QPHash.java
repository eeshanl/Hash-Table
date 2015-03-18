// Eeshan Londhe, Student ID: 1227852, CSE 373, Winter 2015
// Homework #5, March 1, 2015
//
// QPHash.java
//
// This class allows the user to create a Hash Table that handles collisions through Quadratic Probing.
// The user can store String's and their frequencies in this table.

public class QPHash {
		
		private wordNode hashTable[]; // hashTable
		private int keyIndex;
		public static final int DEFAULT_SIZE = 210671; // first prime after 4*readBacon().length
		
		// This class is used to make the hash table of wordNodes
		// Contains a String word and int frequency.
		private class wordNode {
			private String word;
			private int frequency;
			
			// Pre: Passes in a String word
			// Post: Constructs a ListNode that contains the word passed in.
			public wordNode(String word) {
				this.word = word;
				this.frequency = 1;
			}
		}
		
		// Post: Constructs a QPHash table of DEFAULT_SIZE size.
		public QPHash(){
			//TODO Implement a default constructor for QPHash
			this(DEFAULT_SIZE);
		}
		
		// Pre: Passes in an int startSize
		// Post: Constructs a QPHash table of startSize size.
		public QPHash(int startSize){
			//TODO Implement a constructor that instantializes the hash array to startSize.
			hashTable = new wordNode[startSize];
			keyIndex = -1;
		}

		// Post: Returns the next element of the hash table. Returns null if it is at its end of the table.
		public String getNextKey(){
			//TODO returns the next key in the hash table.
			//You will need external tracking variables to account for this.

			if (keyIndex >= hashTable.length) {
				return null;
			}
			keyIndex++;
			while (keyIndex < hashTable.length && hashTable[keyIndex] == null) {
				keyIndex++;
			}
			if (keyIndex < hashTable.length) {
				return hashTable[keyIndex].word;
			} else {
				return null;
			}
		}
		
		// Pre: Passes in a String keyToAdd
		// Post: Adds the key passed in to the hash table.
		//		 If the key already exists in the table, then it increments the key's counter.
		public void insert(String keyToAdd){
			//TODO Implement insert into the hash table.
			//If keyToAdd is already in the hash table, then increment its count.
			int collisions = 1;
			int index = Math.abs(keyToAdd.hashCode()) % hashTable.length;
			while (hashTable[index] != null && !hashTable[index].word.equals(keyToAdd)) {
				index = nextIndex(collisions, index);
			}
			if (hashTable[index] != null && hashTable[index].word.equals(keyToAdd)) {
				hashTable[index].frequency++;
			} else {
				hashTable[index] = new wordNode(keyToAdd);
			}
		}
		
		// Private helper method for insert(String keyToAdd) and findCount(String keyToFind)
		// Pre: Passes in an int collisions, and int index
		// Post: Returns the next index in the table.
		private int nextIndex(int collisions, int index) {
			int nextIndex = (collisions*collisions + index) % hashTable.length;
			collisions++;
			return nextIndex;
		}
		
		// Pre: Passes in a String keyToFind.
		// Post: Returns the number of times the key has been added to the hashTable.
		public int findCount(String keyToFind){
			//TODO Implement findCount such that it returns the number of times keyToFind
			// has been added to the data structure.
			int collisions = 1;
			int index = Math.abs(keyToFind.hashCode()) % hashTable.length;
			while (hashTable[index] != null && !hashTable[index].word.equals(keyToFind)) {
				index = nextIndex(collisions, index);
			}
			if (hashTable[index] != null && hashTable[index].word.equals(keyToFind)) {
				return hashTable[index].frequency;
			} else {
				return 0;
			}
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
