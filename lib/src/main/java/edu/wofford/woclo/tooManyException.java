package edu.wofford.woclo;

public class tooManyException extends RuntimeException {
  int expected;
  String[] args_list;

  public tooManyException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list;
  }

  public String getFirstExtra() {
    return args_list[expected];
  }
}