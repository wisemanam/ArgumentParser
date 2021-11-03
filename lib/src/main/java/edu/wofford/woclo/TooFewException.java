package edu.wofford.woclo;

import java.util.*;

/**
 * The TooManyException is thrown when the number of arguments given to ArgumentParser is less than
 * the number expected. The exception requires the expected number of arguments and the list of
 * arguments given. It includes methods that return the name of the next expected argument.
 */
public class TooFewException extends RuntimeException {
  int actual;
  List<String> names;
  Map<String, Argument> args;

  /**
   * The constructor for the exception thrown when the number of arguments is less than expected.
   *
   * @param expected the expected number of arguments given from the command line
   * @param args_list the list of arguments from the command line
   */
  public TooFewException(int actual, List<String> names, Map<String, Argument> args) {
    this.actual = actual;
    this.names = names;
    this.args = args;
  }
  /**
   * Gets the index of the next argument that ArgumentParser expects.
   *
   * @return the index of the the next expected argument
   */
  public String getNextExpectedName() {
    return names.get(actual);
  }

  public String getNextExpectedValue() {
    String name = names.get(actual);
    Argument arg = args.get(name);
    String value = arg.getValue();
    return value;
  }
}
