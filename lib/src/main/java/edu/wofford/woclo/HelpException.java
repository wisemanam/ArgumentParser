package edu.wofford.woclo;
/** The HelpException is thrown by ArgumentParser when "--help" or "--h" is one of the arguments. */
public class HelpException extends RuntimeException {
  /**
   * Takes a message and prints it when HelpException is thrown.
   *
   * @param message the message that the user wishes to print when the HelpException is thrown.
   */
  public HelpException(String message) {
    super(message);
  }
}
