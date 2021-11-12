package edu.wofford.woclo;

/**
 * The ArgumentNameNotSpecifiedException is thrown when the name of an argument given to
 * ArgumentParser does not match an expected argument name. The exception requires the argument name
 * given to the ArgumentParser. It includes methods that return the name of the argument that does
 * not match an expected argument name.
 */
public class ArgumentNameNotSpecifiedException extends RuntimeException {
  String arg_name;

  /**
   * The constructor for the exception thrown when when the name of an argument given to
   * ArgumentParser does not match an expected argument name.
   *
   * @param arg_name the name of the argument that does not match an expected argument name
   */
  public ArgumentNameNotSpecifiedException(String arg_name) {
    this.arg_name = arg_name;
  }

  /**
   * Gets the name of the argument that does not match an expected argument name
   *
   * @return the name of the argument that does not match an expected argument name
   */
  public String getArgName() {
    return arg_name;
  }
}
