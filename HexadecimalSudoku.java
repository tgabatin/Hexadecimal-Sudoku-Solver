import java.util.ArrayList;

/**
 * Class for recursively finding a solution to a Hexadecimal Sudoku problem.
 *
 * @author Biagioni, Edoardo, Cam Moore
 *     date August 5, 2016
 *     missing solveSudoku, to be implemented by the students in ICS 211
 *     Written method by Taylor D. Gabatino received help from Cam Moore & Matthew Kirts.
 */
public class HexadecimalSudoku {

  /**
   * Find an assignment of values to sudoku cells that makes the sudoku valid.
   *
   * @param sudoku the sudoku to be solved.
   * @return whether a solution was found if a solution was found, the sudoku is
   *         filled in with the solution if no solution was found, restores the
   *         sudoku to its original value.
   */
  public static boolean solveSudoku(int[][] sudoku) {
    // TODO: Implement this method recursively. You may use a recursive helper method.
    //throw new UnsupportedOperationException("solveSudoku not implemented.");
    
    //Helper method 
    boolean foundEmpty = false;
    int emptyRow = -1;
    int emptyCol = -1;
    
    //Traverse through the sudoku 
    for (int r = 0; r < sudoku.length; r++) {
      for (int c = 0; c < sudoku.length; c++) {
        if (sudoku[r][c] == -1 && !foundEmpty) {
          emptyRow = r;
          emptyCol = c;
          foundEmpty = true;
        }
      }
    }
    if (foundEmpty) {
      boolean solvedCell = solveSudokuRec(sudoku, emptyRow, emptyCol);
      if (solvedCell) {
        return true;
      } else {
        if (checkSudoku(sudoku, false)) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }
  
  /** 
   * Checks if the sudoku is solvable.
   * @param sudoku the sudoku matrix
   * @param emptyRow the unfilled rows
   * @param emptyCol the unfilled columns
   * @return true or false 
   */
  private static boolean solveSudokuRec(int[][] sudoku, int row, int column) {
    // TODO Auto-generated method stub
    ArrayList<Integer> possibleOutcome = legalValues(sudoku, row, column);
    
    for (int i = 0; i < possibleOutcome.size(); i++) {
      int input = possibleOutcome.get(i);
      sudoku[row][column] = input;
      
      boolean foundEmpty = false;
      int blankRow = -1;
      int blankCol = -1;
      
      //traverse through the sudoku and look for first blank cell
      for (int r = 0; r < sudoku.length; r++) {
        for (int c = 0; c < sudoku.length; c++) {
          if (sudoku[r][c] == -1 && !foundEmpty) {
            blankRow = r;
            blankCol = c;
            foundEmpty = true;
          }
        }
      }
      if (foundEmpty) {
        boolean solvedCell = solveSudokuRec(sudoku, blankRow, blankCol);
        if (solvedCell) {
          return true;
        } 
      } else {
        if (checkSudoku(sudoku, false)) {
          return true;
        } else {
          return false;
        }
      }
    }
    sudoku[row][column] = -1;
    return false;
  }

  /**
   * Find the legal values for the given sudoku and cell.
   * @param sudoku the sudoku being solved.
   * @param row the row of the cell to get values for.
   * @param column the column of the cell.
   * @return an ArrayList of the valid values.
   */
  public static ArrayList<Integer> legalValues(int[][] sudoku, int row, int column) {
    if (sudoku[row][column] != -1) {
      return null;
    } 
    ArrayList<Integer> check = new ArrayList();
    ArrayList<Integer> vals = new ArrayList();
    for (int i = 0; i < sudoku.length; i++) {
      for (int k = 0; k < sudoku.length; k++) {
        int cell = sudoku[i][column];        
        check.add(cell);
      }
    }  
    for (int j = 0; j < sudoku.length; j++) {
      for (int n = 0; n < sudoku.length; n++) {
        int cell = sudoku[row][j];
        
        check.add(cell);
      }
    }
    for (int k = 0; k < 4; k++) {
      for (int z = 0; z < 4; z++) {
        int testRow = (row / 4 * 4) + k; 
        int testCol = (column / 4 * 4) + z;
        int cell = sudoku[testRow][testCol];
        check.add(cell);
      }
    }    
    for (int i = 0; i < sudoku.length; i++) {
      if (check.indexOf(i) == -1) {
        vals.add(i);
      }
    }
    return vals;
  }

  /**
   * checks that the sudoku rules hold in this sudoku puzzle. cells that contain
   * 0 are not checked.
   *
   * @param sudoku the sudoku to be checked.
   * @param printErrors whether to print the error found, if any.
   * @return true if this sudoku obeys all of the sudoku rules, otherwise false.
   */
  public static boolean checkSudoku(int[][] sudoku, boolean printErrors) {
    if (sudoku.length != 16) {
      if (printErrors) {
        System.out.println("sudoku has " + sudoku.length + " rows, should have 16");
      }
      return false;
    }
    for (int i = 0; i < sudoku.length; i++) {
      if (sudoku[i].length != 16) {
        if (printErrors) {
          System.out.println("sudoku row " + i + " has "
              + sudoku[i].length + " cells, should have 16");
        }
        return false;
      }
    }

    for (int i = 0; i < sudoku.length; i++) {
      for (int j = 0; j < sudoku.length; j++) {
        int cell = sudoku[i][j];
        if (cell == -1) {
          continue; /* blanks are always OK */
        }
        if ((cell < 0) || (cell > 16)) {
          if (printErrors) {
            System.out.println("sudoku row " + i + " column " + j
                + " has illegal value " + String.format("%02X", cell));
          }
          return false;
        }
        /* does it match any other value in the same row? */
        for (int m = 0; m < sudoku.length; m++) {
          if ((j != m) && (cell == sudoku[i][m])) {
            if (printErrors) {
              System.out.println("sudoku row " + i + " has " + String.format("%X", cell)
                  + " at both positions " + j + " and " + m);
            }
            return false;
          }
        }
        /* does it match any other value it in the same column? */
        for (int k = 0; k < sudoku.length; k++) {
          if ((i != k) && (cell == sudoku[k][j])) {
            if (printErrors) {
              System.out.println("sudoku column " + j + " has " + String.format("%X", cell)
                  + " at both positions " + i + " and " + k);
            }
            return false;
          }
        }
        /* does it match any other value in the 4x4? */
        for (int k = 0; k < 4; k++) {
          for (int m = 0; m < 4; m++) {
            int testRow = (i / 4 * 4) + k; /* test this row */
            int testCol = (j / 4 * 4) + m; /* test this col */
            if ((i != testRow) && (j != testCol) && (cell == sudoku[testRow][testCol])) {
              if (printErrors) {
                System.out.println("sudoku character " + String.format("%X", cell) + " at row "
                    + i + ", column " + j + " matches character at row " + testRow + ", column "
                    + testCol);
              }
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * Converts the sudoku to a printable string.
   *
   * @param sudoku the sudoku to be converted.
   * @param debug whether to check for errors.
   * @return the printable version of the sudoku.
   */
  public static String toString(int[][] sudoku, boolean debug) {
    if ((!debug) || (checkSudoku(sudoku, true))) {
      String result = "";
      for (int i = 0; i < sudoku.length; i++) {
        if (i % 4 == 0) {
          result = result + "+---------+---------+---------+---------+\n";
        }
        for (int j = 0; j < sudoku.length; j++) {
          if (j % 4 == 0) {
            result = result + "| ";
          }
          if (sudoku[i][j] == -1) {
            result = result + "  ";
          } else {
            result = result + String.format("%X", sudoku[i][j]) + " ";
          }
        }
        result = result + "|\n";
      }
      result = result + "+---------+---------+---------+---------+\n";
      return result;
    }
    return "illegal sudoku";
  }
}
