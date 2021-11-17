package demos;

import edu.wofford.woclo.*;

public class HeatedField {

  public String heatedField(String[] arguments) {
    ArgumentParser argParse = new ArgumentParser();
    String[] accepted = {"1", "2", "3", "4", "5", "6", "7", "8"};
    argParse.addPositional("north", "float", "the temperature of the north edge");
    argParse.addPositional("south", "float", "the temperature of the south edge");
    argParse.addPositional("east", "float", "the temperature of the east edge");
    argParse.addPositional("west", "float", "the temperature of the west edge");
    argParse.addPositional(
        "x", "integer", "the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}", accepted);
    argParse.addPositional(
        "y", "integer", "the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}", accepted);
    argParse.addNonPositional(
        "temperature",
        "t",
        "float",
        "the initial temperature of internal cells (default: 32.0)",
        "32.0");
    argParse.addNonPositional(
        "minutes", "m", "integer", "the number of minutes to apply heating (default: 10)", "10");
    try {
      argParse.parse(arguments);
      float north = argParse.getValue("north");
      float south = argParse.getValue("south");
      float east = argParse.getValue("east");
      float west = argParse.getValue("west");
      int x = argParse.getValue("x");
      int y = argParse.getValue("y");
      float temperature = argParse.getValue("temperature");
      int minutes = argParse.getValue("minutes");
      if (minutes < 0) {
        return "HeatedField error: minutes must be positive";
      }
      float[][] field = new float[10][10];
      field[0][0] = (float) 1.0;
      field[0][9] = (float) 1.0;
      field[9][0] = (float) 1.0;
      field[9][9] = (float) 1.0;
      for (int i = 1; i < 9; i++) {
        for (int j = 1; j < 9; j++) {
          field[0][j] = north;
          field[9][j] = south;
          field[i][9] = east;
          field[i][0] = west;
          field[i][j] = temperature;
        }
      }
      int time = 0;
      while (time < minutes) {
        // System.out.println("time = " + time);
        // System.out.println("field currently looks like: ");
        // System.out.println(toString(field));
        float[][] temp = new float[10][10];
        temp[0][0] = (float) 1.0;
        temp[0][9] = (float) 1.0;
        temp[9][0] = (float) 1.0;
        temp[9][9] = (float) 1.0;
        for (int i = 1; i < 9; i++) {
          for (int j = 1; j < 9; j++) {
            temp[0][j] = north;
            temp[9][j] = south;
            temp[i][9] = east;
            temp[i][0] = west;
            temp[i][j] = temperature;
          }
        }
        for (int i = 1; i < 9; i++) {
          for (int j = 1; j < 9; j++) {
            temp[i][j] =
                (field[i - 1][j] + field[i + 1][j] + field[i][j - 1] + field[i][j + 1]) / 4;
          }
        }
        // System.out.println(toString(temp));
        field = temp;
        // System.out.println("field");
        // System.out.println(toString(field));
        time++;
      }
      float end_temperature = field[x][y];
      String end_temp_str = Float.toString(end_temperature);
      return "cell ("
          + Integer.toString(x)
          + ", "
          + Integer.toString(y)
          + ") is "
          + end_temp_str
          + " degrees Fahrenheit after "
          + minutes
          + " minutes";
    } catch (TooFewException e) {
      String missing = e.getNextExpectedName();
      return "HeatedField error: the argument " + missing + " is required";
    } catch (TooManyException e) {
      String tooMany = e.getFirstExtra();
      return "HeatedField error: the value " + tooMany + " matches no argument";
    } catch (ValueNotAcceptedException e) {
      String wrongValue = e.getUnacceptedValue();
      return "HeatedField error: x value "
          + wrongValue
          + " is not a member of [1, 2, 3, 4, 5, 6, 7, 8]";
    } catch (WrongTypeException e) {
      return "HeatedField error: the value bob is not of type float";
    }
  }

  public static void main(String... args) {
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    System.out.print(result);
  }
}
