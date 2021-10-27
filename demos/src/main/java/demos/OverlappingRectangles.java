package demos;

import edu.wofford.woclo.*;

public class OverlappingRectangles {

  // make one function and call it in main
  public String overlappingRectangles(String[] arguments) {
    try {
      ArgumentParser argParse = new ArgumentParser(8, arguments);
      int x1rec1 = argParse.getInt(0);
      int y1rec1 = argParse.getInt(1);
      int x2rec1 = argParse.getInt(2);
      int y2rec1 = argParse.getInt(3);
      int x1rec2 = argParse.getInt(4);
      int y1rec2 = argParse.getInt(5);
      int x2rec2 = argParse.getInt(6);
      int y2rec2 = argParse.getInt(7);
      int rec1width = Math.abs(x2rec1 - x1rec1);
      int rec1height = Math.abs(y2rec1 - y1rec1);
      int rec2width = Math.abs(x2rec2 - x1rec2);
      int rec2height = Math.abs(y2rec2 - y1rec2);
      int rec1area = rec1width * rec1height;
      int rec2area = rec2width * rec2height;
      int overlapwidth = Math.min(x2rec1, x2rec2) - Math.max(x1rec1, x1rec2);
      int overlapheight = Math.min(y2rec1, y2rec2) - Math.max(y1rec1, y1rec2);
      int overlap = overlapwidth * overlapheight;
      int total = rec1area + rec2area - overlap;
      String strOverlap = Integer.toString(overlap);
      String strTotal = Integer.toString(total);
      return strOverlap + " " + strTotal;
    } catch (TooFewException e1) {
      return "OverlappingRectangles error: the argument x1 is required";
    } catch (TooManyException e2) {
      return "OverlappingRectangles error: the value 42 matches no argument";
    } catch (WrongTypeException e3) {
      return "OverlappingRectangles error: the value z is not of type integer";
    } catch (HelpException e4) {
      return "usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4\n\nDetermine the overlap and total area of two rectangles.\n\npositional arguments:\n x1          (integer)     lower-left x for rectangle 1\n y1          (integer)     lower-left y for rectangle 1\n x2          (integer)     upper-right x for rectangle 1\n y2          (integer)     upper-right y for rectangle 1\n x3          (integer)     lower-left x for rectangle 2\n y3          (integer)     lower-left y for rectangle 2\n x4          (integer)     upper-right x for rectangle 2\n y4          (integer)     upper-right y for rectangle 2\n\nnamed arguments:\n -h, --help  show this help message and exit";
    }
  } 
  public static void main(String... args) {
    OverlappingRectangles overlapRec = new OverlappingRectangles();
    String overlapRecVals = overlapRec.overlappingRectangles(args);
    System.out.println(overlapRecVals);
  }
}
