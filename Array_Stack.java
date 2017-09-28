/*
 * 	Name: Arbaaz Meghni
 * 	File: Array_Stack.java
 * 	Description: File contains object of a stack implementation of an array of points
 */
import java.io.*;

//declare class
public class Array_Stack {
	
	//array of points
	private Point2d[] values;
	
	//size of array
	private int iSize;
	
	//top of the array
	private int iTop;

	//constructor
	public Array_Stack() {
		iSize = 10;
		iTop = -1;
		values = new Point2d[iSize];
	}

	/*
	 * 	Function: push value onto stack
	 * 	Parameters: a point
	 * 	Return: none
	 */
	public void push(Point2d location) {
		if(isFull()) {
			grow();
		}
		iTop++;
		values[iTop] = location;
	}

	/*
	 * 	Function: grow the array
	 * 	Parameters: none
	 * 	Return: none
	 */
	public void grow() {
		Point2d[] tmp = new Point2d[iSize*2];
		for(int i = 0; i < iSize; i++) {
			tmp[i] = values[i];
		}
		values = tmp;
		iSize *= 2;
	}

	/*
	 * 	Function: pop value off of stack
	 * 	Parameters: none
	 * 	Return: none
	 */
	public void pop() {
		iTop--;
	}
	
	/*
	 * 	Function: get value at top of stack
	 * 	Parameters: none
	 * 	Return: point at top of stack
	 */
	public Point2d top() {
		return values[iTop];
	}
	
	/*
	 * 	Function: check if stack is empty
	 * 	Parameters: none
	 * 	Return: true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return iTop == -1;
	}

	/*
	 * 	Function: check if stack is full
	 * 	Parameters: none
	 * 	Return: true if full, false otherwise
	 */
	public boolean isFull() {
		return iTop == iSize-1;
	}
	
	/*
	 * 	Function: print the stack from beginning
	 * 	Parameters: none
	 * 	Return: none
	 */
	public void print() {
		for(int i = 0; i <= iTop; i++) {
			System.out.println("(" + values[i].getX() + "," + values[i].getY() + ")");
		}
	}
}
