package tracerSolver;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Map;
import java.lang.Math;
import java.util.Set;
import java.io.IOException;

public class TracerSolver {
	private int[][] grid;
	private int numRow;
	private int numCol;
	private int startRow;
	private int startCol;
	Vector<Point> directions;
	      
	public TracerSolver(int numRow, int numCol, int startRow, int startCol) {
		this.numRow = numRow;
		this.numCol = numCol;
		this.startRow = startRow;
		this.startCol = startCol;
		grid = new int[numRow][numCol];
		directions = new Vector<>();               
		for(int i = 0; i < numRow; i++) {
			for(int j = 0; j < numCol; j++) {
				grid[i][j] = 1;
			}
		}
		grid[startRow][startCol] = 0;
	}
	
	public Vector<Point> getDirections(){
		return directions;
	}
	
	private boolean gridSolved() {
            for(int i = 0; i < numRow; i++) {
                for(int j = 0; j < numCol; j++) {
                    if(grid[i][j] == 1) {
                        return false;
                    }
                }
            }
		return true;
	}
	
	private Queue<Point> possibleMoves(int currentRow, int currentCol){
            Queue<Point> directionList = new LinkedList<>();
            for(int r = currentRow - 1; r < currentRow + 2 && r < numRow; r++){
                for(int c = currentCol - 1; c < currentCol + 2 && c < numCol; c++){
                    //only considers the neighbors in cardinal directions
                    if((Math.abs(currentRow - r) + Math.abs(currentCol - c)) == 1 && r >= 0 && c >= 0){
                        if(grid[r][c] != 0) {
                                Point currentPoint = new Point(r ,c);
                                directionList.add(currentPoint);
                        }
                    }
                }
            }
            return directionList;
	}
	
	public void removeSpace(int rowR, int colR) {
		grid[rowR][colR] = 0;
	}
        
	public boolean solveTracer(int currentRow, int currentCol) {
            if(gridSolved()) {
                return true;
            } else {
                Queue<Point> possibleMoves = possibleMoves(currentRow, currentCol);
                int size = possibleMoves.size();
                for(int i = 0; i < size; i++) {
                    Point nextSpot = possibleMoves.poll();
                    int nextRow = nextSpot.x;
                    int nextCol = nextSpot.y;
                    //modify
                    grid[nextRow][nextCol] = 0;
                    directions.add(nextSpot);
                    //explore
                    if(solveTracer(nextRow, nextCol)) {
                            return true;
                    }
                    //unmodify
                    grid[nextRow][nextCol] = 1;
                    directions.remove(directions.size() - 1);
                }
            }
            return false;
	}
	
	public static Vector<String> pointToDirectionConverter(Vector<Point> pointsList, int startRow, int startCol){
            Vector<String> returnList = new Vector<>();
            Point previous = new Point(startRow, startCol);
            for(int i = 0; i < pointsList.size(); i++) {
                Point currentMove = pointsList.elementAt(i);
                if(currentMove.x > previous.x) {
                        returnList.add("Down");
                } else if(currentMove.x < previous.x) {
                        returnList.add("Up");
                } else if(currentMove.y > previous.y) {
                        returnList.add("Right");
                } else {
                        returnList.add("Left");
                }
                previous = currentMove;
            }
            return returnList;
	}
	
    public static void convertToString(int[][] grid) {
        for(int i = 0; i < grid.length; i ++) {
            for(int j = 0; j < grid[1].length; j++) {
                if(grid[i][j] < 10) {
                    System.out.print(" ");
                }
                System.out.printf("%s", grid[i][j]+ " ");
            }
            System.out.println();
        }
    }	
}
