package edu.wofford.woclo;

public class ArgumentParser {
  // Feature 1 Implementation
  private String[] args_list;

  public ArgumentParser(String arguments) {
    if (!arguments.equals("")) {
      args_list = arguments.split(" ");
    } else {
      args_list = new String[0];
    }
  }

  public String getValue(int index) {

    return args_list[index];
  }

  public int numArgs() {
    return args_list.length;
  }

  // Feature 2 Implementation

}
