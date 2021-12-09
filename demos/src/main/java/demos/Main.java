package demos;

import edu.wofford.woclo.*;
import java.util.Arrays;

public class Main {
  public static void main(String... args) {
    String[] demoNames = {
      "EquivalentStrings",
      "OverlappingRectangles",
      "WordSearch",
      "MaximalLayers",
      "TilingAssistant",
      "HeatedField",
      "VolumeCalculator",
      "XMLReadWriteDemo",
      "DistanceCalculator"
    };

    boolean valid = false;
    if (args.length > 0) {
      valid = true;
      String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
      String demoName = args[0];
      if ("EquivalentStrings".equalsIgnoreCase(demoName)) {
        EquivalentStrings.main(mainArgs);
      } else if ("OverlappingRectangles".equalsIgnoreCase(demoName)) {
        OverlappingRectangles.main(mainArgs);
      } else if ("WordSearch".equalsIgnoreCase(demoName)) {
        WordSearch.main(mainArgs);
      } else if ("MaximalLayers".equalsIgnoreCase(demoName)) {
        MaximalLayers.main(mainArgs);
      } else if ("TilingAssistant".equalsIgnoreCase(demoName)) {
        TilingAssistant.main(mainArgs);
      } else if ("HeatedField".equalsIgnoreCase(demoName)) {
        HeatedField.main(mainArgs);
      } else if ("VolumeCalculator".equalsIgnoreCase(demoName)) {
        VolumeCalculator.main(mainArgs);
      } else if ("XMLReadWriteDemo".equalsIgnoreCase(demoName)) {
        XMLReadWriteDemo.main(mainArgs);
      } else if ("DistanceCalculator".equalsIgnoreCase(demoName)) {
        DistanceCalculator.main(mainArgs);
      } else {
        valid = false;
      }
    }

    if (!valid) {
      System.out.println("Demo name must be provided.");
      System.out.println("Options:");
      for (String dn : demoNames) {
        System.out.println("  " + dn);
      }
    }

    String testxml =
        "<?xml version=\"1.0\"?>"
            + "<arguments>"
            + "<positionalArgs>"
            + "<positional>"
            + "<type>float</type>"
            + "<description>the length of the volume</description>"
            + "<name>length</name>"
            + "</positional>"
            + "<positional>"
            + "<name>width</name>"
            + "<type>float</type>"
            + "<description>the width of the volume</description>"
            + "</positional>"
            + "<positional>"
            + "<description>the height of the volume</description>"
            + "<name>height</name>"
            + "<type>float</type>"
            + "</positional>"
            + "</positionalArgs>"
            + "<namedArgs>"
            + "<named>"
            + "<description>the type of volume</description>"
            + "<shortname>t</shortname>"
            + "<type>string</type>"
            + "<name>type</name>"
            + "<restrictions>"
            + "<restriction>box</restriction>"
            + "<restriction>pyramid</restriction>"
            + "<restriction>ellipsoid</restriction>"
            + "</restrictions>"
            + "</named>"
            + "<named>"
            + "<default>"
            + "<value>4</value>"
            + "</default>"
            + "<type>integer</type>"
            + "<description>the maximum number of decimal places for the volume</description>"
            + "<name>precision</name>"
            + "<shortname>p</shortname>"
            + "</named>"
            + "</namedArgs>"
            + "</arguments>";

    ArgumentParser a = new XMLparser().parseXML(testxml);
  }
}
