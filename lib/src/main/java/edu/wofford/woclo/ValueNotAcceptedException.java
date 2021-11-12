package edu.wofford.woclo;

public class ValueNotAcceptedException extends RuntimeException {
  private String value;

  public ValueNotAcceptedException(String value) {
    this.value = value;
  }

  public String getUnacceptedValue() {
    return value;
  }
}
