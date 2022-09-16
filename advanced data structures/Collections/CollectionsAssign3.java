/*
Authour Name : Qabas Imbewa
File Name    : CollectionsAssign3.java
Description  : This is a program that accesses a list of students and their corresponding movies, then arranges them so that each movie
               is to be displayed above the group of students associated with it. As well, the names are sorted according to the last name.
 */
import java.util.*;
import java.io.*;

public class CollectionsAssign3{
    public static void main(String [] args) throws IOException{
        File out = new File("data/movies");
        out.createNewFile();
        PrintWriter outFile = new PrintWriter(new BufferedWriter (new FileWriter (out)));
        Scanner inFilePicks = new Scanner(new BufferedReader(new FileReader("data/picks.txt")));

        Map<String, TreeSet<String>> picks = new HashMap<>();

        while(inFilePicks.hasNextLine()){
            String [] line = inFilePicks.nextLine().split(",");
            String name = line[1] + ", " + line[0]; // last, first, so that the treemap sorts it correctly
            String movie = line[2];
            if(!picks.containsKey(movie)){ // if the movie is not already there, add it
                TreeSet<String> names = new TreeSet<String>();
                picks.put(movie, names);
            }
            picks.get(movie).add(name); // add the name to the treeSet containing the names associated with that movie
        }

        for(Map.Entry<String, TreeSet<String>> entry : picks.entrySet()){
            outFile.println(entry.getKey()); // add the movie to the file once
            for(String name : entry.getValue()){
                outFile.println(name); // add all the names
            }
        }
        outFile.close();
    }
}
