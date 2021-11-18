package edu.wofford.woclo;
/**
 * The ValueNotAcceptedException is thrown when the user attempts to assign the argument a value that
 * is not in the list of accepted values set by the client.
 */
public class ValueNotAcceptedException extends RuntimeException {
  private String value;
  private String name;
  /**
   * The constructor for the ValueNotAcceptedException
   * @param value the value that is not an accepted value for the argument
   * @param name the name of the argument
   */
  public ValueNotAcceptedException(String value, String name) {
    this.value = value;
    this.name = name;
  }
  /**
   * Returns the value that is not accepted.
   * @return the unaccepted value
   */
  public String getUnacceptedValue() {
    return value;
  }
  /**
   * Returns the argument name.
   * @return the name of the argument
   */
  public String getVarName() {
    return name;
  }
}
