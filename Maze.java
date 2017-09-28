/*
 * 	Name: Arbaaz Meghani
 * 	File: Maze.java
 * 	Description: solves a maze using depth first search stack algorithm
 */

//import statements
import java.awt.Color;
import java.io.*;
import java.util.*;

//declare class
public class Maze {
	
	//main
	public static void main (String[] args) {
		
		//check if file is specified
		if(args.length == 0) {
			System.out.println("No file specified");
			return;
		}
		
		//get the file
		File f = new File (args[0]);
		Scanner sc = null;
		
		//open the file
		try
		{
		  sc = new Scanner (f);
		}
		catch (FileNotFoundException fnfe)
		{
		  System.out.println ("File did not exist");
		  return;
		}
		
		//declare size variable, array of points, and a temporary point
		int size = 10;
		Point2d[] fileContents = new Point2d[size];
		Point2d tmp = new Point2d();
		
		//declare count to see how many values are in the file
		int count = 0;
		
		//store all values from file to array of points
		while(sc.hasNextInt()) {
			if(count == size) {
				fileContents = array_grow(fileContents, size);
				size *= 2;
			}
			tmp.setX(sc.nextInt());
			tmp.setY(sc.nextInt());
			fileContents[count] = tmp;
			tmp = new Point2d();
			count++;
		}
		
		//close the file
		sc.close();
		
		//declare increment variable, temporary string variable, and a point for size of maze
		int i = 0;
		String tempString;
		Point2d mazeSize = null;
		
		//loop to get to size of maze making sure it's valid
		while(mazeSize == null && i < count) {
			if( (fileContents[i].getX() > 0)  && (fileContents[i].getY() > 0) ) {
				mazeSize = fileContents[i];
			}
			else {
				tempString = fileContents[i].toString();
				System.err.println(tempString + ": size cannot be less than or equal to 0");
			}
			i++;
		}
		
		//make sure mazeSize was assigned a size
		if(i >= count && mazeSize == null) {
			System.err.println("ERROR: No valid size input in file. EXITING PROGRAM");
			return;
		}
		
		//print the size if the values at 0th index of file contents wasn't used
		if(mazeSize != fileContents[0]) {
			System.err.println(mazeSize.toString() + ": is the new size");
		}
		
		//declare start and end points
		Point2d start = null;
		Point2d end = null;
		
		//get start point
		while(start == null && i < count) {
			if(fileContents[i].getX() < 0 || fileContents[i].getY() < 0 || fileContents[i].getX() > mazeSize.getX() || fileContents[i].getY() > mazeSize.getY()) {
				tempString = fileContents[i].toString();
				System.err.println(tempString + ": start out of bounds of size " + mazeSize.toString());
			}
			else {
				start = fileContents[i];
			}
			i++;
		}

		//make sure start has a point
		if(i >= count && start == null) {
			System.err.println("ERROR: no valid start input in file. EXITING PROGRAM");
			return;
		}
		
		//if second point in file contents wasn't used as start, print the start
		if(start != fileContents[1]) {
			System.err.println(start.toString() + ": is the new start");
		}
		while(end == null && i < count) {
			if(fileContents[i].getX() < 0 || fileContents[i].getY() < 0 || fileContents[i].getX() > mazeSize.getX() || fileContents[i].getY() > mazeSize.getY()) {
				tempString = fileContents[i].toString();
				System.err.println(tempString + ": end out of bounds of size " + mazeSize.toString());
			}
			else {
				end = fileContents[i];
			}
			i++;
		}
		
		//make sure there's an end point
		if(i >= count && end == null) {
			System.err.println("ERROR: no valid end input in file. EXITING PROGRAM");
			return;
		}
		
		//if the third point in file was not used as end point, print new end point
		if(end != fileContents[2]) {
			System.err.println(end.toString() + ": is the new end");
		}
		
		//declare 2d array with mazeSize+2 to account for border
		char[][] mazeArr = new char[mazeSize.getX()+2][mazeSize.getY()+2];
		
		//loop through maze and set border to 'b' or blocked and everything else to 'u' or unblocked
		for(int j = 0; j < mazeSize.getX()+2; j++) {
			for(int c = 0; c < mazeSize.getY()+2; c++) {
				if( (j == 0 || j+1 == mazeSize.getX()+2 || c == 0 || c+1 == mazeSize.getY()+2) ) {
					mazeArr[j][c] = 'b';
				}
				else {
					mazeArr[j][c] = 'u';
				}
			}
		}
		
		
		//Loop to set the blocked positions from file and make sure that they don't block
		//the start or the end and that they are within the bounds
		for(int x = i; x < count; x++) {
			if(fileContents[x].getX() < 0 || fileContents[x].getY() < 0 || fileContents[x].getX() > mazeSize.getX() || fileContents[x].getY() > mazeSize.getY()) {
				tempString = fileContents[x].toString();
				System.err.println(tempString + ": out of bounds of size " + mazeSize.toString());
			}
			else if(fileContents[x].getX() == start.getX() && fileContents[x].getY() == start.getY()) {
				tempString = fileContents[x].toString();
				System.err.println(tempString + ": attempting to block start " + start.toString());
			}
			else if(fileContents[x].getX() == end.getX() && fileContents[x].getY() == end.getY()) {
				tempString = fileContents[x].toString();
				System.err.println(tempString + ": attempting to block end " + end.toString());
			}
			else {
				mazeArr[fileContents[x].getX()][fileContents[x].getY()] = 'b';
			}
		}
		
		//set the start and end locations of 2d array
		mazeArr[start.getX()][start.getY()] = 's';
		mazeArr[end.getX()][end.getY()] = 'e';
		
		//declare GridDisplay with mazeSize+2 to account for border
		GridDisplay maze = new GridDisplay(mazeSize.getX()+2, mazeSize.getY()+2);
		
		//Loop to set color at each location
		//Red: blocked
		//Yellow: unblocked
		//Blue: start
		//Pink: end
		for(int j = 0; j < mazeSize.getX()+2; j++) {
			for(int c = 0; c < mazeSize.getY()+2; c++) {
				if(mazeArr[j][c] == 'b') {
					maze.setColor(j, c, Color.RED);
				}
				else if(mazeArr[j][c] == 'u') {
					maze.setColor(j, c, Color.YELLOW);
				}
				else if(mazeArr[j][c] == 's') {
					maze.setColor(j, c, Color.BLUE);
				}
				else if(mazeArr[j][c] == 'e') {
					maze.setColor(j, c, Color.PINK);
				}
			}
		}

		//print what each color represents to make it easier for grader
		System.out.println("GREEN - correct path");
		System.out.println("GREY - vistied");
		System.out.println("BLUE - start");
		System.out.println("PINK - end");
		System.out.println("YELLOW - unblocked");
		System.out.println("RED - blocked");
		
		//set the end point of the array to be an unblocked location but color pink at location
		//used to show it's the end.  This will ensure that the point won't be treated as a 
		//blocked position
		mazeArr[end.getX()][end.getY()] = 'u';
		
		//declare sleepTimer to make it easier for bigger mazes
		int sleepTimer  = 100;
		
		//declare stack called path
		Array_Stack path = new Array_Stack();
		
		//push start value onto stack
		path.push(start);
		
		//use boolean endFound to signify if end has been found or not
		boolean endFound = false;
		
		//DFS stack algorithm
		while(!path.isEmpty() && !endFound){
			
			//Check x+1, x-1, y+1, y-1
			//if it is unblocked position push it onto the stack and color location green
			//also set to visited
			if(mazeArr[path.top().getX() + 1][path.top().getY()] == 'u') {
				tmp = new Point2d(path.top().getX()+1, path.top().getY());
				mazeArr[path.top().getX() + 1][path.top().getY()] = 'v';
				maze.setColor(path.top().getX()+1, path.top().getY(), Color.GREEN);
				mySleep(sleepTimer);
				path.push(tmp);
			}
			else if(mazeArr[path.top().getX() - 1][path.top().getY()] == 'u') {
				tmp = new Point2d(path.top().getX()-1, path.top().getY());
				mazeArr[path.top().getX() - 1][path.top().getY()] = 'v';
				maze.setColor(path.top().getX()-1, path.top().getY(), Color.GREEN);
				mySleep(sleepTimer);
				path.push(tmp);
			}
			else if(mazeArr[path.top().getX()][path.top().getY()+1] == 'u') {
				tmp = new Point2d(path.top().getX(), path.top().getY()+1);
				mazeArr[path.top().getX()][path.top().getY()+1] = 'v';
				maze.setColor(path.top().getX(), path.top().getY()+1, Color.GREEN);
				mySleep(sleepTimer);
				path.push(tmp);
			}
			else if(mazeArr[path.top().getX()][path.top().getY()-1] == 'u') {
				tmp = new Point2d(path.top().getX(), path.top().getY()-1);
				mazeArr[path.top().getX()][path.top().getY()-1] = 'v';
				maze.setColor(path.top().getX(), path.top().getY()-1, Color.GREEN);
				mySleep(sleepTimer);
				path.push(tmp);
			}
			//if there is no unblocked position we set the location to grey and pop it off the stack
			else {
				maze.setColor(path.top().getX(), path.top().getY(), Color.GRAY);
				mySleep(sleepTimer);
				path.pop();
			}
			//check if end has been found
			if(!path.isEmpty() && path.top().getX() == end.getX() && path.top().getY() == end.getY()) {
				endFound = true;
			}
		}
		//if the path is not empty, there is a solution so print it. else print no solution
		//set end back to pink because after algorithm it'll be changed to grey
		if(!path.isEmpty()) {
			maze.setColor(path.top().getX(), path.top().getY(), Color.PINK);
			System.out.println("Maze Solution: ");
			path.print();
		}
		else {
			maze.setColor(start.getX(), start.getY(), Color.BLUE);
			System.out.println("Maze has no solution");
		}
	}//end of main
	
	/*
	 * 	Function: grow an array of points
	 * 	Parameters: the array to grow, and its current size
	 */
	static Point2d[] array_grow(Point2d[] src, int size) {
		Point2d[] dst = new Point2d[size*2];
		for(int i = 0; i < size; i++) {
			dst[i] = src[i];
		}
		return dst;
	}
	
	/*
	 * 	Function: mySleep, written by professor Pat Troy
	 * 				Used to create 'animation' effect
	 */
	public static void mySleep( int milliseconds)
    {
      try
      {
        Thread.sleep(milliseconds);
      }
      catch (InterruptedException ie)
      {
      }
    }
	
}//end of class Maze
