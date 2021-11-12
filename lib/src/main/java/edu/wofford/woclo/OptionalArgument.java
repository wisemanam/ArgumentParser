package edu.wofford.woclo;

import java.util.*;

/**
 * OptionalArgument is a subclass of argument that handles arguments that will come in named on the
 * command line (if they are given at all) and be given a default value by the client for cases in
 * which the user does not assign it a value.
 */
public class OptionalArgument extends Argument {
  private String value;
  private String short_name;
  private String[] accepted_values;

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
  public OptionalArgument(
      String name, String short_name, String type, String description, String value) {
    super(name, type, description);
    this.value = value;
    this.short_name = short_name;
    accepted_values = null;
  }

  public OptionalArgument(String name, String type, String description, String value) {
    super(name, type, description);
    this.value = value;
    short_name = "";
    accepted_values = null;
  }

  public OptionalArgument(
      String name,
      String short_name,
      String type,
      String description,
      String value,
      String[] accepted_values) {
    super(name, type, description);
    this.value = value;
    this.short_name = short_name;
    this.accepted_values = accepted_values.clone();
  }

  public OptionalArgument(
      String name, String type, String description, String value, String[] accepted_values) {
    super(name, type, description);
    this.value = value;
    short_name = "";
    this.accepted_values = accepted_values.clone();
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
      return (T) Boolean.valueOf(value);
    }
  }

  public String getShortName() {
    return short_name;
  }

  public boolean hasAcceptedValues() {
    if (accepted_values == null) {
      return false;
    } else {
      return true;
    }
  }

  public boolean isAcceptedValue(String arg_val) {
    if (Arrays.asList(accepted_values).contains(arg_val)) {
      return true;
    } else {
      return false;
    }
  }
}
