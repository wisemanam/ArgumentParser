package edu.wofford.woclo;
/**
 * The WrongTypeException is thrown by ArgumentParser when a value cannot be converted to its
 * designated type
 */
public class WrongTypeException extends RuntimeException {
  String value;
  String type;
  /**
   * Takes a message and prints it when WrongTypeException is thrown.
   *
   * @param value the value that was attempting to be converted
   * @param type the expected type of the argument
   */
  public WrongTypeException(String value, String type) {
    this.value = value;
    this.type = type;
  }
  /**
   * Returns the value that is not the correct type
   *
   * @return the value with an incorrect type.
   */
  public String getWrongValue() {
    return value;
  }
  /**
   * Returns the expected type of the argument.
   *
   * @return the expected type of the argument
   */
  public String getExpectedType() {
    return type;
  }
}
