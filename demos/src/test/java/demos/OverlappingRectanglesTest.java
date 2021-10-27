package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class OverlappingRectanglesTest {

  @Test
  public void testOverlappingRectangles1() {
    String[] args = {"-3", "0", "3", "4", "0", "-1", "9", "2"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals(s, "6 45");
  }

  @Test
  public void testOverlappingRectangles2() {
    String[] args = {"-6", "2", "6", "6", "-5", "-5", "-2", "3"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals(s, "3 69");
  }
}
