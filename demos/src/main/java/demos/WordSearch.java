package demos;

import edu.wofford.woclo.*;

public class WordSearch {
  public boolean is_legal(int row, int column, String grid, int width, int height) {
    return (0 <= row) && (row < height) && (0 <= column) && (column < width);
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
  // need expected args (grid, word, [optional]: width, height)
  // String[] expectedArgs = {grid, word, width, height};
  // if arguments doesnt contain --width or --height
  // do setdefault for whatever it is missing

  public static void main(String... args) {
    WordSearch w = new WordSearch();
    boolean b = w.search(args);
    System.out.println(b);
  }
}
