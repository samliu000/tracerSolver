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
