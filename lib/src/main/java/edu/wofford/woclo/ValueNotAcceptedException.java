package edu.wofford.woclo;

public class ValueNotAcceptedException extends RuntimeException {
  private String value;
  private String name;

  public ValueNotAcceptedException(String value, String name) {
    this.value = value;
    this.name = name;
  }

  public String getUnacceptedValue() {
    return value;
  }

  public String getVarName() {
    return name;
  }
}
