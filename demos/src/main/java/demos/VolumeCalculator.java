package demos;

import edu.wofford.woclo.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class VolumeCalculator {
  public String volumeCalculator(String xml, String[] arguments) {
    try {
      ArgumentParser parser = XMLparser.parseXML(xml);
      parser.parse(arguments);
      float length = parser.getValue("length");
      float width = parser.getValue("width");
      float height = parser.getValue("height");
      String type = parser.getValue("type");
      int precision = parser.getValue("precision");
      Float volume = (float) 0.0;
      if (type.equals("box")) {
        volume = length * width * height;
      } else if (type.equals("pyramid")) {
        volume = length * width * height / 3;
      } else if (type.equals("ellipsoid")) {
        volume = (float) Math.PI * length * width * height * 4 / 3;
      }
      StringBuffer buff = new StringBuffer("#");
      if (precision > 0) {
        buff.append(".");
        for (int i = 0; i < precision; i++) {
          buff.append("#");
        }
      }
      String decimalFormat = buff.toString().trim();
      DecimalFormat df = new DecimalFormat(decimalFormat);
      df.setRoundingMode(RoundingMode.CEILING);
      Double d = volume.doubleValue();
      return df.format(d);
    } catch (HelpException e) {
      return "usage: java VolumeCalculator [-h] [-t TYPE] [-p PRECISION] length width height\n\nCalculate the volume.\n\npositional arguments:\n length                               (float)       the length of the volume\n width                                (float)       the width of the volume\n height                               (float)       the height of the volume\n\nnamed arguments:\n -h, --help                           show this help message and exit\n -t TYPE, --type TYPE                 (string)      the type of volume {box, pyramid, ellipsoid} (default: box)\n -p PRECISION, --precision PRECISION  (integer)     the maximum number of decimal places for the volume (default: 4)";
    } catch (TooManyException e) {
      return "VolumeCalculator error: the value " + e.getFirstExtra() + " matches no argument";
    } catch (TooFewException e) {
      return "TilingAssistant error: the argument " + e.getNextExpectedName() + " is required";
    }
  }

  public static void main(String... args) {}
}
