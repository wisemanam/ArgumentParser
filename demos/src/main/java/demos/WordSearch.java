package demos;

import edu.wofford.woclo.*;
import java.awt.*;
import java.util.*;

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
        return "WordSearch error: grid dimensions ("
            + width
            + " x "
            + height
            + ") do not match grid length ("
            + grid.length()
            + ")";
      }
      char[][] g = new char[height][width];
      int k = 0;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          g[i][j] = grid.charAt(k);
          k++;
        }
      }
      Deque<Point> potentialStart = new ArrayDeque<Point>();
      for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[i].length; j++) {
          if (g[i][j] == target.charAt(0)) {
            Point p = new Point(i + 1, j + 1);
            potentialStart.addLast(p);
          }
        }
      }
      while (!potentialStart.isEmpty()) {
        Deque<Point> location = new ArrayDeque<Point>();
        location.add(potentialStart.removeFirst());
        location = findWord(target.substring(1), g, location);
        if (location.size() == target.length()) {
          return asString(target, location);
        }
      }
      return target + " not found";
    } catch (HelpException e) {
      return "usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target\n\nFind a target word in a grid.\n\npositional arguments:\n grid             (string)      the grid to search\n target           (string)      the target word\n\nnamed arguments:\n -h, --help       show this help message and exit\n --width WIDTH    (integer)     the grid width (default: 5)\n --height HEIGHT  (integer)     the grid height (default: 5)";
    } catch (WrongTypeException e) {
      String wrongValue = e.getWrongValue();
      return "WordSearch error: the value " + wrongValue + " is not of type integer";
    } catch (TooManyException e) {
      String firstExtra = e.getFirstExtra();
      return "WordSearch error: the value " + firstExtra + " matches no argument";
    } catch (TooFewException e) {
      String next = e.getNextExpectedName();
      return "WordSearch error: the argument " + next + " is required";
    } catch (NoValueException e) {
      String val_expected = e.getNameMissingValue();
      return "WordSearch error: no value for " + val_expected;
    }
  }

  public Deque<Point> findWord(String target, char[][] grid, Deque<Point> locations) {
    if (target.length() == 0) {
      return new ArrayDeque<Point>(locations);
    }
    Point p = locations.getLast();
    int row = (int) p.getX() - 1;
    int col = (int) p.getY() - 1;
    int[] neighbors = {-1, 0, 1, 0, 0, -1, 0, 1};
    for (int i = 0; i < neighbors.length; i += 2) {
      int nrow = row + neighbors[i];
      int ncol = col + neighbors[i + 1];
      Point npoint = new Point(nrow + 1, ncol + 1);
      if (is_legal(nrow, ncol, grid)
          && grid[nrow][ncol] == target.charAt(0)
          && !locations.contains(npoint)) {
        Deque<Point> tmp = new ArrayDeque<Point>(locations);
        tmp.add(npoint);
        Deque<Point> result = findWord(target.substring(1), grid, tmp);
        if (result.size() == target.length() + tmp.size() - 1) {
          return result;
        }
      }
    }
    return new ArrayDeque<Point>();
  }

  public boolean is_legal(int row, int column, char[][] grid) {
    return (0 <= row) && (row < grid.length) && (0 <= column) && (column < grid[0].length);
  }

  public String asString(String target, Deque<Point> locations) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < target.length(); i++) {
      Point p = locations.pop();
      buff.append(target.charAt(i) + ":" + (int) p.getX() + "," + (int) p.getY() + " ");
    }
    String string = buff.toString();
    return string.trim();
  }

  public static void main(String... args) {
    WordSearch w = new WordSearch();
    String p = w.wordsearch(args);
    System.out.println(p);
  }
}
