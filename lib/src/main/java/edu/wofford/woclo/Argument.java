package edu.wofford.woclo;
// import java.lang.ProcessBuilder.Redirect.Type;

public class Argument {
  protected String name;
  protected String type;
  protected String description;
  protected String value;

  public Argument(String name, String type, String description) {
    this.name = name;
    this.type = type;
    this.description = description;
  }

  public void setValue(String value) {
    this.value = value;
  }

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

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
