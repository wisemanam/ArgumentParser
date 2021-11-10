package demos;

import edu.wofford.woclo.*;
import java.util.*;
import java.awt.*;

public class MaximalLayers {
  public String maximialLayers(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addPositional("points", "string", "the data points");
      parser.addNonPositional("sortedX", "boolean", "sort layers by x coordinate", "false");
      parser.addNonPositional("sortedY", "boolean", "sort layers by y coordinate", "false");
      parser.parse(arguments);
      String points = parser.getValue("points");
      Boolean sortedX = parser.getValue("sortedX");
      Boolean sortedY = parser.getValue("sortedY");
      String[] pointSplit = points.split(",", 0);
      if (pointSplit.length % 2 == 1) return "MaximalLayers error: " + points.substring(points.length() - 1) + " is an unpaired x coordinate";
      int[] point = new int[pointSplit.length];
      for (int i = 0; i < pointSplit.length; i++) {
        point[i] = Integer.parseInt(pointSplit[i]);
      }
      ArrayList<Point> pointList = new ArrayList<>();
      for (int i = 0; i < point.length; i += 2) {
        Point p = new Point(point[i], point[i + 1]);
        pointList.add(p);
      }
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
