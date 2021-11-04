package edu.wofford.woclo;

public class NoValueException extends RuntimeException {
  private String name;

  public NoValueException(String name) {
    this.name = name;
  }

  public String getNameMissingValue() {
    return name;
  }
}
