package edu.wofford.woclo;

public class tooFewException extends RuntimeException {
  int expected;
  String[] args_list;

  public tooFewException(int expected, String[] args_list) {
    this.expected = expected;
    this.args_list = args_list;
  }

  public String getNextExpected() {
    return "string" + String.valueOf(args_list.length + 1);
  }
}
