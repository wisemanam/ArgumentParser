package edu.wofford.woclo;

// import java.awt.List;
import java.io.*;
import java.util.*;

/** The HelpException is thrown by ArgumentParser when "--help" or "--h" is one of the arguments. */
public class HelpException extends RuntimeException {
  ArgumentParser argParse;
  List<List<String>> allArgumentsList;
  List<List<String>> positionalStringList;
  List<List<String>> namedStringList;
  List<String> helpArgs;
  List<List<String>> flags;
  /**
   * Takes a message and prints it when HelpException is thrown.
   *
   * @param message the message that the user wishes to print when the HelpException is thrown.
   */
  public HelpException(String message) {
    super(message);
  }

  public HelpException(ArgumentParser argParse) {
    this.argParse = new ArgumentParser(argParse);
    allArgumentsList = new ArrayList<List<String>>();
    positionalStringList = new ArrayList<List<String>>();
    namedStringList = new ArrayList<List<String>>();
    helpArgs = new ArrayList<String>();
    flags = new ArrayList<List<String>>();
  }

  private void getArgumentList() {
    // add help flags
    helpArgs.add("-h, --help");
    helpArgs.add("show this help message and exit");
    allArgumentsList.add(helpArgs);

    // add positionals
    // ["length", "(float)", "the length of the volume"]

    for (int i = 0; i < argParse.getPositionalNames().size(); i++) {
      List<String> argumentList = new ArrayList<String>();
      String name = argParse.getPositionalNames().get(i);
      Argument arg = argParse.getArgument(name);
      argumentList.add(arg.getName());
      argumentList.add("(" + arg.getType() + ")");
      argumentList.add(arg.getDescription());

      positionalStringList.add(argumentList);
      allArgumentsList.add(argumentList);
    }

    // add named args
    // ["-p PRECISION, --precision PRECISION", "(integer)", "the maximum number of decimal places
    // for the volume"]
    for (int i = 0; i < argParse.getNonPositionalNames().size(); i++) {
      List<String> argumentList = new ArrayList<String>();
      String name = argParse.getNonPositionalNames().get(i);
      Argument arg = argParse.getArgument(name);
      String shortName = "";
      String defaultValue = "";
      boolean required = false;
      if (arg instanceof OptionalArgument) {
        OptionalArgument optArg = (OptionalArgument) arg;
        shortName = optArg.getShortName();
        defaultValue = optArg.getValueAsString();
        required = optArg.isRequired();
      }

      // if there is a short name
      String name_str = "";
      if (!shortName.equals("") && !arg.getType().equals("boolean")) {
        name_str +=
            "-"
                + shortName
                + " "
                + arg.getName().toUpperCase(Locale.ENGLISH)
                + ", "
                + "--"
                + arg.getName()
                + " "
                + arg.getName().toUpperCase(Locale.ENGLISH);
      } else if (shortName.equals("") && !arg.getType().equals("boolean")) {
        name_str += "--" + arg.getName() + " " + arg.getName().toUpperCase(Locale.ENGLISH);
      }
      argumentList.add(name_str);
      if (!arg.getType().equals("boolean")) {
        argumentList.add("(" + arg.getType() + ")");
      }
      argumentList.add(arg.getDescription());

      if (arg.hasAcceptedValues()) {
        StringBuilder sb = new StringBuilder();
        String[] accepted = arg.getAcceptedValues();

        sb.append("{");

        for (int j = 0; j < accepted.length - 1; j++) {
          sb.append(accepted[j].trim() + ", ");
        }
        sb.append(accepted[accepted.length - 1] + "}");

        argumentList.add(sb.toString());
      }
      if (!required) {
        argumentList.add("(default: " + defaultValue + ")");
      }

      if (arg.getType().equals("boolean")) {
        argumentList.clear();
        if (!shortName.equals("")) {
          argumentList.add(" -" + shortName + ", " + "--" + arg.getName().trim());
          flags.add(argumentList);
        } else {
          argumentList.add(" --" + arg.getName());
          flags.add(argumentList);
        }
      } else {
        namedStringList.add(argumentList);
      }
      allArgumentsList.add(argumentList);
    }
  }

