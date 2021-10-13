package edu.wofford.woclo;

public class ArgumentParser {
  // Feature 1 Implementation
  private String[] args;

  public ArgumentParser(String[] arguments) {
    args = arguments;
  }

  public String getValue(int index) {
    return args[index];
  }

  // Feature 2 Implementation

}
