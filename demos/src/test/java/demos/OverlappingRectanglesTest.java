package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class OverlappingRectanglesTest {

  @Test
  public void testOverlappingRectangles1() {
    String[] args = {"-3", "0", "3", "4", "0", "-1", "9", "2"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("6 45", s);
  }

  @Test
  public void testOverlappingRectangles2() {
    String[] args = {"-6", "2", "6", "6", "-5", "-5", "-2", "3"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("3 69", s);
  }

  @Test
  public void testTooFew() {
    String[] args = {"-6", "2", "6", "6", "-5", "-5", "-2"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("OverlappingRectangles error: the argument y4 is required", s);
  }

  @Test
  public void testTooMany() {
    String[] args = {"-6", "2", "6", "6", "-5", "-5", "-2", "3", "8"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("OverlappingRectangles error: the value 8 matches no argument", s);
  }

  @Test
  public void testWrongType() {
    String[] args = {"8", "6", "hello", "5", "4", "3", "2", "10"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("OverlappingRectangles error: the value hello is not of type integer", s);
  }

  @Test
  public void testHelp() {
    String[] args = {"8", "6", "7", "5", "3", "0", "9", "-h"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals(
        "usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4\n\nDetermine the overlap and total area of two rectangles.\n\npositional arguments:\n x1          (integer)     lower-left x for rectangle 1\n y1          (integer)     lower-left y for rectangle 1\n x2          (integer)     upper-right x for rectangle 1\n y2          (integer)     upper-right y for rectangle 1\n x3          (integer)     lower-left x for rectangle 2\n y3          (integer)     lower-left y for rectangle 2\n x4          (integer)     upper-right x for rectangle 2\n y4          (integer)     upper-right y for rectangle 2\n\nnamed arguments:\n -h, --help  show this help message and exit",
        s);
  }
}
