import java.util.Scanner;

/**
 *
 * On my honor, Numa Fatima Begum, this programming assignment is my own work and 
 * I have not viewed a solution to this problem from another student or online source.
 * I have also not allowed other students to view my code. Also, I will
 * not share my solution in the future on Course Hero, a public GitHub repo,
 * or any other place, since this encourages cheating by others and shows
 * a lack of academic integrity.
 *
 * email address: NumaFatima@utexas.edu 
 * UTEID: nb27777 

 * Program to decrypt a message that has been encrypted with a substitution cipher.
 * We assume only characters with ASCII codes from 32 to 126 inclusive have been encrypted.
 */

public class Decrypt {

	/*
	 * Main method that calls almost all of the other methods to run the program.
	 */
	public static void main(String[] arg) {
		System.out.print( (char)(1 + ((int)'a')));
		Scanner keyboard = new Scanner(System.in);
		String fileName = getFileName(keyboard);
		String encryptedText = DecryptUtilities.convertFileToString(fileName);
		printEncryptedText(encryptedText);
		int[] frequencies = getFrequencyTable(encryptedText);
		// The method call below returns a char array that contains the first best
		// guess at the characters that should replace the characters in the encrypted text
		// based on frequency analysis. This array has a length of 128
		// and uses the index values of the frequencies array as the ASCII codes
		// to get the characters for those codes.
		char[] key = DecryptUtilities.getDecryptionKey(frequencies);
		printDecryptionKey(key);
		getDecryptedText(key, encryptedText);
		changeKeyAndContinue(encryptedText, keyboard, key);
		keyboard.close();
	}

	/*
	 * Method that prints the encrypted text.
	 * This method does not return anything.
	 * Parameter encryptedText is the text in the file that has been converted into a string.
	 */
	public static void printEncryptedText(String encryptedText) {
		System.out.println("The encrypted text is:");
		System.out.println(encryptedText);
	}

	/*
	 * Method that gets the frequency of all the ASCII characters in the encrypted
	 * text and counts the characters from ASCII code 0 to code 127.
	 * Method then puts them in an array called frequencies and returns that array to an array
	 * called int[] frequencies with a length of 128 in the main method.
	 * Method also prints frequencies of the ASCII characters code 32 to 126 in 
	 * the encrypted text.
	 * Parameter encryptedText is the text in the file that has been converted into a string.
	 */
	public static int[] getFrequencyTable(String encryptedText) {
		System.out.println("Frequencies of characters.\nCharacter - Frequency");
		int[] frequencies = new int[128];
		// This loop will run once for each character in the encrypted text and increase
		// the frequencies of those characters at their ASCII code.
		for (int i = 0; i < encryptedText.length(); i++) {
			char value = encryptedText.charAt(i);
			frequencies[value]++;
		}
		// This loop will run once for each value in the frequencies array and print
		// the frequencies of the ASCII characters code 32 to 126 in the encrypted text.
		for (int i = 0; i < frequencies.length; i++) {
			if (i > 31 && i < 127) {
				System.out.println((char)i + " - " + frequencies[i]);
			}
		}
		return frequencies;
	}

	/*
	 * Method that prints the current version of the key for ASCII characters code 32 to 126.
	 * Parameter key is the char array that contains the first best guess at the characters that
	 * should replace the characters in the encrypted text based on frequency analysis.
	 * Method does not return anything.
	 */
	public static void printDecryptionKey(char[] key) {
		System.out.println("\nThe current version of the key for ASCII "
				+ "characters 32 to 126 is: ");
		// This loop will run once for each character in the key array and print out
		// decrypted character and the corresponding encrypted character.
		for (int i = 0; i < key.length; i++) {
			if (i > 31 && i < 127) {
				System.out.println("Encrypt character: " + (char)i +
						", decrypt character: " + key[i]);
			}
		}
	}

	/*
	 * Method that prints the current version of the decrypted text.
	 * Parameter key is is the char array that contains the first best guess at
	 * the characters that should replace the characters in the encrypted text
	 * based on frequency analysis.
	 * Parameter encryptedText is the text in the file that has been converted into a string.
	 * This method returns the decrypted text to method changeKeyAndContinue when it is
	 * called by it.
	 */
	public static String getDecryptedText(char[] key, String encryptedText) {
		System.out.println("\nThe current version of the decrypted text is: \n");
		String decryptedText = "";
		// This loop will run once for each each character in the encrypted text
		// and slowly build up the decrypted text by converting the encrypted
		// character to its corresponding decrypted character using the 
		// key array.
		for (int i = 0; i < encryptedText.length(); i++) {
			decryptedText+=key[encryptedText.charAt(i)];
		}
		System.out.println(decryptedText);
		return decryptedText;
	}
	
