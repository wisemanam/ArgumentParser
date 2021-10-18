package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class EquivalentStrings {
  String string1;
  String string2;
  String error_message;
  boolean errors;
  int num;

  public EquivalentStrings(String[] strings) {
    ArgumentParser argParse = new ArgumentParser(2, strings);
    errors = false;
    error_message = "";
    try {
      string1 = argParse.getValue(0);
      string2 = argParse.getValue(1);
    } catch (tooFewException e1) {
      errors = true;
      String nextExpected = e1.getNextExpected()
      error_message = "EquivalentStrings error: the argument " + nextExpected + " is required";
    
    } catch (tooManyException e2) {
      errors = true
      String firstExtra = e2.getFirstExtra();
      error_message =
            "EquivalentStrings error: the value " + firstExtra + " matches no argument";

    } catch (HelpException e3) {
      errors = true;
      error_message =
          "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit";
    }
  }

  public String getString1() {
    return string1;
  }

  public String getString2() {
    return string2;
  }

  public int[] mapString(String str) {
    String[] str_hold = new String[str.length()];
    String[] str_arr = str.split("", 0);
    int hold = 0;
    int[] str_map = new int[str.length()];
    for (int i = 0; i < string1.length(); i++) {
      if (Arrays.asList(str_hold).contains(str_arr[i]) == false) {
        str_hold[hold] = str_arr[i];
        str_map[i] = hold;
        hold++;
      } else {
        int num = Arrays.asList(str_hold).indexOf(str_arr[i]);
        str_map[i] = num;
      }
    }
    return str_map;
  }

  public String checkEquivalent(int[] map1, int[] map2) {
    boolean equivalent = true;
    if (errors == false) {
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
    } else {
      return error_message;
    }
  }

  public static void main(String... args) {
    // how do we get these arguments when someone does java EquivalentStrings bob mom
    EquivalentStrings equivStrings = new EquivalentStrings(args);
    String string1 = equivStrings.getString1();
    String string2 = equivStrings.getString2();
    int[] map1 = equivStrings.mapString(string1);
    int[] map2 = equivStrings.mapString(string2);
    String equivalence = equivStrings.checkEquivalent(map1, map2);
    System.out.println(equivalence);
  }
}
