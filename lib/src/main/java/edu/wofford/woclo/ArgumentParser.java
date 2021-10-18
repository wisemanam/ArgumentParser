package edu.wofford.woclo;

import java.util.*;

public class ArgumentParser {
  // Feature 1 Implementation
  private String[] args_list;
  private int expectedArgs;
  private boolean help;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   *
   * @param expectedArgs
   * @param arguments
   */
  public ArgumentParser(int expectedArgs, String arguments) {
    // add help message
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

  /**
   * Takes an integer and returns the corresponding string.
   *
   * @param index
   * @return string corresponding to the index
   */
  public String getValue(int index) {
    return args_list[index];
  }

  /**
   * Returns the number of words in the string passed to ArgumentParser.
   *
   * @return the number of words in the string
   */
  public int numArgs() {
    return args_list.length;
  }

  /** @return true if the number of words entered does not match the number of words expected */
  public boolean numWordsNotExpected() {
    return args_list.length != expectedArgs;
  }

  // Feature 2 Implementation

  public boolean needsHelp() {
    return help;
    // need to change this to add more info
    // in the constructor add a string to print when we need help
  }
}
