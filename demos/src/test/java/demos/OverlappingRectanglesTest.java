package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class OverlappingRectanglesTest {

  @Test
  public void Test1() {
    String[] args = {"-3", "0", "3", "4", "0", "-1", "9", "2"};
    OverlappingRectangles o = new OverlappingRectangles();
    String s = o.overlappingRectangles(args);
    assertEquals(s, "6 45");
  }
}
