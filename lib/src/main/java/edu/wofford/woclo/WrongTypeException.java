package edu.wofford.woclo;
/**
 * The WrongTypeException is thrown by ArgumentParser when a value cannot be converted to its
 * designated type
 */
public class WrongTypeException extends RuntimeException {
  Argument argument;
  /**
   * Takes a message and prints it when WrongTypeException is thrown.
   *
   * @param value the value that was attempting to be converted
   */
  public WrongTypeException(Argument argument) {
    this.argument = argument;
  }

  public String getWrongValue() {
    String value = argument.getValue();
    return value;
  }
}
