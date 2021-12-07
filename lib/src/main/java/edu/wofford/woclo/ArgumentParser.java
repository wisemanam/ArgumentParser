package edu.wofford.woclo;

import java.io.*;
import java.util.*;

/**
 * The ArgumentParser class takes the arguments entered into the command line and parses each
 * argument as strings. The class includes methods for examining the number of arguments given to
 * the constructor as well as a method for returning the argument at a certain index.
 * ArgumentParsers are constant and changing the list of arguments that was given to the constructor
 * will not change the contents of the ArgumentParser.
 */
public class ArgumentParser {
  private Map<String, Argument> args;
  private Map<String, String> short_args;
  private List<String> positional_names;
  private List<String> nonpositional_names;
  private List<String> short_name_names;

  /**
   * ArgumentParser parses the arguments for the user to retreive. If there are fewer or more
   * arguments than expeted, the constructor throws an exception. It will also throw an exception if
   * arguments contains "--help" or "--h".
   */
  public ArgumentParser() {
    args = new HashMap<String, Argument>();
    short_args = new HashMap<String, String>();
    positional_names = new ArrayList<String>();
    nonpositional_names = new ArrayList<String>();
    short_name_names = new ArrayList<String>();
  }

  /**
   * The addPositional method adds an expected argument to ArgumentParser that will not be named
   * when arguments are given on the command line.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   */
  public void addPositional(String name, String type, String description) {
    Argument arg = new Argument(name, type, description);
    positional_names.add(name);
    args.put(name, arg);
  }

  /**
   * This performs the same actions as the first addPositional method but takes an additional
   * paremeter that allows the client to add a set of accepted values.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   * @param accepted_values String list of accepted values for this argument
   */
  public void addPositional(
      String name, String type, String description, String[] accepted_values) {
    Argument arg = new Argument(name, type, description, accepted_values);
    positional_names.add(name);
    args.put(name, arg);
  }

  /**
   * The addNonPositional method adds an expected argument to ArgumentParser that when given on the
   * command line should be named.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   * @param value the default value that the argument is given in the case that it is not one of the
   *     arguments passed into the command line
   */
  public void addNonPositional(String name, String type, String description, String value) {
    Argument arg = new OptionalArgument(name, type, description, value);
    nonpositional_names.add(name);
    args.put(name, arg);
  }

  /**
   * This performs the same actions as the first addNonPositional method but takes an additional
   * paremeter that allows the client to add a short version of the name.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param short_name the short name that can be used on the command line or used to access this
   *     argument
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   * @param value the default value that the argument is given in the case that it is not one of the
   *     arguments passed into the command line
   */
  public void addNonPositional(
      String name, String short_name, String type, String description, String value) {
    Argument arg = new OptionalArgument(name, short_name, type, description, value);
    nonpositional_names.add(name);
    short_name_names.add(short_name);
    args.put(name, arg);
    short_args.put(short_name, name);
  }

  /**
   * This method adds a non-positional argument using the name, type, description, default value,
   * and list of accepted values.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   * @param value the default value that the argument is given in the case that it is not one of the
   *     arguments passed into the command line
   * @param accepted_values list of values accepted for this argument
   */
  public void addNonPositional(
      String name, String type, String description, String value, String[] accepted_values) {
    Argument arg = new OptionalArgument(name, type, description, value, accepted_values);
    nonpositional_names.add(name);
    args.put(name, arg);
  }
  /**
   * This method adds a non-positional argument using the name, type, description, default value,
   * and list of accepted values.
   *
   * @param name the name that will be used to retrieve this argument from ArgumentParser
   * @param short_name the short name that can be used on the command line or used to access this
   *     argument
   * @param type the type that the value will be when it is retreived from ArgumentParser
   * @param description the description of the argument used in the help message
   * @param value the default value that the argument is given in the case that it is not one of the
   *     arguments passed into the command line
   * @param accepted_values list of values accepted for this argument
   */
  public void addNonPositional(
      String name,
      String short_name,
      String type,
      String description,
      String value,
      String[] accepted_values) {
    Argument arg =
        new OptionalArgument(name, short_name, type, description, value, accepted_values);
    nonpositional_names.add(name);
    short_name_names.add(short_name);
    args.put(name, arg);
    short_args.put(short_name, name);
  }

