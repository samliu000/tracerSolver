/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracersolver;

import java.util.Scanner;
import java.util.Vector;
import static tracerSolver.TracerSolver.convertToString;
import static tracerSolver.TracerSolver.pointToDirectionConverter;

/**
 *
 * @author nickw
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Number of Rows: ");
        int row = in.nextInt();
        System.out.println("Number of Cols: ");
        int col = in.nextInt();
        System.out.println("Enter starting row");
        int startRow = in.nextInt();
        System.out.println("Enter starting column");
        int startCol = in.nextInt();
        tracerSolver.TracerSolver grid = new tracerSolver.TracerSolver(row, col, startRow, startCol);

        System.out.println("Would you like to remove any blocks? N for no; Any other key for Yes");
        while(!in.next().toLowerCase().equals("n")) {
                System.out.print("Row: ");
                int removedRow = in.nextInt();
                System.out.print("Column: ");
                int removedCol = in.nextInt();
                if(removedRow == startRow && removedCol == startCol) {
                        System.out.println("You cannot remove the starting position");
                } else {
                        grid.removeSpace(removedRow, removedCol);
                }
                System.out.println("Another?");
        }
        if(grid.solveTracer(startRow, startCol)) {
                Vector<tracerSolver.Point> listOfMoves = grid.getDirections();
                Vector<String> stringForm = pointToDirectionConverter(listOfMoves, startRow, startCol);
                int[][] numberedGrid = new int[row][col];
                System.out.println(stringForm.toString());
                for(int i = 1; i < listOfMoves.size()+1; i++) {
                        int row1 = listOfMoves.elementAt(i-1).x;
                        int col1 = listOfMoves.elementAt(i-1).y;
                        numberedGrid[row1][col1] = i;
                }
                convertToString(numberedGrid);
        } else {
                System.out.println("Puzzle could not be solved");
        }
        in.close();
    }
}
