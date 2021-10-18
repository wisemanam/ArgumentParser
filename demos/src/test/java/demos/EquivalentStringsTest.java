package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class EquivalentStringsTest {

  @Test
  public void testEquivalent() {
    String[] arguments = {"coccon", "xyxxyz"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "equivalent");
  }

  @Test
  public void testNotEquivalent() {
    String[] arguments = {"emily", "hello"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }

  @Test
  public void stringsDifLengths() {
    String[] arguments = {"audrey", "savannah"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }

  @Test
  public void noArgsGiven() {
    String[] arguments = {};
    EquivalentStrings test = new EquivalentStrings(arguments);
    assertEquals(
        test.getErrorMessage(), "EquivalentStrings error: the argument string1 is required");
  }

  @Test
  public void oneArgGiven() {
    String[] arguments = {"bob"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    assertEquals(
        test.getErrorMessage(), "EquivalentStrings error: the argument string2 is required");
  }

  @Test
  public void tooManyArgsGiven() {
    String[] arguments = {"bob", "dad", "mom"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    assertEquals(
        test.getErrorMessage(), "EquivalentStrings error: the value mom matches no argument");
  }
}
