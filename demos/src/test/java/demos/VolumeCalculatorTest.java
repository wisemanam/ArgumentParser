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
  public void testMissingDefault() {
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
    String[] args = {"2", "--type", "pyramid", "5", "-p", "2", "3"};
    VolumeCalculator v = new VolumeCalculator();
    String result = v.volumeCalculator(xml, args);
    assertEquals("VolumeCalculator error: invalid XML", result);
  }

  @Test
  public void testHelp() {
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
            + "<value>5</value>"
            + "</default>"
            + "<type>integer</type>"
            + "<description>the maximum number of decimal places for the volume</description>"
            + "<name>precision</name>"
            + "<shortname>p</shortname>"
            + "</named>"
            + "</namedArgs>"
            + "</arguments>";
    String[] args = {"-h", "2", "--type", "ellipsoid", "5", "-p", "3", "3"};
    VolumeCalculator v = new VolumeCalculator();
    String result = v.volumeCalculator(xml, args);
    String help =
        "usage: java VolumeCalculator [-h] [-t TYPE] [-p PRECISION] length width height\n\nCalculate the volume.\n\npositional arguments:\n length                               (float)       the length of the volume\n width                                (float)       the width of the volume\n height                               (float)       the height of the volume\n\nnamed arguments:\n -h, --help                           show this help message and exit\n -t TYPE, --type TYPE                 (string)      the type of volume {box, pyramid, ellipsoid} (default: box)\n -p PRECISION, --precision PRECISION  (integer)     the maximum number of decimal places for the volume (default: 5)";
    assertEquals(help, result);
  }
}
