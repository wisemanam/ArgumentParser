package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class EquivalentStrings {
  private String string1;
  private String string2;
  private String error_message;

  enum Error {
    HELP,
    TOO_MANY,
    TOO_FEW,
    NONE
  }

  Error error;

  public EquivalentStrings(String[] strings) {
    ArgumentParser argParse = new ArgumentParser();
    try {
      argParse.addPositional("string1", "string", "the first string");
      argParse.addPositional("string2", "string", "the second string");
      argParse.parse(strings);
      string1 = argParse.getValue("string1");
      string2 = argParse.getValue("string2");
      error = Error.NONE;
      error_message = "";

    } catch (HelpException e1) {
      error = Error.HELP;
      error_message = "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit";
          // argParse.constructHelp("EquivalentStrings", "Determine if two strings are equivalent.");
      // "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are
      // equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2
      //     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help
      // message and exit";

    } catch (TooFewException e2) {
      error = Error.TOO_FEW;
      String value = e2.getNextExpectedName();
      error_message = "EquivalentStrings error: the argument " + value + " is required";

    } catch (TooManyException e3) {
      error = Error.TOO_MANY;
      String firstExtra = e3.getFirstExtra();
      error_message = "EquivalentStrings error: the value " + firstExtra + " matches no argument";
    }
  }

  public String getString1() {
    return string1;
  }

  public String getString2() {
    return string2;
  }

  public String getErrorMessage() {
    return error_message;
  }

  public int[] mapString(String str) {
    String[] str_hold = new String[str.length()];
    String[] str_arr = str.split("", 0);
    int hold = 0;
    int[] str_map = new int[str.length()];
    for (int i = 0; i < str.length(); i++) {
      if (Arrays.asList(str_hold).contains(str_arr[i]) == false) {
        str_hold[hold] = str_arr[i];
        str_map[i] = hold;
        hold++;
      } else {
        str_map[i] = Arrays.asList(str_hold).indexOf(str_arr[i]);
      }
    }
    return str_map;
  }

  public String checkEquivalent(String string1, String string2) {

    int[] map1 = mapString(string1);
    int[] map2 = mapString(string2);
    boolean equivalent = true;
    if (map1.length == map2.length) {
      for (int i = 0; i < map1.length; i++) {
        if (map1[i] != map2[i]) {
          equivalent = false;
        }
      }
    } else {
      equivalent = false;
    }
    if (equivalent) {
      return "equivalent";
    } else {
      return "not equivalent";
    }
  }

  public static void main(String... args) {
    EquivalentStrings equivStrings = new EquivalentStrings(args);
    if (equivStrings.error == Error.NONE) {
      String equivalence =
          equivStrings.checkEquivalent(equivStrings.getString1(), equivStrings.getString2());
      System.out.println(equivalence);
    } else {
      System.out.println(equivStrings.getErrorMessage());
    }
  }
}