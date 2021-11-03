package demos;

import edu.wofford.woclo.*;

public class WordSearch {

  public boolean is_legal(int row, int column, String grid, int width, int height) {
    return (0 <= row) && (row < height) && (0 <= column) && (column < width);
  }

  public String longest_path(String grid, int[][] longest, int row, int col) {
    // if not is_legal(row, col, grid):
        // return -1
    // else
      if (longest[row][col] < 0) {
        // vals = [-1]
        // neighbors = [(-1, 0), (1, 0), (0, -1), (0, 1)]
        // for offrow, offcol in neighbors:
            // nrow, ncol = row + offrow, col + offcol
            // if is_legal(nrow, ncol, grid) and grid[nrow][ncol] > grid[row][col]:
            // longest[nrow][ncol] = longest_path(grid, longest, nrow, ncol)
            // vals.append(longest[nrow][ncol])
        // longest[row][col] = max(vals) + 1
      // return longest[row][col]
      }
  }

  public boolean search(String[] args) {
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("grid", "String", "the grid to search");
    argParse.addPositional("target", "String", "the target word");
    argParse.addNonPositional("width", "int", "the grid width", "5");
    argParse.addNonPositional("height", "int", "the grid height", "5");

    argParse.parse(args);

    String grid = argParse.getValueString("grid");
    String target = argParse.getValueString("target");
    int width = argParse.getValueInt("width");
    int height = argParse.getValueInt("height");

    return true;
    // word search
  }

  public static void main(String... args) {
    WordSearch w = new WordSearch();
    boolean b = w.search(args);
    System.out.println(b);
  }
}
