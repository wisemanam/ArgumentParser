package edu.wofford.woclo;

/**
 * The NoValueException is thrown when a nonpositional argument is given to ArgumentParser without a
 * value. The exception requires the name of the nonpositional arguments that is missing a value. It
 * includes methods that return the name of the nonpositional argument that is missing a value.
 */
public class NoValueException extends RuntimeException {
  private String name;

  /**
   * The constructor for the exception thrown when a nonpositional argument does not have a value.
   *
   * @param name The name of the nonpositional argument that does not have a value.
   */
  public NoValueException(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the nonpositional argument that was not given a value.
   *
   * @return the name of the nonpositional argument that was not given a value
   */
  public String getNameMissingValue() {
    return name;
  }
}
