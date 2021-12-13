package demos;

import edu.wofford.woclo.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class VolumeCalculator {
  public String volumeCalculator(String xml, String[] arguments) {
    ArgumentParser parser = null;
    try {
      parser = XMLparser.parseXML(xml);
    } catch (MissingFromXMLException e) {
      return "VolumeCalculator error: invalid XML";
    }
    try {
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
      } else {
        volume = (float) Math.PI * length * width * height * 4 / 3;
      }
      Double d = volume.doubleValue();
      BigDecimal bd = new BigDecimal(d).setScale(precision, RoundingMode.HALF_UP);
      double val2 = bd.doubleValue();
      return String.valueOf(val2);
    } catch (HelpException e) {
      return e.getHelpMessage(parser, "VolumeCalculator", "Calculate the volume.");
    } catch (TooManyException e) {
      return "VolumeCalculator error: the value " + e.getFirstExtra() + " matches no argument";
    } catch (TooFewException e) {
      return "VolumeCalculator error: the argument " + e.getNextExpectedName() + " is required";
    } catch (MutualExclusionException e) {
      return "VolumeCalculator error: mutual exclusion ["
          + e.getMutuallyExcList().get(0)
          + ", "
          + e.getMutuallyExcList().get(1)
          + "]";
    } catch (RequiredArgumentMissingException e) {
      return "VolumeCalculator error: required named argument "
          + e.getMissingRequired()
          + " not given";
    }
  }

  public static void main(String... args) {
    VolumeCalculator v = new VolumeCalculator();
    String s = v.volumeCalculator(args[0], Arrays.copyOfRange(args, 1, args.length));
    System.out.println(s);
  }
}
