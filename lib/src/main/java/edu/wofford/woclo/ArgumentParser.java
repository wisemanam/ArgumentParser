package edu.wofford.woclo;

import java.util.*;

/**
 * The ArgumentParser class takes the arguments entered into the command line and parses each
 * argument as strings. The class includes methods for examining the number of arguments given to
 * the constructor as well as a method for returning the argument at a certain index.
 * ArgumentParsers are constant and changing the list of arguments that was given to the constructor
 * will not change the contents of the ArgumentParser.
 */
public class ArgumentParser {
  private String[] args_list;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   *
   * @param expected_args the number of values that the client expects to receive
   * @param arguments a list of the arguments the client would like to parse
   */
  public ArgumentParser(int expected_args, String[] arguments) {
    // add help message
    args_list = arguments.clone();
    int expectedArgs = expected_args;

    if (Arrays.asList(args_list).contains("--help") || Arrays.asList(args_list).contains("--h")) {
      throw new HelpException("Help needed.");
    } else if (expectedArgs > args_list.length) {
      throw new TooFewException(expectedArgs, args_list);
    } else if (expectedArgs < args_list.length) {
      throw new TooManyException(expectedArgs, args_list);
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
