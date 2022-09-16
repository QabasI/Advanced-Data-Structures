/*
Authour Name : Qabas Imbewa
File Name    : LinkedLists.java
Description  : This is a class that functions as a Linked List from scratch. The list is doubly-linked, and the LList class
               contains many useful methods that make use of the doubly-linked structure. Elements can be added to, deleted
               from, and rearranged (reversed). This Linked List can implement Stacks through the methods push and pop, Queues
               through enqueue and dequeue, and it can even perform sorted insert. 
 */

class LinkedLists {
  public static void main(String[] args) {
    // testing
    LList nums = new LList();  
    nums.add(90);
    nums.add(79);
    nums.add(42);
    nums.add(10);
    System.out.println(nums);
    nums.enqueue(15);
    System.out.println(nums);
    nums.dequeue();
    System.out.println(nums);
    nums.dequeue();
    System.out.println(nums);
    nums.push(5);
    System.out.println(nums);
    nums.reverse();
    System.out.println(nums);
    nums.pop();
    System.out.println(nums);
    nums.reverse();
    System.out.println(nums);
    LList clone = nums.clone();
    System.out.println(clone);
    clone.add(6);
    System.out.println(clone);
    System.out.println(nums);
    nums.add(90);
    System.out.println(nums);
    nums.removeDuplicates(90);
    System.out.println(nums);
  }
}

class LList{
  private LNode head; // head reference (front of the list)
  private LNode tail; // tail reference (back of the list)

  public LList(){ // when you create a new list, it contains no elements so the head and tail both have null values
    head = null;
    tail = null;
  }

  public void add(int v){ // adds an element to the front of the list (at the head)
    LNode tmp = new LNode(null, v, head); // the new value will have a node that points at the current head and does not have a previous value (since it is the newest value)
    if(head == null){ // if the list is empty
      tail = tmp; // the tail will become the new node
    }else{
      head.setLast(tmp); // since the current head will no longer be the head, it needs to point back at a value. this value will be the new node
    }
    head = tmp; // finally, once all references to the former head have been made, the head is officially changed to the new node
  }

  public void push(int v){ // same as add
    LNode tmp = new LNode(null, v, head);
    if(head == null){
      tail = tmp;
    }else{
      head.setLast(tmp);
    }
    head = tmp;
  }

  public LNode pop(){ // removes an element from the front of the list (top of the stack)
    LNode temp = new LNode(null, head.getValue(), head.getNext()); // just to return the node at the end
    head = head.getNext(); // move the head over one spot to the right
    head.setLast(null); // stop pointing at the old head
    return temp; 
  }

  public void enqueue(int v){ // add an element to the back of the list (at the tail)
    LNode tmp = new LNode(tail, v, null); // the old tail will become the previous value to the new node
    tail.setNext(tmp); // the old tail will point at the new node
    tail = tmp; // the new node becomes the tail
  }

  public LNode dequeue(){ // remove an element from the front of the queue
    LNode tmp = new LNode(head, head.getValue(), head.getNext()); // the head is the node to be removed; this is just to return
    head = head.getNext(); // move the head over one spot to the right
    head.setLast(null); // the head has a null prev value
    return tmp;
  }

  public void delete(LNode nodeToDelete){ // delete a node
    if(nodeToDelete.getLast() != null && nodeToDelete.getNext() != null){ 
      // if the node to delete has nodes at either side, make the one on the left 
      // point to the one on the right, and vice versa, eliminating the middle value
      nodeToDelete.getLast().setNext(nodeToDelete.getNext());
      nodeToDelete.getNext().setLast(nodeToDelete.getLast());
    }
    else if(nodeToDelete.getLast() == null && nodeToDelete.getNext() == null){ // if the node is the only element in the list, make the list empty (null)
      head = null;
      tail = null;
    }
    else if(nodeToDelete.getLast() == null){ // i.e. if the node to delete is the head
      head = nodeToDelete.getNext();
      head.setLast(null);
    }else{ // i.e. if the node to delete is the tail
      tail = nodeToDelete.getLast();
      tail.setNext(null);
    }
  }

