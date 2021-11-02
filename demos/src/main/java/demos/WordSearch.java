package demos;

import edu.wofford.woclo.*;

public class WordSearch {
  public boolean is_legal(int row, int column, String grid, int width, int height) {
    return (0 <= row) && (row < height) && (0 <= column) && (column < width);
  }
  
  public boolean search(String[] args){
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("grid", type, "the grid to search");
    argParse.addPositional("target", type, "the target word");
    argParse.addNonPositional("width", type, "5", "the grid width");
    argParse.addNonPositional("height", type, "5", "the grid height");

    argParse.parse(args);
    
    String grid = getValue("grid");
    String target = getValue("target");
    int width = getValue("width");
    int height = getValue("height");

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
