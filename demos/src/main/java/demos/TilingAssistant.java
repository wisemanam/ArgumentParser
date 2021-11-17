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
      return "help";
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
    int numPartialTilesLength = 0;
    float lengthofPartialTiles = 0;
    if (roomLength % (tileSize + grout) < 0.01) {
      numFullTilesLength += (int) (roomLength / (tileSize + grout));
      roomLength = 0;
    } else if (roomLength % (tileSize + grout) == grout) {
      numFullTilesLength = (int) (roomLength / (tileSize + grout));
      roomLength += tileSize;
      roomLength -= (roomLength / (tileSize + grout));
      numPartialTilesLength += 2;
      roomLength -= grout;
      lengthofPartialTiles = tileSize / 2;
      roomLength -= tileSize;
    } else if (roomLength % (tileSize + grout) > grout) {
      numFullTilesLength = (int) (roomLength / (tileSize + grout));
      roomLength += tileSize;
      roomLength -= (roomLength / (tileSize + grout));
      roomLength -= grout;
      numPartialTilesLength += 2;
      lengthofPartialTiles = roomLength / 2;
      roomLength -= 2 * lengthofPartialTiles;
    }
    int numFullTilesWidth = 1;
    roomWidth -= tileSize;
    int numPartialTilesWidth = 0;
    float widthofPartialTiles = 0;
    if (roomWidth % (tileSize + grout) < 0.01) {
      numFullTilesWidth += (int) (roomWidth / (tileSize + grout));
      roomWidth = 0;
    } else if (roomWidth % (tileSize + grout) == grout) {
      numFullTilesWidth = (int) (roomWidth / (tileSize + grout));
      roomWidth += tileSize;
      roomWidth -= (roomWidth / (tileSize + grout));
      numPartialTilesWidth += 2;
      roomWidth -= grout;
      widthofPartialTiles = tileSize / 2;
      roomWidth -= tileSize;
    } else if (roomWidth % (tileSize + grout) > grout) {
      numFullTilesWidth = (int) (roomWidth / (tileSize + grout));
      roomWidth += tileSize;
      roomWidth -= (roomWidth / (tileSize + grout));
      roomWidth -= grout;
      numPartialTilesWidth += 2;
      widthofPartialTiles = roomWidth / 2;
      roomWidth -= 2 * widthofPartialTiles;
    }
    numPartialTilesLength = numPartialTilesLength * numFullTilesLength;
    numPartialTilesWidth = numPartialTilesWidth * numPartialTilesWidth;
    if (numPartialTilesLength > 0 && numPartialTilesWidth > 0) {
      numPartialTilesLength += 2;
      numPartialTilesWidth += 2;
    }
    String units = " in";
    if (metric) {
      units = " cm";
    }
    int numFullTiles = numFullTilesLength * numFullTilesWidth;
    String string = "";
    string += numFullTiles + ":(" + tileSize + " x " + tileSize + units + ") ";
    if (!fullOnly) {
      if (numPartialTilesLength > 0) {
        string +=
            numPartialTilesLength + ":(" + lengthofPartialTiles + " x " + tileSize + units + ") ";
      }
      if (numPartialTilesWidth > 0) {
        string +=
            numPartialTilesWidth + ":(" + tileSize + " x " + widthofPartialTiles + units + ") ";
      }
      if (numPartialTilesLength > 0 && numPartialTilesWidth > 0) {
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
