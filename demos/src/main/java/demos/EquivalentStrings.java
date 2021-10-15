package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class EquivalentStrings {
  private String string1;
  private String string2;
  private int[] string_1_array;
  private int[] string_2_array;

  public EquivalentStrings(String string1, String string2) {
    this.string1 = string1;
    this.string2 = string2;
  }

  public int[] mapString1(String string1) {
    String[] string_1_hold = new String[string1.length()];
    String[] string_1_as_arr = string1.split("", 0);
    int hold = 0;
    int[] string_1_map = new int[string1.length()];
    for (int i = 0; i < string1.length(); i++) {
      if (Arrays.asList(string_1_hold).contains(string_1_as_arr[i]) == false) {
        string_1_hold[hold] = string_1_as_arr[i];
        string_1_map[i] = hold;
        hold++;
      } else {

      }
    }
    return string_1_map;
  }

  public boolean checkEquivalent(String string1, String string2) {
    return false;
  }

  public static void main(String... args) {}
}
