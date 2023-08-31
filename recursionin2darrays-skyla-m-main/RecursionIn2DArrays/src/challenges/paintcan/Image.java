package challenges.paintcan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import challenges.GridTemplate;

/*

	This program paint can fills objects on a 2D grid. 

	Coded by: Skyla Ma
	Modified on: 4/19/2021

*/

public class Image extends GridTemplate {

	//boolean[][] alreadyVisited;
	
	// Constructs an empty grid
	public Image () {
		super();
		//alreadyVisited = new boolean[grid.length][grid[0].length];
		}

	// Constructs the grid defined in the file specified
	public Image (String filename) {
		super(20, 20, filename);
		//alreadyVisited = new boolean[grid.length][grid[0].length];
	}
	
	/**
	 * Fills an object beginning at x,y.
	 * 
	 * @param x The x coordinate of the beginning of the paint can fill.
	 * @param y The y coordinate of the beginning of the paint can fill.
	 */
	public void paintCanFill(int x, int y) {
		char toFill = ' ';
		char currentFill = '*';
		if(grid[x][y]==' ') {
			toFill = '*';
			currentFill = ' ';
		}
		doPaintCanFill2(x, y, toFill, currentFill);
		
	}


	// Additional private recursive methods
	
	private void doPaintCanFill(int x, int y, char toFill, char currentFill) {
		grid[x][y] = toFill;
		int[] dx = {0,-1,0,1};
		int[] dy = {-1,0,1,0};
		for(int i=0; i<dx.length; i++) {
			int x1 = x+dx[i];
			int y1 = y+dy[i];
			if(isValid(x1, y1, currentFill)) {
				//alreadyVisited[x1][y1] = true;
				doPaintCanFill(x1, y1, toFill, currentFill);
			}
		}
		
	}	
	
	private void doPaintCanFill2(int x, int y, char toFill, char currentFill) {
		if(x<0 || x>=grid.length || y<0 || y>=grid[0].length) {
			return;
		}
		if(grid[x][y] != currentFill) {
			return;
		}
		grid[x][y] = toFill;
		doPaintCanFill2(x-1, y, toFill, currentFill);
		doPaintCanFill2(x+1, y, toFill, currentFill);
		doPaintCanFill2(x, y-1, toFill, currentFill);
		doPaintCanFill2(x, y+1, toFill, currentFill);
		
	}
	
	private boolean isValid(int x, int y, char currentFill) {
		if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y]!=currentFill) {
			return false;
		}
		return true;
	}


}