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
    value = "";
  }

  public void setValue(String value) {
    this.value = value;
  }

<<<<<<< HEAD
  public <T> T getValue() {
    return (T) value;
=======
  public String getValue() {
    return value;
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
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
