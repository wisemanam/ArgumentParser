package edu.wofford.woclo;
/**
 * The TooManyException is thrown when the number of arguments given to ArgumentParser is less than
 * the number expected. The exception requires the expected number of arguments and the list of
 * arguments given. It includes methods that return the name of the next expected argument.
 */
public class TooFewException extends RuntimeException {
  int expected;
  String[] args_list;

  /**
   * The constructor for the exception thrown when the number of arguments is less than expected.
   *
   * @param expected the expected number of arguments given from the command line
   * @param args_list the list of arguments from the command line
   */
  public TooFewException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list.clone();
  }
  /**
   * Gets the index of the next argument that ArgumentParser expects.
   *
   * @return the index of the the next expected argument
   */
  public int getNextExpected() {
    return args_list.length;
  }
}
