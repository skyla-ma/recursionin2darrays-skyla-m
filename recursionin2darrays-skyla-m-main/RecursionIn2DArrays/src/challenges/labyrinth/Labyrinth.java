package challenges.labyrinth;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import challenges.GridTemplate;

import java.util.ArrayList;

/*

	Coded by: Skyla Ma
	Modified on: 4/23/2021

*/

public class Labyrinth extends GridTemplate {
	
	private ArrayList<Point> result;
	private ArrayList<Point> shortestResult;
	//private boolean hasCloak;
	private static int shortestPath;
	private static boolean [][] visited;
	private int currentX;
	private int currentY;

	// Constructs an empty grid
	public Labyrinth() {
		super();
		visited = new boolean[20][20];
		result = new ArrayList<>();
		shortestResult = new ArrayList<>();
		shortestPath = Integer.MAX_VALUE;
	}

	// Constructs the grid defined in the file specified
	public Labyrinth(String filename) {
		super(20, 20, filename);
		visited = new boolean[20][20];
		result = new ArrayList<>();
		shortestResult = new ArrayList<>();
		shortestPath = Integer.MAX_VALUE;
	}

	/**
	* Solves the labyrinth in the smallest number of moves.
	* 
	* @param x The x coordinate of the starting point.
	* @param y The y coordinate of the starting point.
	* @return An ArrayList containing the coordinates of all locations on the shortest path to the exit, where the first 
	* element is the location of the starting point and the last element is the location of the exit, or null if no path can be found.
	*/
	public ArrayList<Point> findPath(int x, int y) {
			
		int len1 = Integer.MAX_VALUE, len2 = Integer.MAX_VALUE;
		ArrayList<Point> path1 = new ArrayList<>(), path2= new ArrayList<>(), path;

		//System.out.println("["+x+","+y+"],"+"["+grid.length+","+grid[0].length+"]");
		// This is assuming that the player cannot click on the monster to start
		if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y] == '#' || grid[x][y] == 'A') {
			return path1;
		}
		
		if (grid[x][y] == 'X') {
			path1.add(new Point(x,y));
			return path1;
		}
		
		GetShortestPath(x, y, 'X', false);
		
		len1 = getLen(shortestResult);			
		move(path1, shortestResult); 	
		//System.out.println("native path(" + x + "," + y +"):"+len1);
		
		if (grid[x][y] == '@') {
			len2 = 0;
			//path2.add(new Point(x,y));
			currentX = x;
			currentY = y;
		} else 
		{
			GetShortestPath(x, y, '@', false);
			len2 = getLen(shortestResult);
			move(path2, shortestResult); 
		}
		//System.out.println("cloak path(" + x + "," + y +"):"+len2);

		if (len2!=Integer.MAX_VALUE) {

			//clearA();
			clearVisited();
			
			GetShortestPath(currentX, currentY, 'X', true);

			merge(path2, shortestResult);
			len2 += getLen(shortestResult);
			//System.out.println("2nd path(" + currentX + "," + currentY +"):"+len2);

			
		}
				
		//System.out.println("len["+len1+","+len2+"]");

		if (len1 < len2) {
			path = path1;
		} else {
			path = path2;
		}

		//System.out.println("path size:"+path.size());

		if (path.size()==0)
			return  null;
		
		fill(path, '!');

		// add the target
		path.add(new Point(currentX,currentY));
		//System.out.print(path.size());
		return path;
	}


	// Additional private recursive methods
	
	private int getLen(ArrayList<Point> path) {
		int len = path.size();
		if (len==0) {
			return Integer.MAX_VALUE;
		}
		return len;
	}
	
	private void fill(ArrayList<Point> path, char toFill) {
		int len = path.size();
		for (int i =0; i<len; i++) {
			int x = path.get(i).x, y = path.get(i).y;
			if (grid[x][y]=='.' || grid[x][y]=='@' || grid[x][y]=='A') 
			{
				grid[x][y] = toFill;
			}
		}
	}
	
	private void clearVisited() {
		for (int i=0; i<visited.length; i++) {
			for (int j=0; j<visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}
	
	private void clearA() {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == 'A') {
					grid[i][j] = '.';
				}
			}
		}		
	}
	private void clearPath() {
		clearVisited();
		result.clear();
		shortestResult.clear();
		shortestPath = Integer.MAX_VALUE;
	}
	
	private void GetShortestPath(int x, int y, char target, boolean ignoreA) {

		clearPath();
		doShortestPath(x, y, target, ignoreA);
		// System.out.println("GetShortestPath(" + x + "," + y +"):"+grid[x][y]);
	}

	private boolean doShortestPath(int x, int y, char target, boolean ignoreA) {
		int[]dx = {0,-1,0,1};
		int[]dy = {-1,0,1,0};
		boolean[] canMove = {false, false, false, false};
		boolean totalMove = false;

	    
		//System.out.println("(" + x + "," + y +"):"+grid[x][y]);
		
		result.add(new Point(x,y));
		visited[x][y] = true;
		
		//if (target=='@' && grid[x][y] == target) {
		//	return true;
		//}
		
		for(int i=0; i<dx.length; i++) {
			int x1 = x+dx[i];
			int y1 = y+dy[i];
			if(x1<0 || x1>=grid.length || y1<0 || y1>=grid[0].length || visited[x1][y1]) {
				canMove[i] = false;
			}
			else if(grid[x1][y1]== '.' ||ignoreA && grid[x1][y1]== 'A' || grid[x1][y1] == target ) {
				canMove[i] = true;
				//if (ignoreA && grid[x1][y1]== 'A') {
				//	grid[x1][y1]='.';
				//}
			}
			else {
				canMove[i] = false;
			}
			
			if(canMove[i]) {
				//System.out.println("Enter("+x+","+y+")=" + canMove);
				if(grid[x1][y1]==target) {
					if(result.size() < shortestPath) {
						shortestPath = result.size();
						move(shortestResult, result);
						currentX = x1;
						currentY = y1;
					}
					//System.out.println("Bingo:"+shortestPath);
					return true;
				}
				else {
					canMove[i] = doShortestPath(x1, y1, target, ignoreA);
					//if (!canMove[i]) 
					{
						if (result.size()>1) {
							result.remove(result.size()-1);
						}
						visited[x1][y1] = false;
					}
				}
			}
			totalMove = totalMove || canMove[i];
		}
		return totalMove;
	}
		
	
	
	private void move(ArrayList<Point> a, ArrayList<Point> b)  {
		a.clear();
		a.addAll(b);
	}
	
	private void merge(ArrayList<Point> a, ArrayList<Point> b)  {
		a.addAll(b);
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
	
//	public static void main(String args[]) {
//        Labyrinth l = new Labyrinth("testfiles/labyrinth/test2.txt");
//        ArrayList<Point> res;
//        System.out.println(l);
//        res = l.findPath(10, 10);
//        if (res != null) {
//	        for (Point p: res) {
//	           System.out.print("("+p.x+",");
//	           System.out.println(p.y + ")");
//	        }
//        }
//        System.out.println("Result:");
//        if (res != null) {
//           System.out.println(l);
//           System.out.println(res);
//        }
//        else
//           System.out.println("Path is null");
//        
//	}

}