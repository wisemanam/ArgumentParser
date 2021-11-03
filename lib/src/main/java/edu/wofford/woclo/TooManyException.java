package edu.wofford.woclo;

/**
 * The TooManyException is thrown when the number of arguments given to ArgumentParser is greater
 * than the number expected. The exception requires the expected number of arguments and the list of
 * arguments given. It includes methods that return the first additional argument that was given to
 * ArgumentParser.
 */
public class TooManyException extends RuntimeException {
  int expected;
  String[] args_list;

  /**
   * The constructor for the exception thrown when the number of arguments is greater than expected.
   *
   * @param expected the expected number of arguments given from the command line
   * @param args_list the list of arguments from the command line
   */
  public TooManyException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list.clone();
  }
  /**
   * Gets the first argument after the initial expected arguments.
   *
   * @return the first additional argument that is given on the command line
   */
  public String getFirstExtra() {
    return args_list[expected - 1];
  }
}
