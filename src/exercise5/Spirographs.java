import java.lang.Math;
import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.Scanner;
import java.util.Random;

import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

public class Spirographs {

	private double rR,r,a;
	private int x,y;
	private int w,h;

	public Spirographs(double rR,double r,double a) {
		this.rR = rR;
		this.r = r;
		this.a = a;
		w = 2;
		h = 2;
	}

	public void paint(Graphics g) {

        for(int t = 0; ; t++) {
        	x = (int)Math.round((rR+r)*Math.cos(t) - (r+a)*Math.cos(((rR+r)/r)*t));
        	y = (int)Math.round((rR+r)*Math.sin(t) - (r+a)*Math.sin(((rR+r)/r)*t));
        	//System.out.println(x+" "+y);
       		x+=rR*5;
       		y+=rR*5;
       		Random random = new Random();
       		int r = random.nextInt(10);
       		switch (r) {
       			case 1:
       				g.setColor(Color.blue);
       				break;
       			case 2:
       				g.setColor(Color.red);
       				break;
       			case 3:
       				g.setColor(Color.pink);
       				break;
       			case 4:
       				g.setColor(Color.green);
       				break;
       			case 5:
       				g.setColor(Color.MAGENTA);
       				break;
       			case 6:
       				g.setColor(Color.orange);
       				break;
       			case 7:
       				g.setColor(Color.cyan);
       				break;
       			case 8:
       				g.setColor(Color.gray);
       				break;
       			case 9:
       				g.setColor(Color.yellow);
       				break;
       			case 10:
       				g.setColor(Color.black);
   	    			break;
   	    	};
       		g.fillOval(x,y,w,h);
        }
	}

	public static void main(String[] args) {
        

        //run();

        int x,y;
        int w,h;

        w = 2;
        h = 2;
        Scanner sc = new Scanner(System.in);
        System.out.print("Set R:");
        double rR = sc.nextDouble();
        System.out.println();

        System.out.print("Set r:");
        double r = sc.nextDouble();
        System.out.println();

        System.out.print("Set a:");
        double a = sc.nextDouble();
        System.out.println();

        Spirographs spiro = new Spirographs(rR,r,a);
        
        Canvas c = new Canvas() {
			public void paint(Graphics g) {}
		};
  		JFrame frame = new JFrame("Spirograph");
	    c.setBackground(Color.black); 
        c.setSize(800,800);
        frame.add(c);
        frame.pack();
        frame.setVisible(true);

        Graphics g = c.getGraphics(); 
        spiro.paint(g);
    }
}