	/*
	 * Method that will ask the user if the want to change the key for ASCII characters 32 to
	 * 126. To get their answer, it will call helper method askToChangeKey.
	 * If the user enters 'y' or 'Y' as the first character, it will allow them to change
	 * the key. To change the key, it will call helper method changeKey.
	 * The method will then print out the changed decrypted text by calling
	 * method getDecryptedText.
	 * The method will keep doing these things until the user enters something other than 'y'
	 * or 'Y' as the first letter. When they do, it will call helper method end.
	 * Parameter key is is the char array that contains the first best guess at 
	 * the characters that should replace the characters in the encrypted text
	 * based on frequency analysis.
	 * Parameter encryptedText is the text in the file that has been converted into a string.
	 * Parameter keyboard will allow the user to type in their responses.
	 * This method does not return anything.
	 */
	public static void changeKeyAndContinue(String encryptedText, Scanner keyboard, char[] key) {
		String newDecryptedText = "";
		String lastDecryptedText = "";
		char userAnswer = askToChageKey(keyboard);
		// This loop will run once for each time the user wants to continue changing
		// the key.
		while (userAnswer == 'y' || userAnswer == 'Y') {
			System.out.print("Enter the decrypt character you want to change: ");
			String changeChar = keyboard.nextLine();
			char changeCharacter = changeChar.charAt(0);
			System.out.print("Enter what the character " + changeCharacter +
					" should decrypt to instead: ");
			String decryptToChar = keyboard.nextLine();
			char decryptToCharacter = decryptToChar.charAt(0);
			changeKey(key, changeCharacter, decryptToCharacter);
			System.out.println(changeCharacter + "'s will now decrypt to " + 
			decryptToCharacter + "'s and vice versa.");
			newDecryptedText = getDecryptedText(key, encryptedText);
			lastDecryptedText = newDecryptedText;
			newDecryptedText = "";
			userAnswer = askToChageKey(keyboard);
		}
		end(key, lastDecryptedText);
	}

	/*
	 * Method which will return the user's answer to if they want to change the
	 * key or not. 
	 * Parameter keyboard will allow the user to type in their answer.
	 */
	public static char askToChageKey(Scanner keyboard) {
		System.out.println("Do you want to make a change to the key?");
		System.out.print("Enter 'Y' or 'y' to make change: ");
		String answer = keyboard.nextLine();
		char userAnswer = answer.charAt(0);
		return userAnswer;
	}

	/*
	 * Method that will change the key array by swapping the two letters that the
	 * user wants to change.
	 * Parameter key is the char array that contains the first best guess at 
	 * the characters that should replace the characters in the encrypted text
	 * based on frequency analysis.
	 * Parameters chageCharacter and decryptTOCharacter are the decrypt character the user
	 * wants to change and the character the changeCharacter should decrypt to instead
	 * respectively.
	 * This method does not return anything as array don't need to be returned.
	 */
	public static void changeKey(char[] key, char changeCharacter, char decryptToCharacter) {
		// This for loop will run once for each character in the key array and 
		// swap the two letters that the user wants to change.
		for (int i = 0; i < key.length; i++) {
			if (key[i]== changeCharacter) {
				key[i]= decryptToCharacter;
			}
			else if (key[i]== decryptToCharacter) {
				key[i]= changeCharacter;
			}
		}
	}
	
	/*
	 * Helper method that is called by method changeKeyAndContinue when the user dosen't want
	 * to change the key anymore. 
	 * This method will print the final updated key by calling method printDecryptionKey
	 * and also print the final version of the decrypted text.
	 * Parameter key is is the char array that contains the first best guess at 
	 * the characters that should replace the characters in the encrypted text
	 * based on frequency analysis.
	 * Parameter lastDecryptedText is the final decrypted text after the user said they didn't
	 * want to continue changing the key.
	 * This method does not return anything.
	 */
	public static void end(char[] key, String lastDecryptedText) {
		printDecryptionKey(key);
		System.out.println("\nThe final version of the decrypted text is: \n");
		System.out.print(lastDecryptedText);
		
	}

	// Get the name of file to use.
	public static String getFileName(Scanner keyboard) {
		System.out.print("Enter the name of the encrypted file: ");
		String fileName = keyboard.nextLine().trim();
		System.out.println();
		return fileName;
	}
}
