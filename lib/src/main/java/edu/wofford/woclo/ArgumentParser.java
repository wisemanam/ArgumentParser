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
  private int positional_counter;

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

  public void addPositional(String name, String type) {
    positional_names.add(name);
    positional_counter++;
  }

  public void addNonPositional(String name, String default_value) {
    args.put(name, default_value);
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
        args.replace(name, value);
        i = i + 2;
      } else {
        String name = positional_names.get(positional);
        String value = arguments[i];
        args.put(name, value);
        i++;
        positional++;
      }
    } if (positional_counter > args.size()) {
        throw new TooFewException(positional_counter, arguments);
    } else if (positional_counter < args.size()) {
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

  /**
   * Returns the number of words in the string passed to ArgumentParser.
   *
   * @return the number of words in the string
   */
  public int numArgs() {
    return args.size();
  }
}
