// Eeshan Londhe, Student ID: 1227852, CSE 373, Winter 2015
// Homework #5, March 1, 2015
//
// Test.java
//
// This class programs takes two different files of text to calculated Total Square Error and 
// the Most Different Word.

public class Test {

	// Prints out total square error and most different word
	public static void main(String[] args) {
		FileInput.init();
		
		//TODO Initialize the hash tables 
		String[] shakespeare = FileInput.readShakespeare();
		String[] baconWords = FileInput.readBacon();
		ChainingHash shake = new ChainingHash(); // will store shakespeare
		QPHash bacon = new QPHash(); // will store bacon
		
		//TODO Use the FileInput functions to read the two files.
		// Input the elements of those two files into two hash tables,
		// one file into a ChainingHash object and the other into a QPHash object.
		
		// Inserts words from Shakespeare into hash table.
		for (int i = 0; i < shakespeare.length; i++) {
			shake.insert(shakespeare[i]);
		}
		
		// Inserts words from bacon into hash table
		for (int i = 0; i < baconWords.length; i++) {
			bacon.insert(baconWords[i]);
		}		
		
		//TODO Initialize necessary variables for calculating the square error
		// and most distant word.
		String shakeWord = shake.getNextKey(); // word in shakespeare
		String baconWord = bacon.getNextKey(); // word in bacon
		double sumSquareErr = 0; // total square error
		double largestDiff = 0; // largest difference in square error
		String mostDiff = ""; // word with largest difference in square error
		
		//TODO Iterate through the first hash table and add sum the squared error
		// for these words.
		while (shakeWord != null) {
			double shakeWordCount = shake.findCount(shakeWord) / (double)shakespeare.length;
			double baconWordCount = bacon.findCount(shakeWord) / (double)baconWords.length;
			double diff = shakeWordCount - baconWordCount;
			diff = diff*diff;
			if (diff > largestDiff) {
				largestDiff = diff;
				mostDiff = shakeWord;
			}
			sumSquareErr += diff;
			shakeWord = shake.getNextKey();
		}
		
		//TODO Find  words in the second hash table that are not in the first and add their squared error
		// to the total
		while (baconWord != null) {
			if (shake.findCount(baconWord) == 0) {
				double baconNum = bacon.findCount(baconWord) / (double)baconWords.length;
				baconNum = baconNum*baconNum;
				if (baconNum > largestDiff) {
					largestDiff = baconNum;
					mostDiff = baconWord;
				}
				sumSquareErr += baconNum;
			}
			baconWord = bacon.getNextKey();
		}
				
		//TODO Print the total calculated squared error for the two tables and also the word with the highest distance.
		System.out.println("Total Square Error: " + sumSquareErr);
		System.out.println("Most different word: " + mostDiff);
	}
}