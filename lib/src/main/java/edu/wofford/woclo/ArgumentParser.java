package edu.wofford.woclo;

import java.util.*;

/**
 * The ArgumentParser class takes the arguments entered into the command line and parses each
 * argument as strings. The class includes methods for examining the number of arguments given to
 * the constructor as well as a method for returning the argument at a certain index.
 * ArgumentParsers are constant and changing the list of arguments that was given to the constructor
 * will not change the contents of the ArgumentParser.
 */
public class ArgumentParser {
  private HashMap<String, Argument> args;
  private List<String> positional_names;
  private List<String> nonpositional_names;
  private int positional_counter;
  private int named_counter;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   * If there are fewer or more arguments than expeted, the constructor throws an exception. It will
   * also throw an exception if arguments contains "--help" or "--h".
   *
   * @param arguments a list of the arguments the client would like to parse
   */
  public ArgumentParser() {
    args = new HashMap<String, Argument>();
    positional_names = new ArrayList<String>();
    nonpositional_names = new ArrayList<String>();
    positional_counter = 0;
    named_counter = 0;
  }

  public void addPositional(String name, String type, String description) {
    Argument arg = new Argument(name, type, description);
    positional_names.add(name);
    args.put(name, arg);
    positional_counter++;
  }

  public void addNonPositional(String name, String type, String description, String value) {
    OptionalArgument arg = new OptionalArgument(name, type, description, value);
    nonpositional_names.add(name);
    args.put(name, arg);
  }

  public void parse(String[] arguments) {
    int total_positional = positional_counter;
    int total_named = named_counter;
    int total_arguments = total_positional + total_named;
    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    }
    int i = 0;
    int positional = 0;
    int actual_total = 0;
    while (i < arguments.length) {
      if (arguments[i].startsWith("--")) {
        String name = arguments[i].substring(2, arguments[i].length());
        String value = arguments[i + 1];
        Argument arg = args.get(name);
        arg.setValue(value);
        Argument check = args.replace(name, arg);
        if (check == null) {
          throw new ArgumentNameNotSpecifiedException(name);
        } else {
          i = i + 2;
          actual_total++;
        }
      } else {
        String name = positional_names.get(positional);
        String value = arguments[i];
        Argument arg = args.get(name);
        arg.setValue(value);
        Argument check = args.replace(name, arg);
        if (check == null) {
          throw new ArgumentNameNotSpecifiedException(name);
        } else {
          i++;
          positional++;
          actual_total++;
        }
      }
    }
    if (total_positional > positional) {
      throw new TooFewException(positional, positional_names, args);
    } else if (total_positional < positional || actual_total > total_arguments) {
      throw new TooManyException(actual_total, arguments);
    }
  }

  /**
   * Takes a string and returns the corresponding string if the type specified by the client is type
   * string.
   *
   * @param arg_name name of the argument wanted
   * @return string corresponding to the name
   */
  public String getValueString(String arg_name) {
    Argument arg = args.get(arg_name);
    if (arg.getType() == "string") {
      String value = arg.getValue();
      return value;
    } else {
      throw new WrongTypeException(arg);
    }
  }

  /**
   * Takes a string and returns the corresponding int if the type specified by the client is type
   * int.
   *
   * @param arg_name name of the argument wanted
   * @return int corresponding to the name
   */
  public int getValueInt(String arg_name) {
    Argument arg = args.get(arg_name);
    if (arg.getType() == "integer") {
      int value = arg.getValue();
      return value;
    } else {
      throw new WrongTypeException(arg);
    }
  }

  /**
   * Takes a string and returns the corresponding float if the type specified by the client is type
   * float.
   *
   * @param arg_name name of the argument wanted
   * @return float corresponding to the name
   */
  public float getValueFloat(String arg_name) {
    Argument arg = args.get(arg_name);
    if (arg.getType() == "float") {
      float value = arg.getValue();
      return value;
    } else {
      throw new WrongTypeException(arg);
    }
  }

  public String constructHelp(String prog_name, String prog_description) {
    String help = "";
    String usage = "usage: java " + prog_name + " [-h] ";
    for (int i = 0; i < nonpositional_names.size(); i++) {
      String name = nonpositional_names.get(i);
      usage = usage + "[--" + name + " " + name.toUpperCase() + "] ";
    }
    for (int i = 0; i < (positional_names.size() - 1); i++) {
      String name = positional_names.get(i);
      usage = usage + name + " ";
    }
    String _name = positional_names.get(positional_names.size() - 1);
    usage = usage + _name + "\n\n";
    String prog_des = prog_description + "\n\n";
    String positional = "positional arguments:\n";
    String positional_args = "";
    for (int i = 0; i < positional_names.size(); i++) {
      String name = positional_names.get(i);
      Argument arg = args.get(name);
      String type = arg.getType();
      String description = arg.getDescription();
      positional_args = positional_args + " " + name + "\t(" + type + ")\t" + description + "\n";
    }
    String extra_space = "\n";
    String named = "named arguments:\n";
    String help_desc = " -h, --help\tshow this help message and exit";
    String named_args = "";
    if (named_counter > 0) {
      for (int i = 0; i < (nonpositional_names.size() - 1); i++) {
        String name = nonpositional_names.get(i);
        Argument arg = args.get(name);
        String type = arg.getType();
        String description = arg.getDescription();
        String value = arg.getValue();
        named_args =
            named_args
                + " --"
                + name
                + " "
                + name.toUpperCase()
                + "\t \t("
                + type
                + ")\t"
                + description
                + "(default: "
                + value
                + ")\n";
      }
      String __name = nonpositional_names.get(nonpositional_names.size() - 1);
      Argument arg = args.get(__name);
      String type = arg.getType();
      String description = arg.getDescription();
      String value = arg.getValue();
      named_args =
          named_args
              + " --"
              + __name
              + " "
              + __name.toUpperCase()
              + "\t \t("
              + type
              + ")\t"
              + description
              + "(default: "
              + value
              + ")";
    }
    help =
        usage
            + prog_des
            + positional
            + positional_args
            + extra_space
            + named
            + help_desc
            + named_args;
    return help;
  }

  /**
   * Returns the number of arguments passed to ArgumentParser.
   *
   * @return an int value of how many arguments there are to be parsed.
   */
  public int numArgs() {
    return args.size();
  }
}
