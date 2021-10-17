package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  void testEquivelent() {
    String arguments = "coccon xyxxyz";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "equivalent");
  }

  @Test
  void testNotEquivalent() {
    String arguments = "emily hello";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }

  @Test
  void stringsDifLengths() {
    String arguments = "audrey savannah";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }

  @Test
  void noArgsGiven() {
    String arguments = "";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "EquivalentStrings error: the argument string1 is required");
  }

  @Test
  void oneArgGiven() {
    String arguments = "bob";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "EquivalentStrings error: the argument string2 is required");
  }

  @Test
  void tooManyArgsGiven() {
    String arguments = "bob dad mom";
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "EquivalentStrings error: the value mom matches no argument");
  }
}
