package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  void testEquivelent() {
    String arguments = "coccon xyxxyz";
    // String string1 = argParse.getValue(0);
    // String string2 = argParse.getValue(1);
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "equivalent");
  }

  @Test 
  void testNotEquivalent() {
    String arguments = "emily hello";
    // String string1 = argParse.getValue(0);
    // String string2 = argParse.getValue(1);
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }

  @Test
  void stringsDifLengths() {
    String arguments = "audrey savannah";
    // String string1 = argParse.getValue(0);
    // String string2 = argParse.getValue(1);
    EquivalentStrings test = new EquivalentStrings(arguments);
    int[] map1 = test.mapString(test.getString1());
    int[] map2 = test.mapString(test.getString2());
    String answer = test.checkEquivalent(map1, map2);
    assertEquals(answer, "not equivalent");
  }
}
