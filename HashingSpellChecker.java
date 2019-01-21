/**
 * Output:
Enter a word.
eys
No matches. Did you mean: 
beys
keys
leys
yes
* 
* 
Enter a word.
leke
No matches. Did you mean: 
eke
lek
leek
BUILD SUCCESSFUL (total time: 3 seconds)
 */

package hashingspellchecker;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class HashingSpellChecker {
    
    public static hashTable table;

    public static void main(String[] args) throws IOException {
       FileInputStream in = null;
       
       table = new hashTable<>();
       
       try{
           in = new FileInputStream("words.txt");
       } finally {
           BufferedReader br = new BufferedReader(new InputStreamReader(in));
           String word;
           int i = 0;
           while((word = br.readLine()) != null) {
               table.put(word, i);
               i++;
           } 
           in.close();
       }

       
       Scanner scanner = new Scanner(System.in);
       System.out.println("Enter a word.");
       String inputWord = scanner.next();
       LinkedList<String> wordList;
       
       if(table.get(inputWord.toLowerCase()) != null) //check dictionary for non-case-sensitive word
           System.out.println("No mistakes found.");
       
       //check if there are similar words. If so, iterate through list and print them.
       else if(!(wordList = similarWords(inputWord.toLowerCase())).isEmpty()) { 
           System.out.println("No matches. Did you mean: ");
           for(String word: wordList) {
               System.out.println(word);
           }
       }
       else {
           System.out.println("That is not an English word. Please try again.");
       }
    }
    
    //returns LinkedList of words that might be similar
    public static LinkedList<String> similarWords(String inputWord) {
        LinkedList<String> similarList = new LinkedList<>();
        String newWord;
        
        for(char letter = 'a'; letter <= 'z'; letter++) { //iterate over alphabet
            
            //I use StringBuilder to append characters to inputWord to find similar words
            newWord = new StringBuilder().append(letter).append(inputWord).toString();
            if(table.get(newWord) != null) {
                similarList.add(newWord);
            }
            
            newWord = new StringBuilder().append(inputWord).append(letter).toString();
            if(table.get(newWord) != null) {
                similarList.add(newWord);
            }
            
        }
        //now trying to remove characters from front and end of word
        newWord = new StringBuilder().append(inputWord).substring(1).toString();
        if(table.get(newWord) != null) {
            similarList.add(newWord);
        }
            
        newWord = new StringBuilder().append(inputWord).substring(0, inputWord.length() - 1).toString();
        if(table.get(newWord) != null) {
            similarList.add(newWord);
        }
        
        for(int i = 0; i < inputWord.length() - 1; i++) {
            
            //swapping adjacent characters in word and seeing if they're in hashtable
            String beginning = new StringBuilder().append(inputWord).substring(0, i);
            String firstSwapped = new StringBuilder().append(inputWord).substring((i + 1), (i + 2));
            String secondSwapped = new StringBuilder().append(inputWord).substring(i, i + 1);
            String end = "";
            if((i + 2) < inputWord.length())
                end = new StringBuilder().append(inputWord).substring(i + 2);
            
            newWord = new StringBuilder().append(beginning).append(firstSwapped).append(secondSwapped).append(end).toString();
            if(table.get(newWord) != null) {
                similarList.add(newWord);
            }
        }
        
        return similarList; //returns list even if it's empty
    }
    
}
