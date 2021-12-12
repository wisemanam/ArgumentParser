package edu.wofford.woclo;

import java.awt.List;

/** The HelpException is thrown by ArgumentParser when "--help" or "--h" is one of the arguments. */
public class HelpException extends RuntimeException {
  ArgumentParser argParse;
  String demoName;
  List<List<String>> allArgumentsList;

  /**
   * Takes a message and prints it when HelpException is thrown.
   *
   * @param message the message that the user wishes to print when the HelpException is thrown.
   */
  public HelpException(String message) {
    super(message);
  }

  public HelpException(ArgumentParser argParse, String demoName) {
    this.argParse = argParse;
    this.demoName = demoName;
    allArgumentsList = getArgumentList(argParse);
  }

  private List<List<String>> getArgumentList() {
    // add positionals 
    // ["length", "(float)", "the length of the volume"]
    List<List<String>> allArgs = new ArrayList<List<String>>();

    for (int i = 0; i < argParse.getPositionalNames().size(); i++) {
      List<String> argumentList = new ArrayList<String>();
      String name = argParse.getPositionalNames().get(i);
      Argument arg = argParse.getArgument(name);
      argumentList.add(arg.getName());
      argumentList.add("(" + arg.getType() + ")");
      argumentList.add(arg.getDescription());

      allArgs.add(argumentList);
    }

    // add named args
    //["-p PRECISION, --precision PRECISION", "(integer)", "the maximum number of decimal places for the volume"]
    for (int i = 0; i < argParse.getNonPositionalNames().size(); i++) {
      List<String> argumentList = new ArrayList<String>();
      String name = argParse.getNonPositionalNames().get(i);
      Argument arg = argParse.getArgument(name);
      String shortName = "";

      if (arg instanceof OptionalArgument) {
        OptionalArgument optArg = (OptionalArgument) arg;
        shortName = optArg.getShortName();
      }

      // add help flags
      List<String> helpArgs = new ArrayList<String>();
      helpArgs.add("-h, --help");
      helpArgs.add("show this help message and exit");
      allArgumentsList.add(helpArgs);

      // if there is a short name
      name_str = "";
      if (!shortName.equals("")) {
        name_str += "-" + shortName;
        if (arg.getType() != "boolean") {
          name_str += " " + arg.getName().toUpperCase() + ", ";
        }
      }
      name_str += "--" + arg.getName() + " " + arg.getName().toUpperCase();

      argumentList.add(name_string); 
      argumentList.add("(" + arg.getType() + ")");
      argumentList.add(arg.getDescription());

      allArgs.add(argumentList);
    }
    return allArgs;
  }

  public String getSummaryString() {
    String str = "usage: java " + demoName + " [-h] ";

    nonPositionals = argParse.getNonPositionalNames();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < nonPositionals.size(); i++) {
      Argument a = nonPositionals.get(i);
      if (a instanceof OptionalArgument) {
        OptionalArgument optArg = (OptionalArgument) a;
        if (!optArg.isRequired()) {
          sb.append("[");

        if (optArg.hasShortName()) {
          sb.append("-" + optArg.getShortName());
        } else {
          sb.append("--" + optArg.getName());
        }

        if (!optArg.getType().equals("boolean")) {
          sb.append(" " + optArg.getName().toUpperCase());
        }

        if (!optArg.isRequired()) {
          sb.append("]");
        }
      }
    }
    for (int j = 0; j < argParse.getPositionalNames().size(); j++) {
      sb.append(argParse.getPositionalNames().get(j) + " ");
    }

    str += sb.toString();
    return str.trim();
    }
  }
}
