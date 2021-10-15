package edu.wofford.woclo;

import java.util.*;

public class ArgumentParser {
  // Feature 1 Implementation
  private String[] args_list;
  private int expectedArgs;
  private boolean help;

  public ArgumentParser(int expectedArgs, String arguments) {
    if (!arguments.equals("")) {
      args_list = arguments.split(" ");
      if (Arrays.asList(args_list).contains("--help") || Arrays.asList(args_list).contains("--h")) {
        help = true;
      }
    } else {
      args_list = new String[0];
    }
    this.expectedArgs = expectedArgs;
    help = false;
  }

  public String getValue(int index) {
    return args_list[index];
  }

  public int numArgs() {
    return args_list.length;
  }

  // Feature 2 Implementation

  public void needsHelp() {
    if (help) {
      // something that tells program to stop and print help message
    }
  }

}
