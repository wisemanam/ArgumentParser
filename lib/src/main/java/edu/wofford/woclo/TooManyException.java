package edu.wofford.woclo;

import java.util.*;

/**
 * The TooManyException is thrown when the number of arguments given to ArgumentParser is greater
 * than the number expected. The exception requires the expected number of arguments and the list of
 * arguments given. It includes methods that return the first additional argument that was given to
 * ArgumentParser.
 */
public class TooManyException extends RuntimeException {
  String value;
  String[] args_list;
  List<String> separated_args;

  /**
   * The constructor for the exception thrown when the number of arguments is greater than expected.
   *
   * @param value the extra value of the first additional agrument that is given on the command line
   */
  public TooManyException(String value) {
    this.value = value;
  }
  /**
   * Gets the first argument after the initial expected arguments.
   *
   * @return the first additional argument that is given on the command line
   */
  public String getFirstExtra() {
    return value;
  }
}
