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
    boolean hasPartialLength = false;
    boolean hasPartialWidth = false;
    double lengthPartialTiles = 0;
    double widthPartialTiles = 0;
    int numFullLength = (int) (Math.floor((roomLength - tileSize) / (tileSize + grout)) + 1);
    int numFullWidth = (int) (Math.floor((roomWidth - tileSize) / (tileSize + grout)) + 1);
    double extraLength = roomLength - (tileSize * numFullLength + grout * (numFullLength - 1));
    double extraWidth = roomWidth - (tileSize * numFullWidth + grout * (numFullWidth - 1));
    if (extraLength > 0.0000001) {
      if ((Math.abs(extraLength - 2 * grout) < 0.000001) || extraLength < 2 * grout) {
        hasPartialLength = true;
        numFullLength--;
        extraLength += tileSize;
        extraLength -= grout;
        lengthPartialTiles = extraLength / 2;
      } else if (extraLength > (2 * grout)) {
        hasPartialLength = true;
        lengthPartialTiles = (extraLength - (2 * grout)) / 2;
      }
    }
    if (extraWidth > 0.0000001) {
      if (Math.abs(extraWidth - (2 * grout)) < 0.0000001 || extraWidth < 2 * grout) {
        hasPartialWidth = true;
        numFullWidth--;
        extraWidth += tileSize;
        extraWidth -= grout;
        widthPartialTiles = extraWidth / 2;
      } else if (extraWidth > (2 * grout)) {
        hasPartialWidth = true;
        widthPartialTiles = (extraWidth - (2 * grout)) / 2;
      }
    }
    String units = " in";
    if (metric) {
      units = " cm";
    }
    int numFullTiles = numFullLength * numFullWidth;
    String string = "";
    string += numFullTiles + ":(" + tileSize + " x " + tileSize + units + ") ";
    if (!fullOnly) {
      if (hasPartialLength) {
        string += (2 * numFullWidth) + ":(" + lengthPartialTiles + " x " + tileSize + units + ") ";
      }
      if (hasPartialWidth) {
        string += (2 * numFullLength) + ":(" + tileSize + " x " + widthPartialTiles + units + ") ";
      }
      if (hasPartialLength && hasPartialWidth) {
        string += "4:(" + lengthPartialTiles + " x " + widthPartialTiles + units + ")";
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
