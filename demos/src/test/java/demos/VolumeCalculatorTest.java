package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VolumeCalculatorTest {
  @Test
  public void testWithGivenType() {
    String xml =
        "<?xml version=\"1.0\"?>"
            + "<arguments>"
            + "<positionalArgs>"
            + "<positional>"
            + "<type>float</type>"
            + "<description>the length of the volume</description>"
            + "<name>length</name>"
            + "</positional>"
            + "<positional>"
            + "<type>float</type>"
            + "<name>width</name>"
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
            + "<default>"
            + "<value>box</value>"
            + "</default>"
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
    String[] args = {"2", "--type", "ellipsoid", "5", "-p", "3", "3"};
    VolumeCalculator v = new VolumeCalculator();
    String result = v.volumeCalculator(xml, args);
    assertEquals("125.664", result);
  }
  // @Test
  // public void testPositionals() {
  //   String xml =
  //       "<?xml version=\"1.0\"?>"
  //           + "<arguments>"
  //           + "<positionalArgs>"
  //           + "<positional>"
  //           + "<type>float</type>"
  //           + "<description>the length of the volume</description>"
  //           + "<name>length</name>"
  //           + "</positional>"
  //           + "<positional>"
  //           + "<type>float</type>"
  //           + "<name>width</name>"
  //           + "<description>the width of the volume</description>"
  //           + "</positional>"
  //           + "<positional>"
  //           + "<description>the height of the volume</description>"
  //           + "<name>height</name>"
  //           + "<type>float</type>"
  //           + "</positional>"
  //           + "</positionalArgs>"
  //           + "</arguments>";
  //   String[] args = {};
  // }
}
