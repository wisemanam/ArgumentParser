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
   */
  public WrongTypeException(String value, String type) {
    this.value = value;
    this.type = type;
  }

  public String getWrongValue() {
    return value;
  }

  public String getExpectedType() {
    return type;
  }
}
