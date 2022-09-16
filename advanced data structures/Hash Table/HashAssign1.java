/*
Authour Name : Qabas Imbewa
File Name    : HashAssign1.java
Description  : This program allows the user to enter 8 letters and returns all the words in a text file called "dictionary.txt"
               that can be made from those letters.
*/

import java.io.*;
import java.util.*;

public class HashAssign1 {
    public static void main(String [] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("data/dictionary.txt")));
        boolean done = false;
        String letters = "";
        while(done == false){ // enter the letters and check that they are valid
            System.out.println("Enter a letter: ");
            String letter = scanner.nextLine();
            if(letter.length() != 1 || Character.isDigit(letter.charAt(0))){
                System.out.println("That wasn't a single letter. ");
            }
            else if(Character.isDigit(letter.charAt(0)) == false){
                letters += letter;
            }
            if(letters.length() == 8){done = true;}
        }
        scanner.close();

        HashTable<String> table = new HashTable<String>();
        ArrayList<String> goodWords = new ArrayList<String>(); // words that are made from the inputted letters

        while(inFile.hasNextLine()){ // go through every word in the dictionary
            goodWords.add(permute(table, inFile.nextLine(), 0, letters));
        }
        for(String word : goodWords){
            if(word.equals("") == false){
                System.out.println(word);
            }
        }
    }

    public static String permute(HashTable<String> table, String word, int letter, String letters){
        if(word.length() != 8){ // if the word isn't exactly 8 letters, it can not possibly work
            return "";
        }
        if(letter > word.length()){ // cannot go past the end of the word
            return "";
        }
        if(letters.contains(""+word.charAt(letter))){ // if a letter matches
            if(Collections.frequency(table.toArray(), ""+word.charAt(letter)) < letters.replaceAll("[^"+word.charAt(letter)+"]", "").length()){ // check frequency (if it appears in the word more than in the inputted letters, then it can not work)
                table.add(""+word.charAt(letter));
                if(letter == 7){ // if it is 8 letters, it is a valid word
                    return word;
                }
                return permute(table, word, letter+=1, letters);
            }
        }
        return "";
    }
}