  /**
   * The parse method takes the arguments given on the command line and sorts them into the expected
   * arguments that are defined using addPositional and addNonPositional
   *
   * @param arguments the arguments entered into the command line
   */
  public void parse(String[] arguments) {
    int expected_positional = positional_names.size();
    // check_digits is used to double check and make sure we don't have
    // a negative number that is meant to be a value rather than an argument name
    String[] check_digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};
    // check and see if we need to throw the help exception
    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    }
    // put all of the arguments into a queue
    Queue<String> box_of_garbage = new LinkedList<String>();
    for (int i = 0; i < arguments.length; i++) {
      box_of_garbage.add(arguments[i]);
    }
    // start the current positional at index 0
    int current_positional_name_index = 0;
    // while there is still stuff in the queue
    // meaning we have not gotten through everything given on the command line
    while (!box_of_garbage.isEmpty()) {
      // LONG NAME ARGUMENTS
      // create a long_name_arg_parser
      // add check mutally exclusive and required specification
      if (box_of_garbage.peek().startsWith("--")) {
        String name = box_of_garbage.poll().substring(2);
        Argument a = args.get(name);
        // if we don't know what that name is, throw name not specified exception
        if (a == null) {
          throw new ArgumentNameNotSpecifiedException(name);
        } else {
          // the argument exists, so get the type
          String type = a.getType();
          // if the queue is empty after this, then the argument has to be a boolean value
          if (box_of_garbage.isEmpty()) {
            // if it isnt, throw that there isn't a value
            if (!type.equals("boolean")) {
              throw new NoValueException(name);
              // if there is, the set the value to true
            } else {
              a.setValue("true");
            }

            // the queue isn't empty
          } else {
            if (type.equals("boolean")) {
              a.setValue("true");
            } else {
              String value = box_of_garbage.poll();
              if (!a.hasAcceptedValues()) {
                if (a.getType().equals("integer")) {
                  try {
                    Integer.parseInt(value);
                    a.setValue(value);
                  } catch (NumberFormatException e) {
                    throw new WrongTypeException(value, a.getType());
                  }
                } else if (a.getType().equals("float")) {
                  try {
                    Float.parseFloat(value);
                    a.setValue(value);
                  } catch (NumberFormatException e) {
                    throw new WrongTypeException(value, a.getType());
                  }
                } else {
                  a.setValue(value);
                }
              } else {
                if (a.getType().equals("integer") && a.isAcceptedValue(value)) {
                  try {
                    Integer.parseInt(value);
                    a.setValue(value);
                  } catch (NumberFormatException e) {
                    throw new WrongTypeException(value, a.getType());
                  }
                } else if (a.getType().equals("float") && a.isAcceptedValue(value)) {
                  try {
                    Float.parseFloat(value);
                    a.setValue(value);
                  } catch (NumberFormatException e) {
                    throw new WrongTypeException(value, a.getType());
                  }
                } else if (a.getType().equals("string") && a.isAcceptedValue(value)) {
                  a.setValue(value);
                } else {
                  throw new ValueNotAcceptedException(value, name);
                }
              }
            }
          }
        }
        // SHORT NAME ARGUMENTS
        // create a stack_name_arg_parser
        // add check mutally exclusive and required specification
      } else if (box_of_garbage.peek().startsWith("-")
          && !Arrays.asList(check_digits)
              .contains(Character.toString(box_of_garbage.peek().charAt(1)))) {
        String short_name_argument = box_of_garbage.poll();
        // if the short name is stacked
        if (short_name_argument.length() > 2) {
          for (int i = 1; i < short_name_argument.length(); i++) {
            String name = Character.toString(short_name_argument.charAt(i));
            String long_name = short_args.get(name);
            Argument a = args.get(long_name);
            if (a == null) {
              throw new ArgumentNameNotSpecifiedException(name);
            } else {
              String type = a.getType();
              if (!type.equals("boolean")) {
                throw new WrongTypeException(name, a.getType());
              } else {
                a.setValue("true");
              }
            }
          }
        } else {
          // if short args are not stacked
          String name = Character.toString(short_name_argument.charAt(1));
          String long_name = short_args.get(name);
          // create a short_name_arg_parser
          Argument a = args.get(long_name);
          if (a == null) {
            throw new ArgumentNameNotSpecifiedException(name);
          } else {
            String type = a.getType();
            if (!type.equals("boolean")) {
              if (box_of_garbage.isEmpty()) {
                throw new NoValueException(name);
              } else {
                String value = box_of_garbage.poll();
                if (!a.hasAcceptedValues()) {
                  if (a.getType().equals("integer")) {
                    try {
                      Integer.parseInt(value);
                      a.setValue(value);
                    } catch (NumberFormatException e) {
                      throw new WrongTypeException(value, a.getType());
                    }
                  } else if (a.getType().equals("float")) {
                    try {
                      Float.parseFloat(value);
                      a.setValue(value);
                    } catch (NumberFormatException e) {
                      throw new WrongTypeException(value, a.getType());
                    }
                  } else {
                    a.setValue(value);
                  }
                } else {
                  if (a.getType().equals("integer") && a.isAcceptedValue(value)) {
                    try {
                      Integer.parseInt(value);
                      a.setValue(value);
                    } catch (NumberFormatException e) {
                      throw new WrongTypeException(value, a.getType());
                    }
                  } else if (a.getType().equals("float") && a.isAcceptedValue(value)) {
                    try {
                      Float.parseFloat(value);
                      a.setValue(value);
                    } catch (NumberFormatException e) {
                      throw new WrongTypeException(value, a.getType());
                    }
                  } else if (!a.isAcceptedValue(value)) {
                    throw new ValueNotAcceptedException(value, long_name);
                  } else {
                    a.setValue(value);
                  }
                }
              }
            } else {
              a.setValue("true");
            }
          }
        }
        // POSITIONAL ARGUMENTS
        // create a positional_argument_parser
      } else {
        String value = box_of_garbage.poll();
        try {
          String name = positional_names.get(current_positional_name_index);
          Argument a = args.get(name);
          if (!a.hasAcceptedValues()) {
            if (a.getType().equals("integer")) {
              try {
                Integer.parseInt(value);
                a.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, a.getType());
              }
            } else if (a.getType().equals("float")) {
              try {
                Float.parseFloat(value);
                a.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, a.getType());
              }
            } else {
              a.setValue(value);
            }
          } else {
            if (a.getType().equals("integer") && a.isAcceptedValue(value)) {
              try {
                Integer.parseInt(value);
                a.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, a.getType());
              }
            } else if (a.getType().equals("float") && a.isAcceptedValue(value)) {
              try {
                Float.parseFloat(value);
                a.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, a.getType());
              }
            } else if (!a.isAcceptedValue(value)) {
              throw new ValueNotAcceptedException(value, name);
            } else {
              a.setValue(value);
            }
          }
          current_positional_name_index++;
        } catch (IndexOutOfBoundsException e) {
          throw new TooManyException(value);
        }
      }
    }
    if (expected_positional > current_positional_name_index) {
      throw new TooFewException(current_positional_name_index, positional_names);
    }
  }

  /**
   * Takes a string and returns the corresponding value in the type that is specified in either
   * addPositional or addNonPositional
   *
   * @param arg_name name of the argument wanted
   * @param <T> the type of the argument being returned
   * @return value corresponding to the name
   */
  public <T> T getValue(String arg_name) {
    String long_name = short_args.get(arg_name);
    if (long_name == null) {
      Argument arg = args.get(arg_name);
      return arg.getValue();
    } else {
      Argument arg = args.get(long_name);
      return arg.getValue();
    }
  }

  /**
   * Returns the Argument corresponding to the name given in addPositional or addNonPositional
   *
   * @param name the name of the value being received
   * @return Argument associated with the name
   */
  public Argument getArgument(String name) {
    return args.get(name);
  }

  /**
   * Returns the number of arguments passed to ArgumentParser.
   *
   * @return an int value of how many arguments there are to be parsed.
   */
  public int numArgs() {
    return args.size();
  }

  public List<String> getPositionalNames() {
    List<String> cloned = new ArrayList<>();
    for (int i = 0; i < positional_names.size(); i++) {
      cloned.add(positional_names.get(i));
    }
    return cloned;
  }

  public List<String> getNonPositionalNames() {
    List<String> cloned = new ArrayList<>();
    for (int i = 0; i < nonpositional_names.size(); i++) {
      cloned.add(nonpositional_names.get(i));
    }
    return cloned;
  }

  public static void main(String[] args) {}
}
