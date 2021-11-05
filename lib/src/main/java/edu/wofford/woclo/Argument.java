package edu.wofford.woclo;
// import java.lang.ProcessBuilder.Redirect.Type;
/**
 * Argument collects information about what is expected to come in on the command line. Each
 * argument has a corresponding name, type, value, and description.
 */
public class Argument {
  protected String name;
  protected String type;
  protected String description;
  protected String value;
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
        throw new WrongTypeException(value);
      }
    } else {
      try {
        return (T) Float.valueOf(value);
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value);
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
}
