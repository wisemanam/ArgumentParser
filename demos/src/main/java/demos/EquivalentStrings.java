package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class EquivalentStrings {
  String string1;
  String string2;
  boolean errors;

  public EquivalentStrings(String strings) {
    ArguementParser argParse = new ArguementParser(strings);
    if (argParse.numArgs() > 2){
      errors = true;
    }
    errors = false;
    string1 = argParse.getValue(0);
    string2 = argParse.getvalue(1);
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
    }
    return "not equivalent";
  }

  public static void main(String... args) {
  }
}
