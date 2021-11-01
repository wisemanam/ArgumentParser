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
  private HashMap<String, String> args;
  private String[] expected_names;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   * If there are fewer or more arguments than expeted, the constructor throws an exception. It will
   * also throw an exception if arguments contains "--help" or "--h".
   *
   * @param expected_args the number of values that the client expects to receive
   * @param arguments a list of the arguments the client would like to parse
   */
  public ArgumentParser(String[] arguments, String[] expected_names) {
    args = new HashMap<String, String>();
    int expectedArgs = expected_names.length;
    this.expected_names = expected_names;
    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    } else if (expectedArgs > arguments.length) {
      throw new TooFewException(expectedArgs, arguments);
    } else if (expectedArgs < arguments.length) {
      throw new TooManyException(expectedArgs, arguments);
    }
    int i = 0;
    int numDefaults = 0;
    while (i < arguments.length) {
      if (arguments[i].contains("--")) {
        String name = arguments[i].substring(2, arguments[i].length());
        args.put(name, arguments[i + 1]);
        i = i + 2;
        int index = Arrays.asList(arguments).indexOf(name);
        Arrays.asList(expected_names).remove(index);
      } else {
        String name = expected_names[numDefaults];
        args.put(name, arguments[i]);
        i++;
        numDefaults++;
      }
    }
  }
  
  /**
   * Takes an array of strings with the names of the expected arguments.
   *
   * @param expected_arg_names array of strings that contains the names of expected arguments.
   */
  public void setExpectedArgNames(String[] expected_arg_names) {
    this.expected_names = expected_names;
  }

  /**
   * Takes an integer representing an index value and returns the name of the expected argument at that index
   *
   * @param index index of the string to be returned.
   * @return string corresponding to the index
   */
  public String getExpectedArgValue(int index) {
    return expected_names[index];
  }

  public void setDefault(String name, String value) {
    String val = args.replace(name, value);
  }

  /**
   * Takes an integer and returns the corresponding string.
   *
   * @param position index of the string to be returned
   * @return string corresponding to the index
   */
  public String getString(int position) {
    String name = "__default" + Integer.toString(position);
    return args.get(name);
  }

  // this is for when they give us the name of a non-positional argument
  public String getString(String arg_name) {
    try {
      String argument = args.get(arg_name);
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(arg_name));
    }
  }

  /**
   * Takes an integer and returns the corresponding integer value. If the value at the index give
   * cannot be converted to an integer, a WrongTypeException will be thrown.
   *
   * @param index index of the integer to be returned
   * @return integer corresponding to the index
   */
  public int getInt(int position) {
    String name = "__default" + Integer.toString(position);
    try {
      int argument = Integer.parseInt(args.get(name));
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(name));
    }
  }

  // this is for when they give us the name of a non-positional argument
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
   * @param index index of the float to be returned
   * @return float corresponding to the index
   */
  public float getFloat(int position) {
    String name = "__default" + Integer.toString(position);
    try {
      float argument = Float.parseFloat(args.get(name));
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException(args.get(name));
    }
  }

  // this is for when they give us the name of a non-positional argument
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
