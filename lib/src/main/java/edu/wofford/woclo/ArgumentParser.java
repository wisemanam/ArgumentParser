package edu.wofford.woclo;

public class ArgumentParser {
  // Feature 1 Implementation
  private String[] args_list;

  public ArgumentParser(String arguments) {
    args_list = arguments.split(" ");
  }

  public String getValue(int index) {
    return args_list[index];
  }

  public int numArgs() {
    return args_list.length;
  }

  // Feature 2 Implementation

}
