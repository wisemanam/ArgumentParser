package edu.wofford.woclo;

import java.io.*;
import java.util.*;

public class RequiredArgumentMissingException extends RuntimeException {
  private List<String> required_names;
  private String[] arguments;
  private List<String> trashcan;
  private Map<String, String> short_to_long;

  public RequiredArgumentMissingException(
      List<String> required_names, String[] arguments, Map<String, String> short_to_long) {
    this.arguments = arguments.clone();
    this.required_names = new ArrayList<String>(required_names);
    this.short_to_long = new HashMap<String, String>(short_to_long);
    trashcan = new ArrayList<String>();
  }

  public String getMissingRequired() {
    String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};
    Queue<String> args = new LinkedList<String>();
    for (int i = 0; i < arguments.length; i++) {
      args.add(arguments[i]);
    }
    String missing = "";
    while (!args.isEmpty()) {
      if (args.peek().startsWith("--")) {
        String name = args.poll().substring(2);
        if (required_names.contains(name)) {
          boolean rm = required_names.remove(name);
          trashcan.add(Boolean.toString(rm));
        }
      } else if (args.peek().startsWith("-")
          && !Arrays.asList(nums).contains(Character.toString(args.peek().charAt(1)))) {
        String short_name = args.poll().substring(1);
        String name = short_to_long.get(short_name);
        if (required_names.contains(name)) {
          boolean rm = required_names.remove(name);
          trashcan.add(Boolean.toString(rm));
        }
      } else {
        trashcan.add(args.poll());
      }
    }
    return required_names.get(0);
  }
}
