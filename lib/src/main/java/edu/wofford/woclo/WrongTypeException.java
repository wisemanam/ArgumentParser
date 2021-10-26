package edu.wofford.woclo;
/**
 * The WrongTypeException is thrown by ArgumentParser when a value cannot be converted to its
 * designated type
 */
public class WrongTypeException extends RuntimeException {
  /**
   * Takes a message and prints it when WrongTypeException is thrown.
   *
   * @param message the message that the user wishes to print when the WrongTypeException is thrown.
   */
  public WrongTypeException(String message) {
    super(message);
  }
}