  private int findSpacing() {
    int m = 0;
    for (int i = 0; i < allArgumentsList.size(); i++) {
      if (allArgumentsList.get(i).get(0).length() > m) {
        m = allArgumentsList.get(i).get(0).length();
      }
    }
    return m + 2;
  }

  private String getSummaryString(String demoName) {
    getArgumentList();
    String str = "usage: java " + demoName + " [-h] ";

    List<String> nonPositionals = argParse.getNonPositionalNames();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < nonPositionals.size(); i++) {
      String n = nonPositionals.get(i);
      Argument a = argParse.getArgument(n);
      if (a instanceof OptionalArgument) {
        OptionalArgument optArg = (OptionalArgument) a;
        if (!optArg.isRequired()) {
          sb.append("[");
        }
        if (optArg.hasShortName()) {
          sb.append("-" + optArg.getShortName());
        } else {
          sb.append("--" + optArg.getName());
        }

        if (!optArg.getType().equals("boolean")) {
          sb.append(" " + optArg.getName().toUpperCase(Locale.ENGLISH));
        }

        if (!optArg.isRequired()) {
          sb.append("] ");
        } else {
          sb.append(" ");
        }
      }
    }
    for (int j = 0; j < argParse.getPositionalNames().size(); j++) {
      sb.append(argParse.getPositionalNames().get(j) + " ");
    }

    str += sb.toString();
    return str.trim();
  }

  public String getHelpMessage(String demoName, String demoDescription) {
    String s = getSummaryString(demoName) + "\n\n";
    s += demoDescription + "\n\n";

    int numSpaces1 = 0;
    if (positionalStringList.size() != 0) {
      s += "positional arguments:\n";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < positionalStringList.size(); i++) {
        sb.append(" " + positionalStringList.get(i).get(0));

        numSpaces1 = findSpacing() - positionalStringList.get(i).get(0).length();
        sb.append(String.join("", Collections.nCopies(numSpaces1, " ")));

        String type = positionalStringList.get(i).get(1);
        int numSpaces2 = "(integer)     ".length() - type.length();
        sb.append(type);
        sb.append(String.join("", Collections.nCopies(numSpaces2, " ")));

        sb.append(positionalStringList.get(i).get(2));
        sb.append("\n");
      }
      s += sb.toString() + "\n";
    }

    s += "named arguments:\n";
    numSpaces1 = findSpacing() - helpArgs.get(0).length();
    s +=
        " "
            + helpArgs.get(0)
            + String.join("", Collections.nCopies(numSpaces1, " "))
            + helpArgs.get(1)
            + "\n";

    if (namedStringList.size() != 0) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < namedStringList.size(); i++) {
        sb.append(" " + namedStringList.get(i).get(0));
        numSpaces1 = findSpacing() - namedStringList.get(i).get(0).length();
        sb.append(String.join("", Collections.nCopies(numSpaces1, " ")));

        String type = namedStringList.get(i).get(1);

        int numSpaces2 = "(integer)     ".length() - type.length();
        sb.append(type);
        sb.append(String.join("", Collections.nCopies(numSpaces2, " ")));

        sb.append(namedStringList.get(i).get(2));
        for (int j = 3; j < namedStringList.get(i).size(); j++) {
          sb.append(" " + namedStringList.get(i).get(j));
        }

        sb.append("\n");
      }
      s += sb.toString();
    }

    if (flags.size() != 0) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < flags.size(); i++) {
        sb.append(flags.get(i).get(0));
        numSpaces1 = findSpacing() - flags.get(i).get(0).length() + 1;
        sb.append(String.join("", Collections.nCopies(numSpaces1, " ")));
        sb.append("\n");
      }
      s += sb.toString();
      s += "\n";
    }

    List<List<String>> mutually_exclusive = argParse.getMutuallyExclusive();
    if (mutually_exclusive.size() != 0) {
      s += "mutually exclusive:\n";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < mutually_exclusive.size(); i++) {
        sb.append(" [");
        for (int j = 0; j < mutually_exclusive.get(i).size(); j++) {
          if (j == mutually_exclusive.get(i).size() - 1) {
            sb.append(mutually_exclusive.get(i).get(j) + "]\n");
          } else {
            sb.append(mutually_exclusive.get(i).get(j) + ", ");
          }
        }
      }
      s += sb.toString();
    }
    return s.trim();
  }
}
