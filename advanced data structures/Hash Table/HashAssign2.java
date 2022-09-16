/*
Authour Name : Qabas Imbewa
File Name    : HashAssign2.java
Description  : This is a program that reads coordinates and emotion scores from a textfile, then translates them into colors on an 800x600
               window/map. The user can then click on a spot on the map and every coordinate point within a 10 pixel radius will light up
               in a colour that is proportionate to the emotion score.
 */

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.*;
import java.awt.*;

public class HashAssign2 extends JFrame{

    public HashAssign2() throws IOException{
		super("Emotions Map");
		Panel pane = new Panel();
		add(pane);
		pack();
		setVisible(true);
    }
    public static void main(String [] args) throws IOException{
        HashAssign2 frame = new HashAssign2();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Panel extends JPanel implements ActionListener, MouseListener{
    Timer timer;
	Image back;
	boolean [] keys;
    HashTable<Blogger> bloggers = new HashTable<Blogger>();
    int cx, cy = 1000;
	
	public Panel() throws IOException{
		super();
		keys = new boolean[2000];
		back = new ImageIcon("data/windsor.PNG").getImage(); // the map background
		setPreferredSize(new Dimension(800, 600));
		addMouseListener(this);
		setFocusable(true);
		requestFocus();
		timer = new Timer(20, this);
		timer.start();

        makeTable();
	}

    public void makeTable() throws IOException{
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("data/creeper.txt")));

        while(inFile.hasNextLine()){
            String [] values = inFile.nextLine().split(" "); // go through every line in the file and split it apart to access the individual pieces of information
            int code = Integer.parseInt(values[0])*1000 + Integer.parseInt(values[1]); // hashcode
            if(bloggers.contains(code)){ // if the coordinate already exists in the hashtable, just add to that one rather than making a new one
                Blogger b = bloggers.get(code);
                b.LH = (b.LH * b.n + Integer.parseInt(values[2])) / (b.n+1); // weighted average of all the entries in a particular coordinate point
                b.HS = (b.HS * b.n + Integer.parseInt(values[3])) / (b.n+1);
                b.EB = (b.EB * b.n + Integer.parseInt(values[4])) / (b.n+1);
                b.addn(); // add one to the entry count
                System.out.println(b.LH +" "+b.HS+" "+b.EB);
            }else{ // if it doesn't exist, make a new object
                Blogger blogger = new Blogger(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
                bloggers.add(blogger);
            }
        }
        System.out.println("hey");
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        cx = (int)MouseInfo.getPointerInfo().getLocation().getX() - (int)this.getLocationOnScreen().getX(); // if you click, update the mouse position
        cy = (int)MouseInfo.getPointerInfo().getLocation().getY() - (int)this.getLocationOnScreen().getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {repaint();}

    @Override
	public void paint(Graphics g){
		g.drawImage(back, 0,0, null);
        if(cx != 1000){ // 1000 is the default and impossibly attained value. if it isn't 1000, then the user clicked and a circle is to be drawn
            for(int x = -10 ; x <= 10 ; x++){
                for(int y = -10 ; y <= 10 ; y++){ // loop through a square and check if the point belongs in a circle
                    if((x*x + y*y)<= 100){
                        if(bloggers.contains((x+cx)*1000+y+cy)){ // check that that coordinate has an entry
                            g.setColor(bloggers.get((x+cx)*1000+y+cy).color);
                            g.drawLine(x+cx, y+cy, x+cx, y+cy); // draw a line of size 1 pixel
                        }
                    }
                }
            }
        }
	}
}

class Blogger {
    // this is a custom class that stores objects that represent points on the map. Each of these objects has an x,y coordinate and emotion scores that are translated into colours
    public int x, y, LH, HS, EB, n;
    public Color color;

    public Blogger(int x, int y, int LH, int HS, int EB){
        this.x = x;
        this.y = y;
        this.LH = LH;
        this.HS = HS;
        this.EB = EB;
        n = 1;
        color = new Color((LH+100)/200*255, (HS+100)/200*255, (EB+100)/200*255);
    }

    public void addn(){
        n++;
        color = new Color((LH+100)*255/200, (HS+100)*255/200, (EB+100)*255/200);
    }

    @Override
    public int hashCode(){
        return x*1000+y;
    }
}
