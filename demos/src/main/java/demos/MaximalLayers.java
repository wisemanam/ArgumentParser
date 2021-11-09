package demos;

import edu.wofford.woclo.*;

public class MaximalLayers {
  public String maximialLayers(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addNonPositional("sortedX", "boolean", "sort layers by x coordinate", "false");
      parser.addNonPositional("sortedY", "boolean", "sort layers by y coordinate", "false");
    } catch (HelpException e){
      return "help";
    } catch (WrongTypeException e) {
      String wrongValue = e.getWrongValue();
      return "MaximalLayers error: the value" + wrongValue + "is not of type integer";
    } catch (TooManyException e) {
      String firstExtra = e.getFirstExtra();
      return "MaximalLayers error: the value" + firstExtra + "matches no argument";
    } catch (TooFewException e) {
      String next = e.getNextExpectedName();
      return "MaximalLayers error: the argument" + next + "is required";
    }
  }

  public static void main(String... args) {}
}
