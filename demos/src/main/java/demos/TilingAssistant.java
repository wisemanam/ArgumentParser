package demos;

import edu.wofford.woclo.*;

public class TilingAssistant {
  public String tileAssistant(String[] arguments) {
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
       return calculateTiles(length, width, tileSize, groutGap, metric, fullOnly);
    } catch (HelpException e) {
      return "help";
    }
  }

  public String calculateTiles(float roomLength, float roomWidth, float tileSize, float grout, boolean metric, boolean fullOnly) {
    int numFullTilesLength = 0;
    int numPartialTilesLength = 0;
    float lengthofPartialTiles = 0;
    while(roomLength - (tileSize + grout) >= tileSize) {
      numFullTilesLength++;
      roomLength -= (tileSize + grout);
    }
    if (roomLength == tileSize) {
      numFullTilesLength++;
      roomLength -= tileSize;
    } else if (roomLength == grout) {
      numFullTilesLength--;
      roomLength += tileSize;
      numPartialTilesLength += 2;
      roomLength -= grout;
      lengthofPartialTiles = tileSize / 2;
      roomLength -= tileSize;
    } else if (roomLength > grout) {
      roomLength -= grout;
      numPartialTilesLength += 2;
      lengthofPartialTiles = roomLength / 2;
      roomLength -= 2 * lengthofPartialTiles;
    }
    int numFullTilesWidth = 0;
    int numPartialTilesWidth = 0;
    float widthofPartialTiles = 0;
    while(roomWidth - (tileSize + grout) >= tileSize) {
      numFullTilesWidth++;
      roomWidth -= (tileSize + grout);
    }
    if (roomWidth == tileSize) {
      numFullTilesWidth++;
      roomWidth -= tileSize;
    } else if (roomWidth == grout) {
      numFullTilesWidth--;
      roomWidth += tileSize;
      numPartialTilesWidth += 2;
      roomWidth -= grout;
      widthofPartialTiles = tileSize / 2;
      roomWidth -= tileSize;
    } else if (roomWidth > grout) {
      roomWidth -= grout;
      numPartialTilesWidth += 2;
      widthofPartialTiles = roomLength / 2;
      roomWidth -= 2 * widthofPartialTiles;
    }
    numPartialTilesLength = numPartialTilesLength * numFullTilesLength;
    numPartialTilesWidth = numPartialTilesWidth * numPartialTilesWidth;
    String units = " in";
    if (metric) {
      units = " cm";
    }
    int numFullTiles = numFullTilesLength * numFullTilesWidth;
    String string = "";
    string += numFullTiles + ":(" + tileSize + " x " + tileSize + units + ") ";
    if (!fullOnly) {
      if (numPartialTilesLength > 0) {
        string += numPartialTilesLength + ":(" + lengthofPartialTiles + " x " + tileSize + units + ") ";
      }
      if (numPartialTilesWidth > 0) {
        string += numPartialTilesWidth + ":(" + tileSize + " x " + widthofPartialTiles + units + ") ";
      }
      if (numPartialTilesLength > 0 && numPartialTilesWidth > 0) {
        string += "4:(" + lengthofPartialTiles + " x " + widthofPartialTiles + units + ")";
      }
    }
    return string.trim();
  }

  public static void main(String... args) {
    
  }
}
