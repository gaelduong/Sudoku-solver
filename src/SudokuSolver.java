import java.util.ArrayList;
import java.util.Arrays;

public class SudokuSolver {
	
	//input: 2D array sudoku
			//entry => which row, which column, which box
			//getBox(entry), getRow(entry), getColumn(entry)
			//row = list, coln => list, box => list
			//row => list of indexes of empty spots
			//getMissingNumbers(list)
			
			//step 1: get all missing numbers of each row (9), each column(9), and each box (9) = 27 lists
			
			//rows = {r0,r1,r2,r3,r4,r5,r6,r7,r8};
			//cols = {c0,c1,c2,c3,c4,c5,c6,c7,c8};
			//boxes = {b0,b1,b2,b3,b4,b5,b6,b7,b8};
			//step 2: 
			// Find obvious answer for ROWS
			//for each missing entry (i,j) of each row i
			// if rows[i][x] is in getBox(i,j)
				// if rows[i][x] is in cols[j]
					// if(spot_to_fill not empty)
						// spot_to_fill = [i,j]
				// else break and move on to next x
			//else continue to next entry (i,j)
			// after looping all missing entries :
				// fill sudoku[spot_to_fill[0]][spot_to_fill[1]] = x && score++
			
			//Find obvious for COLS
			
			//stop when score stops improving
	
