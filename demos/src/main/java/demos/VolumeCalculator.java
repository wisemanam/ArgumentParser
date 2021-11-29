package demos;

import java.io.FileReader;

import edu.wofford.woclo.*;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class VolumeCalculator {
  public String volumeCalculator(String[] arguments, FileReader xml) {
    ArgumentParser parser = new ArgumentParser();
    String[] acceptedType = {"box", "pyramid", "ellipsoid"};
    try {
      parser.addPositional("length", "float", "the length of the volume");
      parser.addPositional("width", "float", "the width of the volume");
      parser.addPositional("height", "float", "the height of the volume");
      parser.addNonPositional("type", "t", "string", "the type of volume {box, pyramid, ellipsoid}", "box", acceptedType);
      parser.addNonPositional("precision", "p", "integer", "the maximum number of decimal places for the volume", "4");
      parser.parse(arguments);
      float length = parser.getValue("length");
      float width = parser.getValue("width");
      float height = parser.getValue("height");
      String type = parser.getValue("type");
      int precision = parser.getValue("precision");
      Float volume;
      if (type.equals("box")) {
        volume = length * width * height;
      } else if (type.equals("pyramid")) {
        volume = length * width * height / 3;
      } else if (type.equals("ellipsoid")) {
        volume = (float) Math.PI * length * width * height * 4 / 3;
      }
      String decimalFormat = "#";
      if (precision > 0) {
        decimalFormat += ".";
        for (int i = 0; i < precision; i++) {
          decimalFormat += "#";
        }
      }

      int variable = 8;
      DecimalFormat df = new DecimalFormat(decimalFormat);
      df.setRoundingMode(RoundingMode.CEILING);
      Double d = volume.doubleValue();
      return df.format(d);
  } catch {

  }
}
  public static void main(String... args) {}
}
