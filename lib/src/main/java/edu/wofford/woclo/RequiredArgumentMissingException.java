package edu.wofford.woclo;

public class RequiredArgumentMissingException extends RuntimeException {
  private String message;

  public RequiredArgumentMissingException(String message) {
    System.out.println("Hello");
    this.message = message;
  }

  public String message() {
    return message;
  }
}
