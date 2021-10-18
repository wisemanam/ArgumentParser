package edu.wofford.woclo;

import java.util.*;

public class ArgumentParser {
  private String[] args_list;
  private int expectedArgs;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   *
   * @param expectedArgs the number of values that the client expects to receive
   * @param arguments a list of the arguments the client would like to parse
   */
  public ArgumentParser(int expectedArgs, String[] arguments) {
    // add help message
    args_list = arguments.clone();
    this.expectedArgs = expectedArgs;

    if (expectedArgs > args_list.length) {
      throw new tooFewException(expectedArgs, args_list);
    } else if (expectedArgs < args_list.length) {
      throw new tooManyException(expectedArgs, args_list);
    }

    if (Arrays.asList(args_list).contains("--help") || Arrays.asList(args_list).contains("--h")) {
      throw new HelpException("Help needed.");
    }
  }

  /**
   * Takes an integer and returns the corresponding string.
   *
   * @param index index of the string to be returned
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

  // Feature 2 Implementation

  /** @return true if there are no arguments given */
  public boolean isEmpty() {
    return args_list.length == 0;
  }
}
