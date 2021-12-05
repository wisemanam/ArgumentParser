package edu.wofford.woclo;

import java.util.*;

/**
 * Argument collects information about what is expected to come in on the command line. Each
 * argument has a corresponding name, type, value, and description.
 */
public class Argument {
  protected String name;
  protected String type;
  protected String description;
  protected String value;
  protected String[] accepted_values;
  /**
   * When an instance of Argument is created, it requires the arguments associated name, the type of
   * the value, and the description used in the help message.
   *
   * @param name the name associated with the incoming value
   * @param type the type of the value
   * @param description the description used in the help message
   */
  public Argument(String name, String type, String description) {
    this.name = name;
    this.type = type;
    this.description = description;
    accepted_values = null;
  }
  /**
   * Creates an argument with the given name, value, description, accepted values.
   *
   * @param name the name associated with the incoming value
   * @param type the type of the value
   * @param description the description used in the help message
   * @param accepted_values the list of acceptable values for the argument
   */
  public Argument(String name, String type, String description, String[] accepted_values) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.accepted_values = accepted_values.clone();
  }
  /**
   * setValue takes a string and sets the value equal to that string
   *
   * @param value the value of the argument as a string
   */
  public void setValue(String value) {
    this.value = value;
  }
  /**
   * returns the value of the argument in the type specified in the constructor
   *
   * @param <T> the type of the returned value
   * @return the value of the argument
   */
  public <T> T getValue() {
    if (type.equals("string")) {
      return (T) value;
    } else if (type.equals("integer")) {
      try {
        return (T) Integer.valueOf(value);
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value, type);
      }
    } else {
      try {
        return (T) Float.valueOf(value);
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value, type);
      }
    }
  }
  /**
   * Gets the type of the value in the form of a string
   *
   * @return the type of the value
   */
  public String getType() {
    return type;
  }
  /**
   * Gets the name associated with the argument
   *
   * @return the name of the argument
   */
  public String getName() {
    return name;
  }
  /**
   * Gets the description used in the help message
   *
   * @return description string
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns true if there are restricted values for this argument.
   *
   * @return boolean stating whether or not the argument has specific accepted values
   */
  public boolean hasAcceptedValues() {
    if (accepted_values == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns whether or not the given argument is an acceptable value for this argument.
   *
   * @param arg_val value to be determined whether or not it is an acceptable value
   * @return true if the value is acceptable, false otherwise
   */
  public boolean isAcceptedValue(String arg_val) {
    if (Arrays.asList(accepted_values).contains(arg_val)) {
      return true;
    } else {
      return false;
    }
  }

  public String[] getAcceptedValues() {
    return accepted_values;
  }
}