	static ArrayList<ArrayList<Integer>> miss_rows = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<Integer>> miss_cols = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<Integer>> miss_boxes = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args) {
		
		int[][] b = 
			{{0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0}};
		
		int[][] sudoku = 
			{{3,2,0,0,0,0,7,1,0},
			 {0,0,5,2,0,0,4,0,8},
			 {8,0,0,0,3,0,0,0,0},
			 {0,0,8,7,2,0,0,0,4},
			 {0,0,0,0,0,0,0,0,0},
			 {5,0,0,0,8,1,9,0,0},
			 {0,0,0,0,9,0,0,0,5},
			 {2,0,9,0,0,4,3,0,0},
			 {0,5,1,0,0,0,0,2,9}};
		
		int[][] sudoku4 = 
			{{0,0,8,0,6,3,0,0,0},
			 {0,4,3,0,0,0,0,9,7},
			 {0,0,2,9,0,0,0,0,0},
			 {0,8,0,0,0,0,0,7,6},
			 {1,0,0,5,4,6,0,0,3},
			 {3,2,0,0,0,0,0,5,0},
			 {0,0,0,0,0,5,7,0,0},
			 {2,7,0,0,0,0,1,4,0},
			 {0,0,0,1,9,0,8,0,0}};

		int[][] sudoku3 = 
			{{3,2,9,0,0,9,0,7,0},
			 {0,4,9,6,0,0,8,0,5},
			 {6,0,0,8,0,5,1,0,9},
			 {0,6,8,9,0,0,7,3,0},
			 {5,9,0,4,3,0,0,6,0},
			 {0,3,0,2,8,0,5,9,0},
			 {4,0,7,0,6,8,0,0,2},
			 {0,0,0,0,1,4,3,8,0},
			 {3,8,6,0,0,2,0,0,7}};
		int[][] sudoku2 =
			{{8,0,0,7,1,0,0,0,2},
			 {5,1,0,0,0,0,0,8,0},
			 {0,0,3,9,0,8,1,0,6},
			 {0,7,0,2,0,1,6,0,0},
			 {0,0,0,6,3,7,0,0,0},
			 {0,0,1,8,0,5,0,4,0},
			 {7,0,4,1,0,9,3,0,0},
			 {0,8,0,0,0,0,0,2,4},
			 {6,0,0,0,7,2,0,0,8}};
		//solve(sudoku); //return a filled sudoku
		/*int[] l = {5,1,3,4,9};
		ArrayList<Integer> missNumbers = getMissingNumbers(l);
		System.out.println(Arrays.toString(missNumbers.toArray()));
		
		System.out.println(Arrays.toString(columnToList(sudoku,8)));
		
		System.out.println(Arrays.toString(boxToList(sudoku,5)));
		
		System.out.println(getBoxIndex(7,0));
		*/
		
		fillMiss(sudoku);
		
		//System.out.println(Arrays.toString(miss_boxes.toArray()));
		int numMiss = 1000;
		int n = 0;
		while(true) {
			
			solve(sudoku);
			n++;
			//if (n==1) break;
			int currentNumMiss = getTotalMissingNumbers(sudoku);
			if(currentNumMiss < numMiss) numMiss = currentNumMiss;
			else break;
			
			
		}
//		while(true) {
//			
//			solve2(sudoku);
//			n++;
//			if (n==1) break;
//			int currentNumMiss = getTotalMissingNumbers(sudoku);
//			if(currentNumMiss < numMiss) numMiss = currentNumMiss;
//			else break;
//			
//			
//		}
		
		//System.out.println(Arrays.deepToString(sudoku));
		//System.out.println(Arrays.toString(miss_rows.get(8).toArray()));
		for(int i=0;i<9;i++) {
			System.out.println(Arrays.toString(sudoku[i]));
		}
		
		
		
	}
	public static void solve(int[][]sudoku) {
		solveRow(sudoku);
		solveColumn(sudoku);
	}
	
	public static void solveRow(int[][] sudoku) {

		//for each sudoku row
		for(int i = 0; i < 9; i++)
		{
			for(int k = 0; k < miss_rows.get(i).size()/*miss_rows.get(i).size()*/; k++) 
			{
				int[] spot_to_fill = {-1,-1};
				for(int j = 0; j < 9; j++)
				{
					if(sudoku[i][j] == 0) 
					{
						int missNum = miss_rows.get(i).get(k);
						if(miss_boxes.get(getBoxIndex(i,j)).contains(missNum)) //if I can fill up missNum in this box
						{
							if(miss_cols.get(j).contains(missNum)) //if I can fill up missNum in this column
							{
								if(spot_to_fill[0] == -1) //if there's no potential fill beforehand
								{
									spot_to_fill[0] = i;
									spot_to_fill[1] = j;
								}
								else{
									
									spot_to_fill[0] = -1;
									spot_to_fill[1] = -1;
									break; // break if it already has a potential fill
								}
								
							}
						}
						
					}
				}
				if(spot_to_fill[0] != -1) 
				{
					//System.out.println(Arrays.toString(miss_rows.get(i).toArray()));
					int number = miss_rows.get(i).get(k);
					sudoku[spot_to_fill[0]][spot_to_fill[1]] = number;
					miss_rows.get(i).remove(miss_rows.get(i).indexOf(number));
					miss_cols.get(spot_to_fill[1]).remove(miss_cols.get(spot_to_fill[1]).indexOf(number));
					System.out.println(spot_to_fill[0] + " " + spot_to_fill[1]);
				}
				
			}
			
		}
		
		
		
		
		
		
	}
	
	
	//method check if 
	
	public static void solveColumn(int[][]sudoku) {

		//for each sudoku column
				for(int i = 0; i < 9; i++)
				{
					for(int k = 0; k < miss_cols.get(i).size()/*miss_rows.get(i).size()*/; k++) 
					{
						int[] spot_to_fill = {-1,-1};
						for(int j = 0; j < 9; j++)
						{
							if(sudoku[j][i] == 0) 
							{
								int missNum = miss_cols.get(i).get(k);
								if(miss_boxes.get(getBoxIndex(j,i)).contains(missNum)) //if I can fill up missNum in this box
								{
									if(miss_rows.get(j).contains(missNum)) //if I can fill up missNum in this column
									{
										if(spot_to_fill[0] == -1) //if there's no potential fill beforehand
										{
											spot_to_fill[0] = j;
											spot_to_fill[1] = i;
										}
										else{
											
											spot_to_fill[0] = -1;
											spot_to_fill[1] = -1;
											break; // break if it already has a potential fill
										}
										
									}
								}
								
							}
						}
						if(spot_to_fill[0] != -1) 
						{
							System.out.println(Arrays.toString(miss_cols.get(i).toArray()));
							int number = miss_cols.get(i).get(k);
							sudoku[spot_to_fill[0]][spot_to_fill[1]] = number;
							miss_cols.get(i).remove(miss_cols.get(i).indexOf(number));
							miss_rows.get(spot_to_fill[0]).remove(miss_rows.get(spot_to_fill[0]).indexOf(number));
							System.out.println(spot_to_fill[0] + " " + spot_to_fill[1]);
						}
						
					}
					
				}
		
	}
	
	public static void fillMiss(int[][] sudoku) {
		//miss_rows
		for(int i=0; i < 9; i++)
		{
			ArrayList<Integer> miss_row = getMissingNumbers(sudoku[i]);
			miss_rows.add(miss_row);
		}
		//miss_cols
		for(int i=0; i < 9; i++)
		{
			ArrayList<Integer> miss_col = getMissingNumbers(columnToList(sudoku,i));
			miss_cols.add(miss_col);
		}
		
		//miss_boxes
		for(int i=0; i < 9; i++)
		{
			ArrayList<Integer> miss_box = getMissingNumbers(boxToList(sudoku,i));
			miss_boxes.add(miss_box);
		}
	}
	
	public static ArrayList<Integer> getMissingNumbers(int[] list){
		ArrayList<Integer> missNums = new ArrayList<Integer>();
		for(int i = 1; i < 10; i++) {
			if(!contain(list,i)) missNums.add(i);
		}
		return missNums;
	}
	
	public static boolean contain(int[] list, int item) {
		for(int i = 0; i < list.length; i++) {
			if(list[i] == item) return true;
		}
		return false;
	}
	
	public static int[] columnToList(int[][] sudoku, int colIndex) {
		int[] colList = new int[9];
		for(int i = 0; i < 9; i++) {
			colList[i] = sudoku[i][colIndex];
		}
		return colList;
	}
	
	public static int[] boxToList(int[][] sudoku, int boxIndex) {
		//0 => 0 to 2 for j and 0 to 2 for i
		//1 => 3 to 5 for j and 0 to 2 for i
		//2 => 6 to 8 for j and 0 to 2 for j
		//...
		int x = -1;
		if(boxIndex == 0 || boxIndex == 1 || boxIndex == 2) {
			x = 0;
		}
		else if(boxIndex == 3 || boxIndex == 4 || boxIndex == 5) {
			boxIndex = boxIndex - 3;
			x = 3;
		}
		else if(boxIndex == 6 || boxIndex == 7 || boxIndex == 8) {
			boxIndex = boxIndex - 6;
			x = 6;
		}
		int[] boxList = new int[9];
		int k = 0;
		for(int i = x; i < x+3; i++) {
			for(int j = boxIndex*3; j < boxIndex*3+3; j++){
				boxList[k] = sudoku[i][j];
				k++;
			}
		}
		
		return boxList;
	}
	
	public static int getBoxIndex(int i, int j) {
		if(j < 3) { //it's either 0,3,6
			if(i < 3) return 0;
			else if(i < 6) return 3;
			else return 6;
		}
		else if(j < 6) { //it's either 1,4,7
			if(i < 3) return 1;
			else if(i < 6) return 4;
			else return 7;
		}
		else if(j < 9) { //it's either 2,5,8
			if(i < 3) return 2;
			else if(i < 6) return 5;
			else return 8;
		}
		return -1;
	}
	public static int getTotalMissingNumbers(int[][]sudoku) {
		int count = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(sudoku[i][j] == 0) count++;
			}
		}
		return count;
	}
	
	
	
	

}
