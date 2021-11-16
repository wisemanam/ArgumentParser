package demos;

import edu.wofford.woclo.*;

public class HeatedField {

  public String heatedField(String[] arguments) {
    ArgumentParser argParse = new ArgumentParser();
    String[] accepted = {"1", "2", "3", "4", "5", "6", "7", "8"};
    argParse.addPositional("north", "float", "the temperature of the north edge");
    argParse.addPositional("south", "float", "the temperature of the south edge");
    argParse.addPositional("east", "float", "the temperature of the east edge");
    argParse.addPositional("west", "float", "the temperature of the west edge");
    argParse.addPositional("x", "integer", "the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}", accepted);
    argParse.addPositional("y", "integer", "the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}", accepted);
    argParse.addNonPositional("temperature", "t", "float", "the initial temperature of internal cells (default: 32.0)", "32.0");
    argParse.addNonPositional("minutes", "m", "integer", "the number of minutes to apply heating (default: 10)", "10");
    try {
      argParse.parse(arguments);
      float north = argParse.getValue("north");
      float south = argParse.getValue("south");
      float east = argParse.getValue("east");
      float west = argParse.getValue("west");
      int x = argParse.getValue("x");
      int y = argParse.getValue("y");
      float temperature = argParse.getValue("temperature");
      int minutes = argParse.getValue("minutes");
      if (minutes < 0) {
        return "HeatedField error: minutes must be positive";
      }
      float[][] field = new float[10][10];
      for (int i = 1; i < 9; i++) {
        for (int j = 1; j < 9; j++) {
          field[0][j] = north;
          field[9][j] = south;
          field[i][0] = east;
          field[i][9] = west;
        }
      }
    } catch {

    }
  }

  public static void main(String... args) {}
}
