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
      parser.addNonPositional(
          "metric", "m", "boolean", "use centimeters instead of inches", "false");
      parser.addNonPositional(
          "fullonly", "f", "boolean", "show only the full tiles required", "false");
      parser.parse(arguments);
      float length = parser.getValue("length");
      float width = parser.getValue("width");
      float tileSize = parser.getValue("tilesize");
      float groutGap = parser.getValue("groutgap");
      boolean metric = parser.getValue("metric");
      boolean fullOnly = parser.getValue("fullonly");
      if (length <= 0) {
        return "TilingAssistant error: length must be positive";
      }
      if (width <= 0) {
        return "TilingAssistant error: width must be positive";
      }
      if (tileSize <= 0) {
        return "TilingAssistant error: tilesize must be positive";
      }
      if (groutGap <= 0) {
        return "TilingAssistant error: groutgap must be positive";
      }
      return calculateTiles(length, width, tileSize, groutGap, metric, fullOnly);
    } catch (HelpException e) {
      return "usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width\n\nCalculate the tiles required to tile a room. All units are inches.\n\npositional arguments:\n length                            (float)       the length of the room\n width                             (float)       the width of the room\n\nnamed arguments:\n -h, --help                        show this help message and exit\n -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)\n -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)\n -m, --metric                      use centimeters instead of inches\n -f, --fullonly                    show only the full tiles required";
    } catch (TooFewException e) {
      return "TilingAssistant error: the argument " + e.getNextExpectedName() + " is required";
    } catch (TooManyException e) {
      return "TilingAssistant error: the value " + e.getFirstExtra() + " matches no argument";
    }
  }

  public String calculateTiles(
      float roomLength,
      float roomWidth,
      float tileSize,
      float grout,
      boolean metric,
      boolean fullOnly) {
    int numFullTilesLength = 1;
    roomLength -= tileSize;
    boolean partialTilesLength = false;
    float lengthofPartialTiles = 0;
    if (roomLength % (tileSize + grout) < 0.01) {
      numFullTilesLength += (int) (roomLength / (tileSize + grout));
      roomLength = 0;
    } else if (roomLength % (tileSize + grout) == grout) {
      numFullTilesLength = (int) (roomLength / (tileSize + grout));
      roomLength += tileSize;
      roomLength -= (roomLength / (tileSize + grout));
      roomLength -= grout;
      lengthofPartialTiles = tileSize / 2;
      roomLength -= tileSize;
      partialTilesLength = true;
    } else if (roomLength % (tileSize + grout) > grout) {
      numFullTilesLength = (int) (roomLength / (tileSize + grout));
      roomLength += tileSize;
      roomLength -= (roomLength / (tileSize + grout));
      roomLength -= grout;
      lengthofPartialTiles = roomLength / 2;
      roomLength -= 2 * lengthofPartialTiles;
      partialTilesLength = true;
    }
    int numFullTilesWidth = 1;
    roomWidth -= tileSize;
    boolean partialTilesWidth = false;
    float widthofPartialTiles = 0;
    if (roomWidth % (tileSize + grout) < 0.01) {
      numFullTilesWidth += (int) (roomWidth / (tileSize + grout));
      roomWidth = 0;
    } else if (roomWidth % (tileSize + grout) == grout) {
      numFullTilesWidth = (int) (roomWidth / (tileSize + grout));
      roomWidth += tileSize;
      roomWidth -= (roomWidth / (tileSize + grout));
      roomWidth -= grout;
      widthofPartialTiles = tileSize / 2;
      roomWidth -= tileSize;
      partialTilesWidth = true;
    } else if (roomWidth % (tileSize + grout) > grout) {
      numFullTilesWidth = (int) (roomWidth / (tileSize + grout));
      roomWidth += tileSize;
      roomWidth -= (roomWidth / (tileSize + grout));
      roomWidth -= grout;
      widthofPartialTiles = roomWidth / 2;
      roomWidth -= 2 * widthofPartialTiles;
      partialTilesWidth = true;
    }
    
    String units = " in";
    if (metric) {
      units = " cm";
    }
    int numFullTiles = numFullTilesLength * numFullTilesWidth;
    String string = "";
    string += numFullTiles + ":(" + tileSize + " x " + tileSize + units + ") ";
    if (!fullOnly) {
      if (partialTilesLength) {
        string +=
            (2 * numFullTilesLength)
                + ":("
                + lengthofPartialTiles
                + " x "
                + tileSize
                + units
                + ") ";
      }
      if (partialTilesWidth) {
        string +=
            (2 * numFullTilesWidth) + ":(" + tileSize + " x " + widthofPartialTiles + units + ") ";
      }
      if (partialTilesLength && partialTilesWidth) {
        string += "4:(" + lengthofPartialTiles + " x " + widthofPartialTiles + units + ")";
      }
    }
    return string.trim();
  }

  public static void main(String... args) {
    TilingAssistant t = new TilingAssistant();
    String s = t.tileAssistant(args);
    System.out.println(s);
  }
}
