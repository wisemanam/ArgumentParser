package demos;

import edu.wofford.woclo.*;
import java.util.*;
import java.awt.*;

public class MaximalLayers {
  public String maximalLayers(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addPositional("points", "string", "the data points");
      parser.addNonPositional("sortedX", "boolean", "sort layers by x coordinate", "false", "x");
      parser.addNonPositional("sortedY", "boolean", "sort layers by y coordinate", "false", "y");
      parser.parse(arguments);
      String points = parser.getValue("points");
      boolean sortedX = parser.getValue("sortedX");
      boolean sortedY = parser.getValue("sortedY");
      String[] pointSplit = points.split(",", 0);
      if (pointSplit.length % 2 == 1) return "MaximalLayers error: " + points.substring(points.length() - 1) + " is an unpaired x coordinate";
      int[] point = new int[pointSplit.length];
      //catch the wrong type in points
      for (int i = 0; i < pointSplit.length; i++) {
        point[i] = Integer.parseInt(pointSplit[i]);
      }
      ArrayList<Point> pointList = new ArrayList<>();
      for (int i = 0; i < point.length; i += 2) {
        Point p = new Point(point[i], point[i + 1]);
        pointList.add(p);
      }
      ArrayList<Point> highestX = new ArrayList<>();
      highestX.add(pointList.get(0));
      ArrayList<Point> highestY = new ArrayList<>();
      highestY.add(pointList.get(0));
      for (int i = 1; i < pointList.size(); i++) {
        int k = 0;
        while (pointList.get(i).getX() < highestX.get(k).getX() && k < highestX.size()) {
          k++;
        }
        highestX.add(k, pointList.get(i));
        int j = 0;
        while (pointList.get(i).getY() < highestY.get(j).getY() && j < highestY.size()) {
          j++;
        }
        highestY.add(j, pointList.get(i));
      }
      String layers = buildLayers(highestX, highestY);
      String sorted = sort(layers, sortedX, sortedY);
      return toString(sorted);
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
  
  private String buildLayers(ArrayList<Point> highestX, ArrayList<Point> highestY) {
    Map layers = new HashMap<Integer,ArrayList<Point>>();
    int counter = 1;
    while (!highestY.isEmpty()) {
      Point highestYValuePoint = highestY.get(0);
      ArrayList<Point> layer = new ArrayList<>();
      layer.add(highestYValuePoint);
      while (highestY.get(0).getY() == highestY.get(1).getY()) {
        layer.add(highestY.get(1));
        highestX.remove(highestY.get(0));
        highestY.remove(0);
      }
      highestX.remove(highestY.get(0));
      highestY.remove(0);
      Point highestXofHighestY = layer.get(0);
      for (int i = 0; i < layer.size(); i++) {
        if ((int)layer.get(i).getX() > (int)highestXofHighestY.getX()) {
          highestXofHighestY = layer.get(i);
        }
      }
      if ((int)highestX.get(0).getX() > (int)highestXofHighestY.getX()) {
        layer.add(highestX.get(0));
        highestY.remove(highestX.get(0));
        highestX.remove(0);
      }
      while ((int)highestX.get(0).getX() == (int)highestXofHighestY.getX()) {
        layer.add(highestX.get(0));
        highestY.remove(highestX.get(0));
        highestX.remove(0);
      }
    } 
  }

  private String sort(String layers, boolean sortedX, boolean sortedY) {
    return "";
  }

  private String toString(String layers) {
    return "";
  }

  public static void main(String... args) {}
}
