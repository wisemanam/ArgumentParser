package demos;

import edu.wofford.woclo.*;

public class TilingAssistant {
  public String tilingAssistant(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addPositional("length", "float", "the length of the room");
      parser.addPositional("width", "float", "the width of the room");
      parser.addNonPositional("tilesize", "s", "float", "the size of the square tile", "6.0");
      parser.addNonPositional("groutgap", "g", "float", "the width of the grout gap", "0.5");
      parser.addNonPositional("metric", "m", "boolean", "use centimeters instead of inches", "false");
      parser.addNonPositional("fullonly", "f", "boolean", "show only the full tiles required", "false");
      parser.parse(arguments);
      float length = parser.getValue("length");
      float width = parser.getValue("width");
      float tileSize = parser.getValue("tilesize");
      float groutGap = parser.getValue("groutgap");
      boolean metric = parser.getValue("metric");
      boolean fullOnly = parser.getValue("fullonly");
    } catch (HelpException e) {
      return "help";
    }
  }

  public int calculateNumFullTiles(float roomLength, float roomWidth, float tileSize, float grout) {
    int numFullTilesDown = 0;
    while(roomLength - (tileSize + grout) >= tileSize) {
      numFullTilesDown += 1;
      roomLength -= (tileSize + grout);
    }
    if (roomLength == tileSize) {
      numFullTilesDown += 1;
    }
    int numFullTilesAcross = 0;
    while(roomWidth - (tileSize + grout) >= tileSize) {
      numFullTilesAcross += 1;
      roomWidth -= (tileSize + grout);
    }
    if (roomWidth == tileSize) {
      numFullTilesAcross += 1;
    }
    return numFullTilesAcross*numFullTilesDown;
  }

  public static void main(String... args) {
    
  }
}
