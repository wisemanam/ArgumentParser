package demos;

import edu.wofford.woclo.*;
import java.awt.*;
import java.util.*;

public class MaximalLayers {
  public String maximalLayers(String[] arguments) {
    ArgumentParser parser = new ArgumentParser();
    try {
      parser.addPositional("points", "string", "the data points");
      parser.addNonPositional("sortedX", "boolean", "sort layers by x coordinate", "false");
      parser.addNonPositional("sortedY", "boolean", "sort layers by y coordinate", "false");
      parser.parse(arguments);
      String points = parser.getValue("points");
      boolean sortedX = parser.getValue("sortedX");
      boolean sortedY = parser.getValue("sortedY");
      String[] pointSplit = points.split(",", 0);
      if (pointSplit.length % 2 == 1)
        return "MaximalLayers error: "
            + pointSplit[pointSplit.length - 1]
            + " is an unpaired x coordinate";
      int[] point = new int[pointSplit.length];

      for (int i = 0; i < pointSplit.length; i++) {
        try {
          point[i] = Integer.parseInt(pointSplit[i]);
        } catch (NumberFormatException e) {
          return "MaximalLayers error: the value " + pointSplit[i] + " is not of type integer";
        }
      }
      ArrayList<Point> pointList = new ArrayList<>();
      for (int i = 0; i < point.length; i += 2) {
        Point p = new Point(point[i], point[i + 1]);
        pointList.add(p);
      }
      Map<Integer, ArrayList<Point>> layers = findMaximalLayers(pointList);
      return toString(layers, pointList, sortedX, sortedY);
    } catch (HelpException e) {
      return "usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points\n\nSort the points into layers.\n\npositional arguments:\n points      (string)      the data points\n\nnamed arguments:\n -h, --help  show this help message and exit\n --sortedX   sort layers by x coordinate\n --sortedY   sort layers by y coordinate";
    } catch (TooFewException e) {
      return "MaximalLayers error: the argument " + e.getNextExpectedName() + " is required";
    } catch (TooManyException e) {
      return "MaximalLayers error: the value " + e.getFirstExtra() + " matches no argument";
    }
  }

  private ArrayList<Point> sortToOriginal(
      ArrayList<Point> pointsToSort, ArrayList<Point> original) {
    ArrayList<Point> sorted = new ArrayList<>();
    while (!pointsToSort.isEmpty()) {
      Point min = pointsToSort.get(0);
      for (int i = 0; i < pointsToSort.size(); i++) {
        int index = original.indexOf(pointsToSort.get(i));
        if (index < original.indexOf(min)) {
          min = pointsToSort.get(i);
        }
      }
      sorted.add(min);
      pointsToSort.remove(min);
    }
    return sorted;
  }

  private ArrayList<Point> sortPoints(ArrayList<Point> points, boolean sortedX, boolean sortedY) {

    ArrayList<Point> points_copy = new ArrayList<Point>(points);

    if (sortedY) {
      points_copy.sort(Comparator.comparing(Point::getY));
    }
    if (sortedX) {
      points_copy.sort(Comparator.comparing(Point::getX));
    }
    return points_copy;
  }

  public Map<Integer, ArrayList<Point>> findMaximalLayers(ArrayList<Point> points) {
    Map<Integer, ArrayList<Point>> layers = new HashMap<Integer, ArrayList<Point>>();
    ArrayList<Point> sortedPoints = sortPoints(points, true, true); // sort by X and Y
    Collections.reverse(sortedPoints); // need to reverse so biggest x comes first
    int layer_count = 1;
    while (!sortedPoints.isEmpty()) {
      ArrayList<Point> layer_list = new ArrayList<Point>();
      // add first point to layer
      Point firstPoint = sortedPoints.get(0);
      layer_list.add(firstPoint);
      sortedPoints.remove(0);
      // add any other points with that same x value to layer
      Point comparePoint = firstPoint;
      while (!sortedPoints.isEmpty() && sortedPoints.get(0).getX() == comparePoint.getX()) {
        comparePoint = sortedPoints.get(0);
        layer_list.add(comparePoint);
        sortedPoints.remove(0);
      }
      // add any point with higher y value
      comparePoint = firstPoint;
      for (int i = 0; i < sortedPoints.size(); i++) {
        Point oldCompare = comparePoint;
        if (sortedPoints.get(i).getY() >= comparePoint.getY()) {
          comparePoint = sortedPoints.get(i);
          layer_list.add(comparePoint);
          sortedPoints.remove(i);
          for (int j = i; j < sortedPoints.size(); j++) {
            if (sortedPoints.get(j).getX() == comparePoint.getX()
                && sortedPoints.get(j).getY() >= oldCompare.getY()) {
              comparePoint = sortedPoints.get(j);
              layer_list.add(comparePoint);
              sortedPoints.remove(j);
            }
          }
        }
      }
      layers.put(layer_count, layer_list);
      layer_count++;
    }
    return layers;
  }

  public String toString(
      Map<Integer, ArrayList<Point>> layers,
      ArrayList<Point> points,
      boolean sortX,
      boolean sortY) {
    StringBuffer buff = new StringBuffer();
    for (int i = 1; i < layers.size() + 1; i++) {
      buff.append(i + ":");
      ArrayList<Point> sortedLayerList = sortToOriginal(layers.get(i), points);
      if (sortX || sortY) {
        sortedLayerList = sortPoints(sortedLayerList, sortX, sortY);
      }
      for (int j = 0; j < sortedLayerList.size(); j++) {
        buff.append("(" + (int) sortedLayerList.get(j).getX() + ",");
        buff.append((int) sortedLayerList.get(j).getY() + ")");
      }
      buff.append(" ");
    }
    String string = buff.toString();
    return string.trim();
  }

  public static void main(String... args) {
    MaximalLayers m = new MaximalLayers();
    String s = m.maximalLayers(args);
    System.out.println(s);
  }
}
