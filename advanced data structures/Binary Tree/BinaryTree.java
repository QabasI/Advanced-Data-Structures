/*
Authour Name : Qabas Imbewa
File Name    : BinaryTree.java
Description  : This is a class that functions as a Binary Tree from scratch. The tree can only store integer values, for simplicity. The display
               method can display the tree in IN, PRE, and POST order, respectively. The BTree class has other functions that can add and remove
               elements, find the depth of a node, count the leaves in a tree, as well as find the height of the tree and verify ancestral 
               relationships between nodes.
*/

class BinaryTree {
  public static void main(String[] args) {
    // testing
    BTree maple = new BTree();
    maple.add(50);
    maple.add(40);
    maple.add(60);
    maple.add(30);
    maple.add(55);
    maple.add(70);
    maple.add(35);
    maple.add(75);
    maple.add(65);
    maple.add(52);
    maple.add(36);
    //maple.add(37);

    System.out.println(maple);
    System.out.println("DEPTH: " + maple.depth(60));
    System.out.println("NUMBER OF LEAVES: " + maple.countLeaves());
    System.out.println("HEIGHT: " + maple.height());
    System.out.println(maple.isAncestor(50, 70));
    maple.delete(70);
    System.out.println(maple);
    System.out.println("MAPLE IS BALANCED " + maple.isBalanced());

    BTree oak = new BTree();
    oak.add(50);
    oak.add(60);
    oak.add(30);
    oak.add(40);
    oak.add(35);
    oak.add(32);
    oak.add(55);
    System.out.println("OAK IS BALANCED " + oak.isBalanced());

    BTree birch = new BTree();
    birch.add(25);
    birch.add(10);
    birch.add(37);
    birch.add(57);
    System.out.println("BIRCH IS BALANCED " + birch.isBalanced());

    maple.add(birch);
    System.out.println(maple);
    System.out.println(maple.display(BTree.POST));
    maple.delete(10);
    System.out.println(maple);
    maple.delete(25);
    System.out.println(maple);
  }
}

class BTree{
  private BNode root; // root reference
  public static final int IN = 0; // constants that represent order
  public static final int PRE = 1;
  public static final int POST = 2;

  public BTree(){
    root=null; // when constructing a new tree, the default root value is null
  }

  public BNode getRoot(){
    return root;
  }
  
  public void add(int v){ // adding a value
    if(root==null){ // if empty, just make it the root
      root = new BNode(v);
    }
    else{
      add(v, root);
    }
  }

  public void add(int v, BNode branch){
    if(v < branch.getVal()){ // if the value is less than the node's value
      if(branch.getLeft()==null){ // if there is a spot, add it there
        branch.setLeft(new BNode(v));
      }
      else{ // otherwise, check that node for another spot
        add(v, branch.getLeft());
      }
    }
    else if(v > branch.getVal()){ // if the value is larger, go to the right
      if(branch.getRight()==null){
        branch.setRight(new BNode(v));
      }
      else{
        add(v, branch.getRight());
      }
    }    
  }

  public void add(BNode node){ // adding a node directly
    if(root==null){
      root = new BNode(node.getVal());
    }
    else{
      add(node, root);
    }
  }

  public void add(BNode node, BNode branch){
    if(node.getVal() < branch.getVal()){ // same as other add function
      if(branch.getLeft()==null){
        branch.setLeft(new BNode(node.getVal()));
      }
      else{
        add(node.getVal(), branch.getLeft());
      }
    }
    else if(node.getVal() > branch.getVal()){
      if(branch.getRight()==null){
        branch.setRight(new BNode(node.getVal()));
      }
      else{
        add(node.getVal(), branch.getRight());
      }
    }   
  }
  
  public int depth(int val){ // find the depth of a node
    return depth(val, root, 1); // depth of root is 1, so starting depth value is 1
  }

  public int depth(int val, BNode branch, int depth){
    if(branch.getVal() == val){ // base case - the value is found
      return depth;
    }
    if(val > branch.getVal()){ // go in the direction of the value. if it is larger than the branch, go right
      if(branch.getRight() == null){ // if nothing is found, the value does not exist in the tree
        return -1;
      }else{
        return depth(val, branch.getRight() , depth+=1); // otherwise, check the right subtree for the value
      }
    }else{ // if it is smaller than the branch, go left
      if(branch.getLeft() == null){
        return -1;
      }else{
        return depth(val, branch.getLeft(), depth+=1);
      }
    }
  }

  public int countLeaves(){ // count the number of leaves (nodes with children)
    return countLeaves(root);
  }

  public int countLeaves(BNode branch){
    if(branch == null){
      return 0; // null is not a leaf
    }
    if(branch.getLeft() == null && branch.getRight() == null){
      return 1; // if it is a leaf, add 1
    }
    return countLeaves(branch.getLeft()) + countLeaves(branch.getRight()); // the number of leaves is the sum of the left and right subtrees
  }

  public int height(){ // find the height of the tree
    return height(root);
  }

  public int height(BNode branch){
    if(branch == null){
      return 0;
    }
    if(branch.getLeft() == null && branch.getRight() == null){
      return 1;
    }
    return Math.max(height(branch.getLeft()), height(branch.getRight())) + 1; // the height is the maximum distance between root and an edge/leaf (plus 1 since the root has a depth of 1)
  }

  public boolean isAncestor(int num1, int num2){ // checks if num1 is an ancestor of num2
    return isAncestor(num2, getNode(root, num1)); // the node to start at has the value of num1
  }

  public boolean isAncestor(int num2, BNode branch){
    // if, after starting at num1's node, num2 can be found, num1 is an ancestor of num2
    if(branch != null && branch.getVal() == num2){
      return true;
    }
    if(branch != null && (isAncestor(num2, branch.getLeft()) || isAncestor(num2, branch.getRight()))){
      return true;
    }
    return false;
  }

