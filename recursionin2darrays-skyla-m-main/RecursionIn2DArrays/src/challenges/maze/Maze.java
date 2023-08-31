package challenges.maze;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import challenges.GridTemplate;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;


/*

	Represents a 2D maze.

	Coded by: Skyla Ma
	Modified on: 4/19/2021

*/

public class Maze extends GridTemplate {

	private static boolean [][] visited;
	// Constructs an empty grid
	public Maze() {
		super();
		visited = new boolean [20][20];
	}

	// Constructs the grid defined in the file specified
	public Maze(String filename) {
		super(20, 20, filename);
		visited = new boolean [20][20];
	}

	/**
	 * Attempts to find a path through the maze and returns whether a path was found or not. The path that is found
	 * need not be an optimal path, just one possible route.
	 * The maze should be marked with the path that was found, so a print-out of the grid will show the path after this method call.
	 * 
	 * @param x The x coordinate of the starting point.
	 * @param y The y coordinate of the starting point.
	 * @return true if a path to the exit was found, false if no such path exists in the maze.
	 */
	public boolean findPath(int x, int y) {
		if (grid[x][y] == '.') {
			grid[x][y] = '!';
		}
		else {
			return false;
		}
		boolean done = doFindPath(x, y);
		if(!done) {
			grid[x][y] = '.';
		}
		
		return done;
	}
	
	public String toString(){
		String s = "";
		for(int i=0; i<grid.length;i++) {
			for(int j=0; j<grid[i].length; j++) {
				s+=grid[i][j];
			}
			s+="\n";
		}
		return s;
	}
	
	// Additional private recursive methods


	public static void main(String args[]) {
        Maze m = new Maze("testfiles/maze/test4.txt");
        System.out.println(m);
        boolean result = m.findPath(10, 5);
        System.out.println("+++++++++++");
        System.out.println(m);
        System.out.println(result);
	}
	
	private boolean doFindPath(int x, int y) {
		int[]dx = {0,-1,0,1};
		int[]dy = {-1,0,1,0};
		boolean res = false;
		for(int i=0; i<dx.length && res == false; i++) {
			int x1 = x+dx[i];
			int y1 = y+dy[i];
			if(x1<0 || x1>=grid.length || y1<0 || y1>=grid[0].length || visited[x1][y1] || grid[x1][y1] == '#') {
				res = false;
			}
			else if(grid[x1][y1] == 'X') {
				return true;
			}
			else if (grid[x1][y1] == '.'){
				char temp =  grid[x1][y1];
				grid[x1][y1] = '!';
				visited[x1][y1] = true;
				res = doFindPath(x1, y1);
				if (res == false){
					grid[x1][y1] = temp;
					visited[x1][y1] = false;
				}
			}
		}
		return res;
	}
}