package demos;

import edu.wofford.woclo.*;
import java.awt.*;
import java.util.*;

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
      if (pointSplit.length % 2 == 1)
        return "MaximalLayers error: "
            + points.substring(points.length() - 1)
            + " is an unpaired x coordinate";
      int[] point = new int[pointSplit.length];
      // catch the wrong type in points
      for (int i = 0; i < pointSplit.length; i++) {
        point[i] = Integer.parseInt(pointSplit[i]);
      }
      ArrayList<Point> pointList = new ArrayList<>();
      for (int i = 0; i < point.length; i += 2) {
        Point p = new Point(point[i], point[i + 1]);
        pointList.add(p);
      }
      Map<Integer, ArrayList<Point>> layers = findMaximalLayers(pointList);
      return toString(layers, pointList, sortedX, sortedY);
    } catch (HelpException e) {
      return "help";
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

    points_copy.sort(Comparator.comparing(Point::getX));
    ArrayList<Point> sortedPointsX = new ArrayList<Point>(points_copy);
    Collections.reverse(sortedPointsX);
    if (sortedX && !sortedY) {
      return sortedPointsX;
    }
    points_copy.sort(Comparator.comparing(Point::getY));
    ArrayList<Point> sortedPointsY = new ArrayList<Point>(points_copy);
    Collections.reverse(sortedPointsY);
    if (sortedY && !sortedX) {
      return sortedPointsY;
    }
    points_copy.sort(Comparator.comparing(Point::getX));
    ArrayList<Point> sortedPointsXY = new ArrayList<Point>(points_copy);
    Collections.reverse(sortedPointsXY);
    return sortedPointsXY;
  }

  public Map<Integer, ArrayList<Point>> findMaximalLayers(ArrayList<Point> points) {
    Map<Integer, ArrayList<Point>> layers = new HashMap<Integer, ArrayList<Point>>();
    ArrayList<Point> sortedPoints = sortPoints(points, true, true); // sort by X and Y
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
        if (sortedPoints.get(i).getY() >= comparePoint.getY()) {
          comparePoint = sortedPoints.get(i);
          layer_list.add(comparePoint);
          sortedPoints.remove(i);
          for (int j = i; j < sortedPoints.size(); j++) {
            if (sortedPoints.get(j).getX() == comparePoint.getX()
                && sortedPoints.get(j).getY() > firstPoint.getY()) {
              comparePoint = sortedPoints.get(j);
              layer_list.add(comparePoint);
              sortedPoints.remove(j);
            }
          }
        }
      }
      // add any points with coords between first and comparePoint?
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
    String str = "";
    for (int i = 1; i < layers.size() + 1; i++) {
      str += i + ":";
      ArrayList<Point> sortedLayerList = new ArrayList<>();
      if (!sortX && !sortY) {
        sortedLayerList = sortToOriginal(layers.get(i), points);
      } else {
        sortedLayerList = sortPoints(layers.get(i), sortX, sortY);
      }
      for (int j = 0; j < sortedLayerList.size(); j++) {
        str += "(" + (int) sortedLayerList.get(j).getX() + ",";
        str += (int) sortedLayerList.get(j).getY() + ")";
      }
      str += " ";
    }
    return str.trim();
  }

  public static void main(String... args) {}
}