  public BNode getNode(BNode branch, int val){ // utility function. not for user use so not overloaded
    // finds the node that has a given value
    if(branch == null){
      return null;
    }
    else if(branch.getVal() == val){
      return branch;
    }
    else if(val > branch.getVal()){
      return getNode(branch.getRight(), val);
    }
    else{
      return getNode(branch.getLeft(), val);
    }
  }

  public void delete(int val){ // deletes a value from the tree
    delete(val, root);
  }

  public void delete(int val, BNode branch){
    if(branch == null){} // if the branch is null, do nothing
    else if(root.getVal() == val){ // if the node to delete is the root
      if(root.getRight() != null){
        BNode left = root.getLeft(); 
        BNode right = root.getRight();
        BNode tempRoot = right;
        BNode last;
        while(tempRoot != null){
          // make the smallest value in the right subtree the new root
          root = tempRoot;
          last = tempRoot; // keeping track of the last node
          tempRoot = tempRoot.getLeft();
          if(tempRoot != null && tempRoot.getLeft() != null){ 
            last.setLeft(tempRoot); // can't point at pointer
          }else if(tempRoot != null && tempRoot.getLeft() == null){
            last.setLeft(null); // stop pointing at the leaf, as it will become the root and nothing can point at the root
          }
        }
        root.setLeft(left); // the left and right values were kept safe in variables so that the new root can point at the same nodes as the old root
        root.setRight(right);
      }else if(root.getLeft() != null){ // if there is nothing to the right, go to the smallest value on the left
        BNode temp = root.getLeft();
        while(temp.getRight() != null){
          root = temp;
          temp = temp.getRight();
        }
      }else{
        root = null;
      }
    }
    else if(branch.getRight() != null && branch.getRight().getVal() == val){ // if the node to delete is to the right
      if(branch.getRight().getLeft() == null && branch.getRight().getRight() == null){ // if it is a leaf
        branch.setRight(null); // just set it to null
      }
      else if(branch.getRight().getLeft() == null && branch.getRight().getRight() != null){ // if there is just something to the right
        branch.setRight(branch.getRight().getRight()); // skip over the node to delete
      }
      else if(branch.getRight().getLeft() != null && branch.getRight().getRight() == null){ // if there is just something to the left
        branch.setRight(branch.getRight().getLeft()); // skip over the node to delete
      }else{ // if there is something on both sides
        BNode right = branch.getRight().getRight();
        BNode left = branch.getRight().getLeft();
        branch.setRight(right); // skip over left
        branch.getRight().setLeft(left); // make the new right node point to the left node that the old, deleted node used to point to
      }
    }else if(branch.getLeft() != null && branch.getLeft().getVal() == val){ // same as right but inverted
      if(branch.getLeft().getLeft() == null && branch.getLeft().getRight() == null){
        branch.setLeft(null);
      }
      else if(branch.getLeft().getLeft() == null && branch.getLeft().getRight() != null){
        branch.setLeft(branch.getLeft().getRight());
      }
      else if(branch.getLeft().getLeft() != null && branch.getLeft().getRight() == null){
        branch.setLeft(branch.getLeft().getLeft());
      }else{
        BNode right = branch.getLeft().getRight();
        BNode left = branch.getLeft().getLeft();
        branch.setRight(right);
        branch.getRight().setLeft(left);
      }
    }
    else if(val > branch.getVal()){
      delete(val, branch.getRight());
    }else if(val < branch.getVal()){
      delete(val, branch.getLeft());
    }
  }

  public boolean isBalanced(){ // checks to see if a tree is balanced
    return isBalanced(root);
  }

  public boolean isBalanced(BNode branch){
    if(branch == null){
      return true;
    }
    if(Math.abs(height(branch.getRight()) - height(branch.getLeft())) <= 1){
      return true;
    }
    isBalanced(branch.getRight());
    isBalanced(branch.getLeft());
    return false;
  }

  public void add(BTree tree){ // adds a tree rather than a value or node
    add(tree, tree.getRoot());
  }

  public void add(BTree tree, BNode branch){
    if(branch != null){ // makes copies of all the nodes as to not have them affect each other
      BNode tempToAdd = branch;
      add(tempToAdd);
      add(tree, branch.getRight());
      add(tree, branch.getLeft()); 
    }
  }

  public String display(){ // default display is in IN-order
    return toString();
  }

  public String display(int type){
    String ans = stringify(root, type);
    if(ans!=""){
      ans = ans.substring(0,ans.length()-2);
    }
    return "<" + ans + ">";
  }

  @Override
  public String toString(){
    String ans = stringify(root, IN);
    if(ans!=""){
      ans = ans.substring(0,ans.length()-2);
    }
    return "<" + ans + ">";
  }

  public String stringify(BNode branch, int type){
    if(branch == null){
      return "";
    }
    else if(type == IN){
      return stringify(branch.getLeft(), type)+ branch.getVal() +", "+ stringify(branch.getRight(), type); // left, middle, right
    }else if(type == PRE){
      return branch.getVal() + ", " +stringify(branch.getLeft(), type) + stringify(branch.getRight(), type); // middle, left, right
    }else{
      return stringify(branch.getLeft(), type) + stringify(branch.getRight(), type) + branch.getVal() + ", "; // left, right, middle
    }
  }
  
}

class BNode{
  private int val;
  private BNode left, right;

  public BNode(int v){
    val = v;
  }
  public int getVal(){return val;}
  public BNode getLeft(){return left;}
  public BNode getRight(){return right;}

  public void setLeft(BNode br){
    left = br;
  }
  public void setRight(BNode br){
    right = br;
  }

}
