package edu.wofford.woclo;

public class TooManyException extends RuntimeException {
  int expected;
  String[] args_list;

  public TooManyException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list.clone();
  }

  public String getFirstExtra() {
    return args_list[expected];
  }
}
