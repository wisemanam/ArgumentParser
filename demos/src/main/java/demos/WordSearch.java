package demos;

import edu.wofford.woclo.*;
import java.awt.*;
import java.util.*;

import org.omg.CORBA.IntHolder;

public class WordSearch {

  public String wordsearch(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addPositional("grid", "string", "the grid to search");
      parser.addPositional("target", "string", "the target word");
      parser.addNonPositional("width", "integer", "the grid width", "5");
      parser.addNonPositional("height", "string", "the grid height", "5");
      parser.parse(arguments);
      String grid = parser.getValue("grid");
      String target = parser.getValue("target");
      int width = parser.getValue("width");
      int height = parser.getValue("height");
      if (grid.length() != width * height) {
        return "WordSearch error: grid dimensions (" + width + " x " + height + ") do not match grid length (" + width * height + ")";
      }
      String[] grid_split = grid.split("", 0);
      String[][] g = new String[width][height];
      int k = 0;
      while (k < grid_split.length) {
        for (int i = 0; i < width; i++) {
          for (int j = 0; i < height; j++) {
            g[i][j] =  grid_split[k];
            k++;
          }
        }
      }
      String[] target_split = target.split("", 0);
      String first = target_split[0];
      Deque<Point> potentialStart = new ArrayDeque<Point>();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (g[i][j] == first) {
            Point p = new Point(i, j);
            potentialStart.add(p);
          }
        }
      }

      Deque<Point> location = findWord(target, g, potentialStart.getFirst(), 0);
    } catch (HelpException e){
      return parser.constructHelp("WordSearch", "Find a target word in a grid.");
    } catch (TooManyException e) {
      String firstExtra = e.getFirstExtra();
      return "WordSearch error: the value " + firstExtra + " matches no argument";
    } catch (TooFewException e) {
      String next = e.getNextExpectedName();
      return "WordSearch error: the argument " + next + " is required";
    } catch (NoValueException e) {
      String val_expected = e.getNameMissingValue();
      return "WordSearch error: no value for " + val_expected;
    } catch (WrongTypeException e) {
      String wrongValue = e.getWrongValue();
      return "WordSearch error: the value " + wrongValue + " is not of type integer";
    } catch (ArgumentNameNotSpecifiedException e) {
      
    }
  }

  public Deque<Point> findWord(String target, String[][] grid, Point p, int currentTargetIndex) {
    String[] targetSplit = target.split("", 0);
    int[][] been = new int[grid.length][grid[0].length];
    int row = (int)p.getX();
    int col = (int)p.getY();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        been[i][j] = -1;
      }
    }
    if (is_legal(row, col, grid) == false) {
      been[row][col] = 0;
    } else {
      if (been[row][col] < 0) {
        been[row][col] = 0;
        int[][] neighbors = {
          {-1,0},
          {1,0},
          {0,-1},
          {0,1},
        };
        for (int offrow = 0; offrow < 4; offrow++) {
          for (int offcol = 0; offcol < 2; offcol++) {
            int new_row = row + offrow;
            int new_col = col + offcol;
            if (is_legal(new_row, new_col, grid) && grid[new_row][new_col] == targetSplit[currentTargetIndex +1 ] && been[new_row][new_col] == -1) {

            }
          }
        }
      }
    }
  }

  public boolean is_legal(int row, int column, String[][] grid) {
    return (0 <= row) && (row < grid[0].length) && (0 <= column) && (column < grid.length);
  }

  public boolean search(String grid, String target, int row, int col) {
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

  public static void main(String... args) {
    WordSearch w = new WordSearch();
    String location = w.wordsearch(args);
    System.out.println(location);
  }
}
