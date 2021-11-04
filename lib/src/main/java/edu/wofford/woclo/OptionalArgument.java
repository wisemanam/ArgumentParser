package edu.wofford.woclo;

public class OptionalArgument extends Argument {
  private String value;

  public OptionalArgument(String name, String type, String description, String value) {
    super(name, type, description);
    this.value = value;
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
}
