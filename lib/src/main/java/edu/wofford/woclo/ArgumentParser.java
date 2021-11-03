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
    positional_counter = 0;
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
    named_counter++;
  }
   
  public void parse(String[] arguments) {
    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    }
    int i = 0;
    int positional = 0;
    while (i < arguments.length) {
      if (arguments[i].startsWith("--")) {
        String name = arguments[i].substring(2, arguments[i].length());
        String value = arguments[i + 1];
        Argument arg = args.get(name);
        arg.setValue(value);
        try {
          args.replace(name, arg);
        } catch (ArgumentNameNotSpecifiedException e) {
          System.out.println(e.getArgName() + " does not match expected argument name");
        }
        i = i + 2;
      } else {
        String name = positional_names.get(positional);
        String value = arguments[i];
        Argument arg = args.get(name);
        arg.setValue(value);
        try {
          args.replace(name, arg);
        } catch (ArgumentNameNotSpecifiedException e) {
          System.out.println(e.getArgName() + " does not match expected argument name");
        }
        i++;
        positional++;
      }
    } if (positional_counter > positional) {
        throw new TooFewException(positional_counter, arguments);
    } else if (positional_counter < positional || arguments.length > positional_counter + named_counter) {
        throw new TooManyException(positional_counter, arguments);
    }
  }

  /**
   * Takes a string and returns the corresponding string.
   *
   * @param arg_name name of the argument wanted
   * @return string corresponding to the name
   */

  public String getString(String arg_name) {
    try {
      String argument = args.get(arg_name);
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(arg_name));
    }
  }

  /**
   * Takes a string and returns the corresponding integer value. If the value at the index give
   * cannot be converted to an integer, a WrongTypeException will be thrown.
   *
   * @param arg_name name of the argument wanted
   * @return integer corresponding to the name
   */

  public int getInt(String arg_name) {
    try {
      int argument = Integer.parseInt(args.get(arg_name));
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(arg_name));
    }
  }

  /**
   * Takes an integer and returns the corresponding float value. If the value at the index give
   * cannot be converted to a float, a WrongTypeException will be thrown.
   *
   * @param arg_name name of the argument wanted
   * @return float corresponding to the name
   */

  public float getFloat(String arg_name) {
    try {
      float argument = Float.parseFloat(args.get(arg_name));
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(arg_name));
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
    usage = usage + _name + "\n";
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
        named_args = named_args + " --" + name + " " + name.toUpperCase() + "\t(" + type + ")\t" + description + "(default: " + value + ")\n";
      }
      String __name = nonpositional_names.get(nonpositional_names.size() - 1);
      Argument arg = args.get(__name);
      String type = arg.getType();
      String description = arg.getDescription();
      String value = arg.getValue();
      named_args = named_args + " --" + __name + " " + __name.toUpperCase() + "\t(" + type + ")\t" + description + "(default: " + value + ")";
    }
    help = usage + prog_des + positional + positional_args + extra_space + named + help_desc + named_args;
    return help;
  }

  /**
   * Returns the number of words in the string passed to ArgumentParser.
   *
   * @return the number of words in the string
   */
  public int numArgs() {
    return args.size();
  }
}
