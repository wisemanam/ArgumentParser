package demos;

import edu.wofford.woclo.*;

public class WordSearch {

  public boolean search(args){
    ArgumentParser argParse = new ArgumentParser(args, 4);
    argParse.addPositional("grid", "string");
    argParse.addPositional("target", "string");
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