  public void delete(int val){ // delete the first instance of a value in the list
    LNode tmp = head; // begin at the head
    while(tmp != null){
      if(tmp.getValue() == val){ // if the values match up
        if(tmp.getLast() != null && tmp.getNext() != null){ // same cases as first delete function w/ node
          tmp.getLast().setNext(tmp.getNext());
          tmp.getNext().setLast(tmp.getLast());
        }
        else if(tmp.getLast() == null && tmp.getNext() == null){
          head = null;
          tail = null;
        }
        else if(tmp.getLast() == null){
          head = tmp.getNext();
          head.setLast(null);
        }else{
          tail = tmp.getLast();
          tail.setNext(null);
        }
        tmp = null; // stop looping as to only remove the first instance of the value
      }else{
        tmp = tmp.getNext(); // move on to the next element if the value isn't found
      }
    }
  }

  public void deleteAt(int index){ // delete at an index
    LNode tmp = head;

    for(int i = 0; i < index; i++){
      tmp = tmp.getNext(); // move through the appropriate number of times until the spot is reached (the head has an index of 0)
    }

    // same cases as other delete functions
    if(tmp != head && tmp != tail){
      tmp.getLast().setNext(tmp.getNext());
      tmp.getNext().setLast(tmp.getLast());
    }else if(tmp == head && tmp == tail){
      head = null;
      tail = null;
    }else if(tmp == head){
      head = tmp.getNext();
      head.setLast(null);
    }else{
      tail = tmp.getLast();
      tail.setNext(null);
    }
  }

  public void sortedInsert(int val){ // add a value in the correct spot assuming ascending order from tail to head
    LNode tmp = tail; // begin at the tail
    boolean done = false;
    if(tmp.getValue() > val || head == null){
      enqueue(val); // if the tail is larger than the value or there are no elements in the list, add the value to the tail
    }else if(val > head.getValue()){
      push(val); // if the value is larger than the largest value, at the value to the head
    }
    else{ // if the value belongs somewhere in the middle
      while(!done){
        if(tmp.getValue() <= val && tmp.getLast().getValue() >= val){ // if the left is larger than the value is larger than the right, insert in between
          done = true;
        }
        tmp = tmp.getLast();
      }
      LNode nodeToAdd = new LNode(tmp, val, tmp.getNext()); // since the tmp variable is updated at the end of the loop, it will become the previous value for the new node
      tmp.setNext(nodeToAdd);
      nodeToAdd.getNext().setLast(nodeToAdd);
    }
  }

  public void removeDuplicates(int val){
    LNode tmp = head;
    boolean first = true;
    while(tmp != null){
      if(tmp.getValue() == val){
        if(first){
          first = false; // skip the first one to leave one element in the list
        }else if(tmp != tail){
          tmp.getLast().setNext(tmp.getNext());
          tmp.getNext().setLast(tmp.getLast()); 
          // if the last is null, it is the head, and therefore the first value. the first is skipped anyway, so a case is not needed for that
        }else{ // the value exists at the tail
          tmp.getLast().setNext(null);
          tail = tmp;
        }
      }
      tmp = tmp.getNext();
    }
  }

  public void reverse(){
    LNode tmp = tail;
    while(tmp != null){
      LNode next = tmp.getNext();
      LNode last = tmp.getLast();
      // reversing the pointers reverses the list
      tmp.setLast(next); // the last becomes the next
      tmp.setNext(last); // the next becomes the last
      if(next == null){ // the tail becomes the head
        head = tmp;
      }
      if(last == null){ // the head becomes the tail
        tail = tmp;
      }
      tmp = last;
    }
  }

  public LList clone(){ // make a new list that has copies of all of the nodes of the original list
    LNode tmp = tail; // start at the tail to maintain order
    LList newList = new LList(); 
    while(tmp != null){
      newList.add(tmp.getValue()); // add copies of all the nodes
      tmp = tmp.getLast();
    }
    return newList;
  }

  @Override
  public String toString(){ // prints a string representation of the list
    LNode tmp = head;
    String ans="";
    while(tmp != null){
      ans += tmp+", ";
      tmp = tmp.getNext();
    }
    if(ans!=""){
      ans = ans.substring(0,ans.length()-2); // remove the ", "
    }
    return "["+ans+"]";
  }
}

class LNode{
  private int val;
  private LNode next;
  private LNode prev;

  public LNode(LNode p, int v, LNode n){
    prev = p;
    val = v;
    next = n;
  }

  public LNode getNext(){
    return next;
  }

  public int getValue(){
    return val;
  }

  public LNode getLast(){
    return prev;
  }

  public void setNext(LNode node){
    next = node;
  } 

  public void setLast(LNode node){
    prev = node;
  } 

  @Override
  public String toString(){
    return ""+val;
  }
  
}