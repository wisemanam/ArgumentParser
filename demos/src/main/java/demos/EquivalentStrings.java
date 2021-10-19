package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class EquivalentStrings {
  String string1;
  String string2;
  String error_message;

  enum Error {
    HELP,
    TOO_MANY,
    TOO_FEW,
    NONE
  }

  Error error;

  public EquivalentStrings(String[] strings) {
    try {
      ArgumentParser argParse = new ArgumentParser(2, strings);
      string1 = argParse.getValue(0);
      string2 = argParse.getValue(1);
      error = Error.NONE;
      error_message = "";

    } catch (HelpException e1) {
      error = Error.HELP;
      error_message =
          "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit";

    } catch (TooFewException e2) {
      error = Error.TOO_FEW;
      String nextExpected = e2.getNextExpected();
      error_message = "EquivalentStrings error: the argument " + nextExpected + " is required";

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

  public String checkEquivalent(int[] map1, int[] map2) {
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
      String string1 = equivStrings.getString1();
      String string2 = equivStrings.getString2();
      int[] map1 = equivStrings.mapString(string1);
      int[] map2 = equivStrings.mapString(string2);
      String equivalence = equivStrings.checkEquivalent(map1, map2);
      System.out.println(equivalence);
    } else {
      System.out.println(equivStrings.getErrorMessage());
    }
  }
}
