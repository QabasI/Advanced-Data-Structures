/*
Authour Name : Qabas Imbewa
File Name    : CollectionsAssign.java
Description  : This is a program that reads from a text file and counts the number of times each word appears, along with the percentageof the total file it takes up.
 */


import java.util.*;
import java.io.*;

class CollectionsAssign {
    public static void main(String [] args) throws IOException{
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("data/story.txt")));
        List<String> words = new LinkedList<>();

        while(inFile.hasNext()){
            String word = inFile.next().replaceAll("[^a-zA-Z]", ""); // strip string of everything but alphabetical letters
            if(word.equals("") == false){
                words.add(word);
            }
        }

        Map<String,Integer> freq = new HashMap<String,Integer>();

        for(String word : words){
               if(!freq.containsKey(word)){
                     freq.put(word,1);
               }else{ // maps cannot have duplicate values, so the new one will replace the old one
                     int n = freq.get(word);
                     freq.put(word,n+1);
        }}

        ArrayList<Map.Entry<String, Integer>> array = new ArrayList<>(freq.entrySet()); // to sort based on value in HashMap
        
        // I used various sources on the internet to teach myself about the Comparator interface, so the following lines of code were inspired by that search
        Collections.sort(array, new Comparator() { 
            public int compare(Object object1, Object object2) {
                return ((Map.Entry<String, Integer>) object1).getValue().compareTo(((Map.Entry<String, Integer>) object2).getValue()); // comparing based on values rather than keys
            }
        });

        Collections.reverse(array); // was in ascending, put in descending order
        
        for (Map.Entry<String, Integer> entry : array) {
            System.out.println(((Map.Entry<String, Integer>) entry).getKey() + " : " + ((Map.Entry<String, Integer>) entry).getValue()); // print them out in descending order according to frequency
        }
        
    }
}