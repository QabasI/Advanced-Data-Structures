/*
Authour Name : Qabas Imbewa
File Name    : CollectionsAssign4.java
Description  : This is a program that accesses a text file with a series of license plates and their associated "offences". Each offence is
               stored in a custom object called Offence that contains a date and time, along with an initial. The program runs until 3
               is entered to exit, and the user can choose to view offences associated with a plate, or add an offence to a plate.
 */
import java.util.*;
import java.io.*;

public class CollectionsAssign4 {
    public static void main(String [] args) throws IOException{
        Map<String, LinkedList<Offence>> map = new HashMap<String, LinkedList<Offence>>(); // each plate is unique and has a list of offences
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("data/cars.txt"))); // the text file

        int cars = inFile.nextInt(); // number of cars
        inFile.nextLine();

        for(int i = 0; i < cars; i++){
            String plate = inFile.nextLine();
            int offencesNum = Integer.parseInt(inFile.nextLine()); // number of offences 
            LinkedList<Offence> offences = new LinkedList<>();

            for(int x = 0; x < offencesNum; x++){ // loop through each offence
                Offence offence = new Offence(inFile.nextLine(), inFile.nextLine());
                offences.add(offence); // add each offence to the list
            }

            map.put(plate, offences); // add the plate and its list of offences to the map
        }

       boolean go = true;
       int SHOW = 1; // magic numbers
       int ADD = 2;
       int EXIT = 3;

        while(go){
            System.out.println("Choose an option [1, 2, 3]:\n1. Show all offences that belong to a plate \n2. Add an offence\n3. Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            if(choice == EXIT){ // exit
                go = false;
                scanner.close();
            }
            else if(choice == SHOW){
                System.out.println("Enter a license plate: ");
                String plate = scanner.nextLine();
                if(map.containsKey(plate)){
                    for(Offence offence : map.get(plate)){
                        System.out.println(offence);
                    }
                }
            }
            else if(choice == ADD){
                System.out.println("Enter a license plate: ");
                String plate = scanner.nextLine();

                System.out.println("Enter a date and time: ");
                String dateAndTime = scanner.nextLine();

                System.out.println("Enter your initials: ");
                String initials = scanner.nextLine();

                if(map.containsKey(plate)){
                    map.get(plate).add(new Offence(dateAndTime, initials));
                }else{ // if the plate doesn't already exist, make it
                    map.put(plate, new LinkedList<>());
                    map.get(plate).add(new Offence(dateAndTime, initials));
                }
            }

            PrintWriter outFile = new PrintWriter(new BufferedWriter (new FileWriter ("data/cars.txt"))); // make a new file and write to it
            outFile.println(map.size());

            for(Map.Entry<String, LinkedList<Offence>> entry : map.entrySet()){ // update the text file
                outFile.println(entry.getKey());
                outFile.println(entry.getValue().size());
                for(Offence offence : entry.getValue()){
                    outFile.println(offence.dateAndTime);
                    outFile.println(offence.initials);
                }
            }

            outFile.close();

        }
    }
}

class Offence {
    // custom class storing offences
    public String dateAndTime, initials;

    public Offence(String dateAndTime, String initials){
        this.dateAndTime = dateAndTime;
        this.initials = initials;
    }

    @Override
    public String toString(){
        return dateAndTime + "\n" + initials;
    }
}
