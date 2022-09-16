/*
Authour Name : Qabas Imbewa
File Name    : CollectionsAssign2.java
Description  : This is a program that accesses words in a story (it can also be slightly modified to accomodate any input) and
               checks them against a dictionary text file to determine if they are spelled correctly.
 */

import java.util.*;
import java.io.*;

public class CollectionsAssign2{
    public static void main(String [] args) throws IOException{
        Set<String> dict = new HashSet<String>();
        Set<String> story = new HashSet<String>();
        Scanner inFileDict = new Scanner(new BufferedReader(new FileReader("data/dictionary.txt"))); // dictionary
        Scanner inFileStory = new Scanner(new BufferedReader(new FileReader("data/story.txt"))); // story

        while(inFileDict.hasNext()){
            dict.add(inFileDict.next()); // add words from dictionary
        }

        while(inFileStory.hasNextLine()){
            String word = inFileStory.next().replaceAll("[^a-zA-Z]", ""); // strip of everything but letters
            if(word.equals("") == false){
                story.add(word); // add words in story
            }
        }

        for(String word : story){ // check every word in the story for its occurence in the dictionary
            if(dict.contains(word)){
                System.out.println(word + " is spelled correctly.");
            }else{
                System.out.println(word + " is spelled incorrectly.");
            }
        }

        
    }

}
