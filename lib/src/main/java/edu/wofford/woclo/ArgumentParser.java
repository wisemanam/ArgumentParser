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
  private String[] args_list;

  /**
   * ArgumentParser takes an integer and a string and parses the arguments for the user to retreive.
   * If there are fewer or more arguments than expeted, the constructor throws an exception. It will
   * also throw an exception if arguments contains "--help" or "--h".
   *
   * @param expected_args the number of values that the client expects to receive
   * @param arguments a list of the arguments the client would like to parse
   */
  public ArgumentParser(int expected_args, String[] arguments) {
    args_list = arguments.clone();
    int expectedArgs = expected_args;

    if (Arrays.asList(args_list).contains("--help") || Arrays.asList(args_list).contains("-h")) {
      throw new HelpException("Help needed.");
    } else if (expectedArgs > args_list.length) {
      throw new TooFewException(expectedArgs, args_list);
    } else if (expectedArgs < args_list.length) {
      throw new TooManyException(expectedArgs, args_list);
    }
  }

  /**
   * Takes an integer and returns the corresponding string.
   *
   * @param index index of the string to be returned
   * @return string corresponding to the index
   */
  public String getString(int index) {
    return args_list[index];
  }

  /**
   * Takes an integer and returns the corresponding integer value. If the value at the index give
   * cannot be converted to an integer, a WrongTypeException will be thrown.
   *
   * @param index index of the integer to be returned
   * @return integer corresponding to the index
   */
  public int getInt(int index) {
    try {
      int argument = Integer.parseInt(args_list[index]);
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException("Value cannot be converted to integer.");
    }
  }

  /**
   * Takes an integer and returns the corresponding float value. If the value at the index give
   * cannot be converted to a float, a WrongTypeException will be thrown.
   *
   * @param index index of the float to be returned
   * @return float corresponding to the index
   */
  public float getFloat(int index) {
    try {
      float argument = Float.parseFloat(args_list[index]);
      return argument;
    } catch (NumberFormatException e) {
      throw new WrongTypeException("Value cannot be converted to float.");
    }
  }

  /**
   * Returns the number of words in the string passed to ArgumentParser.
   *
   * @return the number of words in the string
   */
  public int numArgs() {
    return args_list.length;
  }
}
