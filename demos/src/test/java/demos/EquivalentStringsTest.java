package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class EquivalentStringsTest {

  @Test
  public void testEquivalent() {
    String[] arguments = {"cocoon", "xyxyyz"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    String answer = test.checkEquivalent(test.getString1(), test.getString2());
    assertEquals(answer, "equivalent");
  }

  @Test
  public void testNotEquivalent() {
    String[] arguments = {"emily", "hello"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    String answer = test.checkEquivalent(test.getString1(), test.getString2());
    assertEquals(answer, "not equivalent");
  }

  @Test
  public void stringsDifLengths() {
    String[] arguments = {"audrey", "savannah"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    String answer = test.checkEquivalent(test.getString1(), test.getString2());
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

  @Test
  public void helpTest() {
    String[] arguments = {"bob", "dad", "--help"};
    EquivalentStrings test = new EquivalentStrings(arguments);
    assertEquals(
        test.getErrorMessage(),
        "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit");
    String[] arguments2 = {"bob", "dad", "-h"};
    EquivalentStrings test2 = new EquivalentStrings(arguments2);
    assertEquals(
        test2.getErrorMessage(),
        "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit");
  }
}
