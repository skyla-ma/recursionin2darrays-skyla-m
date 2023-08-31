package challenges;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

public abstract class GridTemplate {

	/** Add a field to represent the grid. This time, make it a 2D array of characters. **/
	
	protected char[][] grid;
	
	
	/**
	 * Construct an empty 2D array with some default dimensions.
	 */
	public GridTemplate() {
		grid = new char[20][20];
	}
	
	
	/**
	 * Construct an empty 2D array with dimensions width and height, then fill it with data from the file filename.
	 * 
	 * @param width The width of the grid.
	 * @param height The height of the grid.
	 * @param filename The text file to read from.
	 */
	public GridTemplate(int width, int height, String filename) {
		grid = new char[height][width];
		this.readData(filename, grid);
		//this.fixInitialization();
	}
	
	
	/**
	 * 
	 * Code a toString() method that nicely prints the grid to the commandline.
	 * 
	 */
	public String toString() {
		String output = "";
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[0].length; c++) {
				if(grid[r][c]=='*') {
					output+="*";
				}
				else {
					output+=" ";
				}
			}
			output+="\n";
		}
		return output;
	}
	
	/*private void fixInitialization() {
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[0].length; c++) {
				if(grid[r][c]=='\0') {
					grid[r][c]=' ';
				}
			}
		}
	}*/
	
	
	/**
	 * (Graphical UI)
	 * Draws the grid on a PApplet.
	 * The specific way the grid is depicted is up to the coder.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 */
	public void draw(PApplet marker, float x, float y, float width, float height) {
		marker.noFill();
		
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				float rectWidth = width / grid[0].length;
				float rectHeight = height / grid.length;
				float rectX = x + j*rectWidth;
				float rectY = y + i*rectHeight;
				if(grid[i][j] =='*' || grid[i][j] =='!') {
					marker.fill(255, 255, 0);
				}
				
				if(grid[i][j] ==' ' || grid[i][j] =='.') {
					marker.noFill();
				}
				
				if(grid[i][j] =='#') {
					marker.fill(0, 0, 0);
				}
				
				if(grid[i][j] =='@') {
					marker.fill(0, 0, 255);
				}
				
				if(grid[i][j] =='A') {
					marker.fill(255, 0, 0);
				}
				
				if(grid[i][j] =='X') {
					marker.fill(0, 255, 0);
				}
				if(grid[i][j] !='\0') {
					marker.rect(rectX, rectY, rectWidth, rectHeight);
				}
			}
		}

	}
	
	
	/**
	 * (Graphical UI)
	 * Determines which element of the grid matches with a particular pixel coordinate.
	 * This supports interaction with the grid using mouse clicks in the window.
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the grid, or null if the pixel coordinate
	 * falls completely outside of the grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		float rectWidth = width / grid[0].length;
		float rectHeight = height / grid.length;
		
		float i = (p.y - y) / rectHeight;
		float j = (p.x - x) / rectWidth;
		
		Point coordinates = new Point((int)i,(int)j);
		//System.out.println(i);
		//System.out.println(j);
		if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
			return null;
		}
		return coordinates;
	}
	


	public void readData (String filename, char[][] gameData) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
					reader = new FileReader(dataFile);
					in = new Scanner(reader);
					
					while (in.hasNext()) {
						String line = in.nextLine();
						for(int i = 0; i < line.length(); i++)
							if (count < gameData.length && i < gameData[count].length)
								gameData[count][i] = line.charAt(i);

						count++;
					}

			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}
			
		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
	}
}
