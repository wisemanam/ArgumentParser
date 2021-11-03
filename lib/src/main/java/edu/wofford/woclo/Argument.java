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
    if (type == "string") {
      return (T)value;
    } else if (type == "integer") {
      try {
        int val = Integer.parseInt(value);
        return (T)val;
      } catch (NumberFormatException e) {
        throw new WrongTypeException(value);
      }
    } else {
      try {
        float val = Float.parseFloat(value);
        return (T)val;
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
