package edu.wofford.woclo;

/**
 * OptionalArgument is a subclass of argument that handles arguments that will come in named on the
 * command line (if they are given at all) and be given a default value by the client for cases in
 * which the user does not assign it a value.
 */
public class OptionalArgument extends Argument {
  private String value;
  /**
   * The OptionalArgument constructor takes the name of the variable, its type, the description used
   * in the help message, and the value that will be used as its default value. The client can call
   * getValue to retrieve the stored value in the type specified when OptionalArgument is
   * constructed.
   *
   * @param name the name associated with the value
   * @param type the type of the value
   * @param description a description of the argument used for the help message
   * @param value the value associated with the argument
   */
  public OptionalArgument(String name, String type, String description, String value) {
    super(name, type, description);
    this.value = value;
  }
  /** setValue sets the value of the Argument to be the value given as a parameter */
  public void setValue(String value) {
    this.value = value;
  }
  /** getValue returns the value of the argument in the type specified in the constructor */
  @Override
  public <T> T getValue() {
    if (type.equals("string")) {
      return (T) value;
    } else if (type.equals("integer")) {
      try {
        return (T) Integer.valueOf(value);
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value);
      }
    } else if (type.equals("float")) {
      try {
        return (T) Float.valueOf(value);
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value);
      }
    } else {
      if (value.equals("true") || value.equals("false")) {
        return (T) Boolean.valueOf(value);
      } else {
        throw new WrongTypeException(value);
      }
    }
  }
}
