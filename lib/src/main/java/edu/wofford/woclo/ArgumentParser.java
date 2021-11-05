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
  private Map<String, Argument> args;
  private List<String> positional_names;
  private List<String> nonpositional_names;
  private int named_counter;

  /**
   * ArgumentParser parses the arguments for the user to retreive. If there are fewer or more
   * arguments than expeted, the constructor throws an exception. It will also throw an exception if
   * arguments contains "--help" or "--h".
   */
  public ArgumentParser() {
    args = new HashMap<String, Argument>();
    positional_names = new ArrayList<String>();
    nonpositional_names = new ArrayList<String>();
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
    OptionalArgument arg = new OptionalArgument(name, type, description, value);
    nonpositional_names.add(name);
    args.put(name, arg);
    named_counter++;
  }

  /**
   * The parse method takes the arguments given on the command line and sorts them into the expected
   * arguments that are defined using addPositional and addNonPositional
   *
   * @param arguments the arguments entered into the command line
   */
  public void parse(String[] arguments) {
    int expected_positional = positional_names.size();
    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    }
    Queue<String> box_of_garbage = new LinkedList<String>();
    for (int i = 0; i < arguments.length; i++) {
      box_of_garbage.add(arguments[i]);
    }
    int current_positional_name_index = 0;
    while (!box_of_garbage.isEmpty()) {
      if (box_of_garbage.peek().startsWith("--")) {
        String name = box_of_garbage.poll().substring(2);
        if (box_of_garbage.isEmpty()) {
          throw new NoValueException(name);
        }
        String value = box_of_garbage.poll();
        Argument arg = args.get(name);
        if (arg == null) {
          throw new ArgumentNameNotSpecifiedException(name);
        } else {
          arg.setValue(value);
          if (arg.getType().equals("integer")) {
            try {
              Integer.parseInt(value);
            } catch (NumberFormatException e) {
              throw new WrongTypeException(value);
            }
          } else if (arg.getType().equals("float")) {
            try {
              Float.parseFloat(value);
            } catch (NumberFormatException e) {
              throw new WrongTypeException(value);
            }
          }
        }
      } else {
        String value = box_of_garbage.poll();
        try {
          String name = positional_names.get(current_positional_name_index);
          Argument arg = args.get(name);
          arg.setValue(value);
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
    Argument arg = args.get(arg_name);
    return arg.getValue();
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
}
