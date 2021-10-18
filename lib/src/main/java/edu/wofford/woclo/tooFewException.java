package edu.wofford.woclo;

public class TooFewException extends RuntimeException {
  int expected;
  String[] args_list;

  public TooFewException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list.clone();
  }

  public String getNextExpected() {
    return "string" + String.valueOf(args_list.length + 1);
  }
}
