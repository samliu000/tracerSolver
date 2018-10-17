package tracerSolver;

import java.util.Scanner;
import java.util.Vector;
import java.util.LinkedList; 
import java.util.Queue;
import java.lang.Math;

public class tracerSolver {
	private int[][] grid;
	private int numRow;
	private int numCol;
	private int startRow;
	private int startCol;
	private Vector<Point> directions;
	
	/**
	 * Constructs a grid of ones with the exception of the starting position, which is zero
	 * 1: Unmarked path
	 * 0: Marked path
	 * Program will not consider paths that are 0 to be a possible move
	 * @param row: number of rows in the grid
	 * @param col: number of columns in the grid
	 * @param startRow: row component of starting position
	 * @param startCol: column compoenent of starting position
	 */
	public tracerSolver(int row, int col, int startRow, int startCol) {
		numRow = row;
		numCol = col;
		this.startRow = startRow;
		this.startCol = startCol;
		//construct grid
		grid = new int[row][col];
		//a list of points that represent the solution to the tracer map
		directions = new Vector<Point>();
		//fills map with 1's, indicating untraveled paths. Traveled paths will be marked with 0
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				grid[i][j] = 1;
			}
		}
		//marks starting point
		grid[startRow][startCol] = 0;
	}
	
	/**
	 * Used in each recursive step to determine if puzzle has been solved
	 * @return: a boolean of whether the grid has been solved (completely filled with 0's)
	 */
	public boolean gridSolved() {
		for(int i = 0; i < numRow; i++) {
			for(int j = 0; j < numCol; j++) {
				//if a block is 1, then the puzzle is not solved yet
				if(grid[i][j] == 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** 
	 * Lists all possible moves in cardinal directions of the current block
	 * @param currentRow: current row 
	 * @param currentCol: current column
	 * @return: a Queue of Points that list all possible moves from the current position
	 */
	public Queue<Point> possibleMoves(int currentRow, int currentCol){
		//declares/initializes a queue of points
		Queue<Point> directionList = new LinkedList<Point>();
		for(int r = currentRow - 1; r < currentRow + 2 && r < numRow; r++){
	        for(int c = currentCol - 1; c < currentCol + 2 && c < numCol; c++){
	            //only considers the neighbors in cardinal directions
	            if((Math.abs(currentRow - r) + Math.abs(currentCol - c)) == 1 && r >= 0 && c >= 0){
	            	//checks if block in question is marked
	                if(grid[r][c] != 0) {
	                	Point currentPoint = new Point(r ,c);
	                	directionList.add(currentPoint);
	                }
	            	
	            }
	        }
	    }
		return directionList;
	}
	
	/**
	 * Uses recursive backtracking to explore all possible paths
	 * @param currentRow: current row
	 * @param currentCol: current column
	 * @return: true or false based on whether the puzzle is solved or not
	 */
	public boolean solveTracer(int currentRow, int currentCol) {
		//base case
		if(gridSolved()) {
			return true;
		} 
		//recursive case
		else {
			//gets possible moves for current block
			Queue<Point> possibleMoves = possibleMoves(currentRow, currentCol);
			int size = possibleMoves.size();
			for(int i = 0; i < size; i++) {
				Point nextSpot = possibleMoves.poll();
				int nextRow = nextSpot.getX();
				int nextCol = nextSpot.getY();
				//modify grid and list of directions
				grid[nextRow][nextCol] = 0;
				directions.add(nextSpot);
				//explore possible paths
				if(solveTracer(nextRow, nextCol)) {
					return true;
				}
				//unmodify grid and list of directions
				grid[nextRow][nextCol] = 1;
				directions.remove(directions.size() - 1);
			}
		}
		return false;
	}
	
	/**
	 * Converts a vector of points into a vector of strings indicating directional movement
	 * @param pointsList: vector to convert
	 * @param startRow: starting row
	 * @param startCol: starting column
	 * @return: a vector of strings indicating directional movement
	 */
	public static Vector<String> pointToDirectionConverter(Vector<Point> pointsList, int startRow, int startCol){
		Vector<String> returnList = new Vector<String>();
		//keeps track of the current block
		Point current = new Point(startRow, startCol);
		for(int i = 0; i < pointsList.size(); i++) {
			Point nextPosition = pointsList.elementAt(i);
			//change in row
			if(nextPosition.getX() > current.getX()) {
				returnList.add("Down");
			} 
			//change in row
			else if(nextPosition.getX() < current.getX()) {
				returnList.add("Up");
			} 
			//change in column
			else if(nextPosition.getY() > current.getY()) {
				returnList.add("Right");
			} 
			//change in column
			else {
				returnList.add("Left");
			}
			//makes the next position the current position
			current = nextPosition;
		}
		return returnList;
	}
	
	/**
	 * Prints out the contents of a 2D array
	 * @param grid: 2D array you want to print
	 * @param startRow: starting row
	 * @param startCol: starting column
	 */
	public static void printSteps(int[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				//formatting (spacing off if number of steps in directions > 99 steps)
				if(grid[i][j] < 10) {
					System.out.print(" ");
				}
				System.out.printf("%s", grid[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints arrows
	 * @param grid: the grid to print out
	 */
	public static void printArrows(String[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == null) {
					System.out.printf("%s",  "0 ");
				} else {
					System.out.printf("%s", grid[i][j]+ " ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Uses a list of directions to fill a 2D array with arrows representing directional movement
	 * @param listDirections: a vector of strings representing directional movement
	 * @return: a 2D array of arrows
	 */
	public String[][] convertToArrows(Vector<String> listDirections) {
		String[][] gridArrows = new String[numRow][numCol];
		gridArrows[startRow][startCol] = "X";
		int currentRow = startRow;
		int currentCol = startCol;
		for(int i = 0; i < listDirections.size(); i++) {
			if(listDirections.elementAt(i).equals("Right")) {
				gridArrows[currentRow][currentCol + 1] = "→";
				currentCol++;
			} else if(listDirections.elementAt(i).equals("Left")) {
				gridArrows[currentRow][currentCol - 1] = "←";
				currentCol--;
			} else if(listDirections.elementAt(i).equals("Up")) {
				gridArrows[currentRow - 1][currentCol] = "↑";
				currentRow--;
			} else {
				gridArrows[currentRow + 1][currentCol] = "↓";
				currentRow++;
			}
		}
		return gridArrows;
	}
	/**
	 * Gives user the option to mark spaces
	 * @param rowR: selected row
	 * @param colR: selected column
	 */
	public void removeSpace(int rowR, int colR) {
		grid[rowR][colR] = 0;
	}
	
	/** 
	 * Access to directions
	 * @return: vector of points
	 */
	public Vector<Point> getDirections(){
		return directions;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Begin?");
		while(!in.next().toLowerCase().equals("n")) {
			
			//prompts for constuctor parameters
			System.out.println("Number of Rows: ");
			int row = in.nextInt();
			System.out.println("Number of Cols: ");
			int col = in.nextInt();
			System.out.println("Enter starting row");
			int startRow = in.nextInt();
			System.out.println("Enter starting column");
			int startCol = in.nextInt();
			
			//constructs object
			tracerSolver grid = new tracerSolver(row, col, startRow, startCol);
			
			//repeatedly asks the user if there are any spaces to mark
			System.out.println("Would you like to remove any blocks? N for no; Any other key for Yes");
			while(!in.next().toLowerCase().equals("n")) {
				System.out.print("Row: ");
				int removedRow = in.nextInt();
				System.out.print("Column: ");
				int removedCol = in.nextInt();
				//removing starting position is not allowed
				if(removedRow == startRow && removedCol == startCol) {
					System.out.println("You cannot remove the starting position");
				} else {
					grid.removeSpace(removedRow, removedCol);
				}
				System.out.println("Another? N for no; Any other key for Yes");
			}
			
			//start timer
			double startTime = System.nanoTime();
			
			//attempt to solve puzzle
			if(grid.solveTracer(startRow, startCol)) {
				Vector<Point> listOfMoves = grid.getDirections();
				Vector<String> stringForm = pointToDirectionConverter(listOfMoves, startRow, startCol);
				
				//prints out solution steps in the form of directional movement
				System.out.println();
				System.out.println("Directional Instructions: ");
				System.out.println(stringForm.toString());
				System.out.println();
				
				//constructs and initializes a grid to give a visual representation of the solution
				int[][] numberedGrid = new int[row][col];
				for(int i = 1; i < listOfMoves.size() + 1; i++) {
					int row1 = listOfMoves.elementAt(i - 1).getX();
					int col1 = listOfMoves.elementAt(i - 1).getY();
					numberedGrid[row1][col1] = i;
				}
				
				//Visualization using numbered steps
				System.out.println("Visualization of Steps:");
				printSteps(numberedGrid);
				System.out.println();
				grid.convertToArrows(stringForm);
				System.out.println();
				
				//Visualization using arrows
				System.out.println("Visualization using arrows: ");
				printArrows(grid.convertToArrows(stringForm));
				System.out.println();
				
				//stop timer and print out how long program took to solve puzzle
				double duration = System.nanoTime() - startTime;
				System.out.println();
				System.out.println("It only took me " + duration/1000000000 + " seconds to solve it!");
				System.out.println();
				
				//prompts user if they want to solve another puzzle
				System.out.println("Another? N for no; Any other key for Yes");
			} 
			//if puzzle could not be solved, indicate so and reprompt user
			else {
				System.out.println("Puzzle could not be solved");
				System.out.println("Another? N for no; Any other key for Yes");
			}
		}
		
		System.out.println("Done");
		
		in.close();
	}
	
}
