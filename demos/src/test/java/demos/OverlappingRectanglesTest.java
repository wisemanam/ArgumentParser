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
    assertEquals("OverlappingRectangles error: the argument string8 is required", s);
  }

  @Test
  public void testTooMany() {
    String[] args = {"-6", "2", "6", "6", "-5", "-5", "-2", "3", "8"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals("OverlappingRectangles error: the value 8 matches no argument", s);
  }
}
