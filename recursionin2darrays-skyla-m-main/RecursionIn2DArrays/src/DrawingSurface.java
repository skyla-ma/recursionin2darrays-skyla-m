


import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import challenges.GridTemplate;
import challenges.paintcan.Image;
import challenges.maze.*;
import challenges.labyrinth.*;
import processing.core.PApplet;



public class DrawingSurface extends PApplet {

	// When you progress to a new prompt, modify this field.
	
	//private Maze board;
	private Labyrinth board;
	
	//uncomment for paintcan
	//private Image board;
	
	
	public DrawingSurface() {
		//test for maze
		//board = new Maze("testfiles/maze/test5.txt");
		
		//test for labyrinth
		board = new Labyrinth("testfiles/labyrinth/test4.txt");
		
		//uncomment for paintcan
		//board = new Image("testfiles/paintcan/digital.txt");
	}
	

	public void draw() { 
		background(255);   
		fill(0);
		textAlign(LEFT);
		textSize(12);
		
		if (board != null) {
			board.draw(this, 50, 0, height, height);
		}
		
	}
	
	
	public void mousePressed() {
		if (mouseButton == LEFT) {
			Point click = new Point(mouseX,mouseY);
			float dimension = height;
			Point cellCoord = board.clickToIndex(click,50,0,dimension,dimension);
			if (cellCoord != null) {
				//test for maze
				//board.findPath(cellCoord.x, cellCoord.y);
				
				//test for labyrinth
				ArrayList<Point> path = board.findPath(cellCoord.x, cellCoord.y); // When you progress to a new prompt, modify this method call.
				if(path!=null) {
					for(Point p: path) {
						System.out.print(p.getX()+" ");
						System.out.print(p.getY()+"\n");
					}
				}
				else {
					System.out.print("No path");
				}
				
				//uncomment for paintcan
				//board.paintCanFill(cellCoord.x, cellCoord.y);
			}
		} 
	}
}











