package demos;

import edu.wofford.woclo.*;
import java.util.*;

/**
 * Equivalent strings takes a string containing two words and returns a boolean that indicates
 * whether the two words are equal (if the patterns of their letters are the same).
 */
public class EquivalentStrings {
  String string1;
  String string2;
  String error_message;
  boolean errors;
  int num;

  /**
   * Constructor that takes a string and uses ArgumentParser to extract the two words to be checked
   * for equivalence. If the number of words given does not match the number of words expected then
   * an error message is printed.
   * 
   * @param strings
   */
  public EquivalentStrings(String[] strings) {
    ArgumentParser argParse = new ArgumentParser(2, strings);
    errors = false;
    error_message = "";
    if (argParse.numArgs() != 2) {
      errors = true;
      num = argParse.numArgs();
      string1 = "";
      string2 = "";
      if (num == 0) {
        error_message = "EquivalentStrings error: the argument string1 is required";
      } else if (num == 1) {
        error_message = "EquivalentStrings error: the argument string2 is required";
      } else {
        String error_value = argParse.getValue(2);
        error_message =
            "EquivalentStrings error: the value " + error_value + " matches no argument";
      }
    } else {
      string1 = argParse.getValue(0);
      string2 = argParse.getValue(1);
    }
  }

  /** Returns the first string that is being compared */
  public String getString1() {
    return string1;
  }

  /** Returns the second string that is being compared */
  public String getString2() {
    return string2;
  }
  
  /**
   * Takes a string and returns...
   * 
   * @param str
   */
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

  /**
   * Compares the mapped strings to check for equivalence.
   * Returns equivalent if the mapped integer arays of the two strings are equivalent.
   * Returns not equivalent if the mapped integer arrays of the two strings are not equivalent.
   * 
   * @param map1
   * @param map2
   * 
   * @return equivalent or not equivalent
   */
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
    if (equivalent && errors == false) {
      return "equivalent";
    } else if (errors == true) {
      return error_message;
    }
    return "not equivalent";
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
