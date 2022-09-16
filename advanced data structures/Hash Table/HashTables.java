/*
Authour Name : Qabas Imbewa
File Name    : HashTables.java
Description  : This is a class that functions as a HashTable from scratch. It can store any type. It has a default maximum load of 70%.
               It is able to scale up and down in order to accomodate a given load between 0.1 and 0.8. Values are stored in positions
               depending on their hashcodes in Linked Lists at those spots.
 */

import java.util.*;

class HashTables {
  public static void main(String[] args) {
    HashTable<Integer>nums = new HashTable<Integer>();
    nums.add(432);
    nums.add(654);
    nums.add(4);
    nums.add(3212);
    nums.add(82653);
    nums.add(2543);
    nums.add(291);
    nums.add(2786);
    nums.add(2);
    nums.add(492);
    System.out.println(nums);
    nums.remove(432);
    System.out.println(nums);
    System.out.println(nums.contains(432));
    System.out.println(nums.getLoad());
    nums.setLoad(0.6);
    System.out.println(nums.getLoad());
    nums.setMaxLoad(0.8);
    nums.setLoad(0.7);
    System.out.println(nums.getLoad());
    System.out.println(nums.toArray());
  }
}

class HashTable<T>{
  private ArrayList<LinkedList<T>>table;
  private int items;
  private double maxLoad;

  public HashTable(){
    maxLoad = 0.7; // default load is 70%
    fillTable(10); // default number of spots is 10
  }
  
  public void setMaxLoad(double load){ // can change the max load
    if(load <= 0.8 && load >= 0.1){ // must be between 0.1 and 0.8 (inclusive)
      maxLoad = load;
      if(getLoad() > maxLoad){ // if the load is greater than the new max, resize the table by making it larger
        resize(10);
      }
    }
  }

  public void setLoad(double percent){ // resize the table (grow or srhink) in order to have a specific load
    if(percent <= maxLoad){  // as long as the load is less than the max load, it can be done
      if(percent > getLoad()){
        resize(getLoad()/percent); // shrink
      }else{
        resize(percent/getLoad()); // grow
      }
    }
  }

  public void fillTable(int n){
    table = new ArrayList<LinkedList<T>>();
    items = 0;
    for(int i=0; i<n; i++){
      table.add(null);
    }
  }
  public void resize(double factor){
    ArrayList<LinkedList<T>>old = table;
    fillTable((int)Math.ceil(table.size()*factor)); // approximate number of spots
    for(LinkedList<T> lst : old){
      if(lst != null){
        for(T val : lst){
          add(val);
        }
      }
    }
  }
  
  public void add(T val){ // add a value to the hashtable
    int spot = Math.abs(val.hashCode()) % table.size(); // the spot is based on the hashcode
    if(table.get(spot) == null){ // if there is not already a linked list there, make one
      table.set(spot, new LinkedList<T>());
    }
    table.get(spot).add(val); // add the value to the linked list at that spot
    items++;
    if(items/table.size() >= maxLoad){
      resize(10); // resize if needed
    }
  }

  public void remove(T val){
    int spot = Math.abs(val.hashCode()) % table.size(); // find spot
    if(table.get(spot) != null && table.get(spot).contains(val)){
      table.get(spot).remove(val); // check if it exists in the table and if it does, delete it
    }
    items--;
  }

  public boolean contains(T val){ // return true if the table contains the value
    int spot = Math.abs(val.hashCode()) % table.size();
    if(table.get(spot) != null && table.get(spot).contains(val)){
      return true;
    }
    return false;
  }

  public boolean contains(int code){ // return true if the table contains an object with a given hashcode
    int spot = Math.abs(code) % table.size();
    if(table.get(spot) != null){
      for(T item : table.get(spot)){
        if(item.hashCode() == code){
          return true;
        }
      }
    }
    return false;
  }
  
  public T get(int code){ // return the object at a given hashcode
    int spot = Math.abs(code) % table.size();
    if(table.get(spot) != null){
      for(T item : table.get(spot)){
        if(item.hashCode() == code){
          return item;
        }
      }
    }
    return null;
  }

  public double getLoad(){
    return (double)items/(double)table.size();
  }

  public ArrayList<T> toArray(){ // create an array of all the objects in the hashtable
    ArrayList<T> array = new ArrayList<T>(0);
    for(LinkedList<T> lst : table){
      if(lst != null){
        for(T item : lst){
          array.add(item);
        }
      }
    }
    return array;
  }

  public String toString(){
    String ans = "";
    for(LinkedList<T> lst : table){
      if(lst != null){
        for(T val : lst){
          ans += val + ", ";
        }
      }
    }
    if(ans != ""){
      ans = ans.substring(0, ans.length()-2);
    }
    return "{" + ans + "}";
  }
}
