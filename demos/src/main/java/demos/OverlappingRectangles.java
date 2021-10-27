package demos;

import edu.wofford.woclo.*;

public class OverlappingRectangles {

  // make one function and call it in main
  public String overlappingRectangles(String[] arguments) {
    ArgumentParser argParse = new ArgumentParser(8, arguments);
    int x1rec1 = argParse.getInt(0);
    int y1rec1 = argParse.getInt(1);
    int x2rec1 = argParse.getInt(2);
    int y2rec1 = argParse.getInt(3);
    int x3rec2 = argParse.getInt(4);
    int y3rec2 = argParse.getInt(5);
    int x4rec2 = argParse.getInt(6);
    int y4rec2 = argParse.getInt(7);
    int rec1width = x2rec1 - x1rec1;
    int rec1height = y2rec1 - y1rec1;
    int rec2width = x4rec2 - x3rec2;
    int rec2height = y4rec2 - y3rec2;
    int rec1area = rec1width * rec1height;
    int rec2area = rec2width * rec2height;
    int overlap = 1;
    int total = rec1area + rec2area - overlap;
    String strOverlap = Integer.toString(overlap);
    String strTotal = Integer.toString(total);
    return strOverlap + " " + strTotal;
  } 
  public static void main(String... args) {
    OverlappingRectangles overlapRec = new OverlappingRectangles();
    String overlapRecVals = overlapRec.overlappingRectangles(args);
    System.out.println(overlapRecVals);
  }
}
