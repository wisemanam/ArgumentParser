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
      parser.addNonPositional("height", "integer", "the grid height", "5");
      parser.parse(arguments);
      String grid = parser.getValue("grid");
      String target = parser.getValue("target");
      int width = parser.getValue("width");
      int height = parser.getValue("height");
      if (grid.length() != width * height) {
        return "WordSearch error: grid dimensions (" + width + " x " + height + ") do not match grid length (" + grid.length() + ")";
      }
      String[] grid_split = grid.split("", 0);
      String[][] g = new String[height][width];
      int k = 0;
      for (int i = 0; i < height; i++) {
        for (int j = 0; i < width; j++) {
          g[i][j] =  grid_split[k];
          k++;
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
      Deque<Point> location = new ArrayDeque<Point>();
      while (!potentialStart.isEmpty()) {
        location.add(potentialStart.getFirst());
        location = findWord(target_split, g, location, 0);
        if (location.size() == target.length()) {
          return toString(target_split, location);
        } else {
          location.clear();
          potentialStart.removeFirst();
        }
      }
      return target + " not found";
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
    }
  }

  public Deque<Point> findWord(String[] targetSplit, String[][] grid, Deque<Point> locations, int currentTargetIndex) {
    if (currentTargetIndex == (targetSplit.length - 1)) {
      return locations;
    }
    Point p = locations.getLast();
    int row = (int)p.getX();
    int col = (int)p.getY();
    int[] neighbors = {-1,0,1,0,0,-1,0,1};
    for (int i = 0; i < neighbors.length; i+=2) {
      int nrow = row + neighbors[i];
      int ncol = col + neighbors[i+1];
      if (is_legal(nrow, ncol, grid) && grid[nrow][ncol] == targetSplit[currentTargetIndex +1]) {
        locations.add(p);
        findWord(targetSplit, grid, locations, currentTargetIndex + 1);
      }
    }
    return locations;
  }

  public boolean is_legal(int row, int column, String[][] grid) {
    return (0 <= row) && (row < grid[0].length) && (0 <= column) && (column < grid.length);
  }

  public String toString(String[] targetSplit, Deque<Point> locations) {
    String string = "";
    for (int i = 0; i < targetSplit.length; i++) {
      Point p = locations.pop();
      string += targetSplit[i] + ":" + p.getX() + "," + p.getY() + " ";
    }
    return string.substring(0, string.length() -1);
  }

  public static void main(String... args) {
    WordSearch w = new WordSearch();
    String find = w.wordsearch(args);
    System.out.println(find);
  }
}
