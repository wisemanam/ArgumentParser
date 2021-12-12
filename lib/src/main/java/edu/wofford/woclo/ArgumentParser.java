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
  private List<String> required_names;
  private List<List<String>> mutually_exclusive;
  private List<String> trashcan;

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
    required_names = new ArrayList<String>();
    mutually_exclusive = new ArrayList<List<String>>();
    trashcan = new ArrayList<String>();
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

  public void addNonPositional(String name, String type, String description, boolean required) {
    Argument arg = new OptionalArgument(name, type, description, required);
    nonpositional_names.add(name);
    args.put(name, arg);
    required_names.add(name);
  }

  public void addNonPositional(
      String name, String shortname, String type, String description, boolean required) {
    Argument arg = new OptionalArgument(name, shortname, type, description, required);
    nonpositional_names.add(name);
    short_name_names.add(shortname);
    args.put(name, arg);
    short_args.put(shortname, name);
    required_names.add(name);
  }

  public void addNonPositional(
      String name, String type, String description, String[] accepted, boolean required) {
    Argument arg = new OptionalArgument(name, type, description, accepted, required);
    nonpositional_names.add(name);
    args.put(name, arg);
    required_names.add(name);
  }

  public void addNonPositional(
      String name,
      String shortname,
      String type,
      String description,
      String[] accepted,
      boolean required) {
    Argument arg = new OptionalArgument(name, shortname, type, description, accepted, required);
    nonpositional_names.add(name);
    short_name_names.add(shortname);
    args.put(name, arg);
    short_args.put(shortname, name);
    required_names.add(name);
  }

  public void addMutuallyExclusiveGroup(List<String> mutuallyExclusive) {
    if (mutuallyExclusive.size() > 1) {
      mutually_exclusive.add(mutuallyExclusive);
    }
  }

  public boolean mutuallyExclusiveError(List<String> mutexc, String[] arguments) {
    int numContains = 0;
    String[] check_digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};
    Queue<String> args = new LinkedList<String>();
    for (int i = 0; i < arguments.length; i++) {
      args.add(arguments[i]);
    }
    while (!args.isEmpty()) {
      if (args.peek().startsWith("--")) {
        String name = args.poll().substring(2);
        if (mutexc.contains(name)) {
          numContains++;
        }
      } else if (args.peek().startsWith("-")
          && !Arrays.asList(check_digits).contains(Character.toString(args.peek().charAt(1)))) {
        String short_name = args.poll().substring(1);
        String name = short_args.get(short_name);
        if (mutexc.contains(name)) {
          numContains++;
        }
      } else {
        String extra = args.poll();
        trashcan.add(extra);
      }
    }
    if (numContains > 1) {
      return true;
    } else {
      return false;
    }
  }

  public void positionalFound(String name, String value) {
    Argument arg = args.get(name);
    if (!arg.hasAcceptedValues()) {
      if (arg.getType().equals("integer")) {
        try {
          Integer.parseInt(value);
          arg.setValue(value);
        } catch (NumberFormatException e) {
          throw new WrongTypeException(value, arg.getType());
        }
      } else if (arg.getType().equals("float")) {
        try {
          Float.parseFloat(value);
          arg.setValue(value);
        } catch (NumberFormatException e) {
          throw new WrongTypeException(value, arg.getType());
        }
      } else {
        arg.setValue(value);
      }
    } else {
      if (arg.getType().equals("integer") && arg.isAcceptedValue(value)) {
        try {
          Integer.parseInt(value);
          arg.setValue(value);
        } catch (NumberFormatException e) {
          throw new WrongTypeException(value, arg.getType());
        }
      } else if (arg.getType().equals("float") && arg.isAcceptedValue(value)) {
        try {
          Float.parseFloat(value);
          arg.setValue(value);
        } catch (NumberFormatException e) {
          throw new WrongTypeException(value, arg.getType());
        }
      } else if (!arg.isAcceptedValue(value)) {
        throw new ValueNotAcceptedException(value, name);
      } else {
        arg.setValue(value);
      }
    }
  }

  public int shortnameStackedFound(String short_name, Queue<String> arguments, int num_required) {
    String name = short_args.get(short_name);
    Argument arg = args.get(name);
    if (arg == null) {
      throw new ArgumentNameNotSpecifiedException(short_name);
    } else {
      OptionalArgument o = (OptionalArgument) arg;
      if (o.isRequired()) {
        num_required++;
      }
      String type = arg.getType();
      if (!type.equals("boolean")) {
        throw new WrongTypeException(name, arg.getType());
      } else {
        arg.setValue("true");
      }
    }
    return num_required;
  }

  public int shortnameFound(String short_name, Queue<String> arguments, int num_required) {
    String name = short_args.get(short_name);
    Argument arg = args.get(name);
    if (arg == null) {
      throw new ArgumentNameNotSpecifiedException(short_name);
    } else {
      OptionalArgument o = (OptionalArgument) arg;
      if (o.isRequired()) {
        num_required++;
      }
      String type = arg.getType();
      if (!type.equals("boolean")) {
        if (arguments.isEmpty()) {
          throw new NoValueException(name);
        } else {
          String value = arguments.poll();
          if (!arg.hasAcceptedValues()) {
            if (arg.getType().equals("integer")) {
              try {
                Integer.parseInt(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (arg.getType().equals("float")) {
              try {
                Float.parseFloat(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else {
              arg.setValue(value);
            }
          } else {
            if (arg.getType().equals("integer") && arg.isAcceptedValue(value)) {
              try {
                Integer.parseInt(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (arg.getType().equals("float") && arg.isAcceptedValue(value)) {
              try {
                Float.parseFloat(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (!arg.isAcceptedValue(value)) {
              throw new ValueNotAcceptedException(value, name);
            } else {
              arg.setValue(value);
            }
          }
        }
      } else {
        arg.setValue("true");
      }
    }
    return num_required;
  }

  public int nonpositionalFound(String name, Queue<String> arguments, int num_required) {
    Argument arg = args.get(name);
    if (arg == null) {
      throw new ArgumentNameNotSpecifiedException(name);
    } else {
      OptionalArgument o = (OptionalArgument) arg;
      if (o.isRequired()) {
        num_required++;
      }
      // the argument exists, so get the type
      String type = arg.getType();
      // if the queue is empty after this, then the argument has to be a boolean value
      if (arguments.isEmpty()) {
        // if it isnt, throw that there isn't a value
        if (!type.equals("boolean")) {
          throw new NoValueException(name);
          // if there is, the set the value to true
        } else {
          arg.setValue("true");
        }

        // the queue isn't empty
      } else {
        if (type.equals("boolean")) {
          arg.setValue("true");
        } else {
          String value = arguments.poll();
          if (!arg.hasAcceptedValues()) {
            if (arg.getType().equals("integer")) {
              try {
                Integer.parseInt(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (arg.getType().equals("float")) {
              try {
                Float.parseFloat(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else {
              arg.setValue(value);
            }
          } else {
            if (arg.getType().equals("integer") && arg.isAcceptedValue(value)) {
              try {
                Integer.parseInt(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (arg.getType().equals("float") && arg.isAcceptedValue(value)) {
              try {
                Float.parseFloat(value);
                arg.setValue(value);
              } catch (NumberFormatException e) {
                throw new WrongTypeException(value, arg.getType());
              }
            } else if (arg.getType().equals("string") && arg.isAcceptedValue(value)) {
              arg.setValue(value);
            } else {
              throw new ValueNotAcceptedException(value, name);
            }
          }
        }
      }
    }
    return num_required;
  }

  /**
   * The parse method takes the arguments given on the command line and sorts them into the expected
   * arguments that are defined using addPositional and addNonPositional
   *
   * @param arguments the arguments entered into the command line
   */
  public void parse(String[] arguments) {
    int expected_positional = positional_names.size();
    int expected_required = required_names.size();
    String[] check_digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};

    if (Arrays.asList(arguments).contains("--help") || Arrays.asList(arguments).contains("-h")) {
      throw new HelpException("Help needed.");
    }

    if (mutually_exclusive.size() > 0) {
      for (int i = 0; i < mutually_exclusive.size(); i++) {
        if (mutuallyExclusiveError(mutually_exclusive.get(i), arguments)) {
          throw new MutualExclusionException(mutually_exclusive.get(i));
        }
      }
    }

    Queue<String> box_of_garbage = new LinkedList<String>();
    for (int i = 0; i < arguments.length; i++) {
      box_of_garbage.add(arguments[i]);
    }

    // start the current positional at index 0
    int current_positional_name_index = 0;
    // set number of required arguments we have encounterd to 0
    int num_required = 0;

    while (!box_of_garbage.isEmpty()) {

      // LONG NAME ARGUMENTS
      // create a long_name_arg_parser
      // add check mutally exclusive and required specification
      if (box_of_garbage.peek().startsWith("--")) {
        String name = box_of_garbage.poll().substring(2);
        num_required = nonpositionalFound(name, box_of_garbage, num_required);

        // SHORT NAME ARGUMENTS
        // create a stack_name_arg_parser
        // add check mutally exclusive and required specification
      } else if (box_of_garbage.peek().startsWith("-")
          && !Arrays.asList(check_digits)
              .contains(Character.toString(box_of_garbage.peek().charAt(1)))) {
        String short_name_argument = box_of_garbage.poll();

        if (short_name_argument.length() > 2) {
          // if the short name is stacked
          for (int i = 1; i < short_name_argument.length(); i++) {
            String short_name = Character.toString(short_name_argument.charAt(i));
            num_required = shortnameStackedFound(short_name, box_of_garbage, num_required);
          }

        } else {
          // if short args are not stacked
          String short_name = Character.toString(short_name_argument.charAt(1));
          num_required = shortnameFound(short_name, box_of_garbage, num_required);
        }

        // POSITIONAL ARGUMENTS
      } else {
        String value = box_of_garbage.poll();
        try {
          String name = positional_names.get(current_positional_name_index);
          positionalFound(name, value);
          current_positional_name_index++;
        } catch (IndexOutOfBoundsException e) {
          throw new TooManyException(value);
        }
      }
    }
    if (expected_positional > current_positional_name_index) {
      throw new TooFewException(current_positional_name_index, positional_names);
    }
    if (expected_required > num_required) {
      throw new RequiredArgumentMissingException(required_names, arguments, short_args);
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

  public List<String> getRequiredNames() {
    return new ArrayList<String>(required_names);
  }

  public List<List<String>> getMutuallyExclusive() {
    return new ArrayList<List<String>>(mutually_exclusive);
  }
}
