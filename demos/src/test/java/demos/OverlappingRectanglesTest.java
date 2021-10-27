package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class OverlappingRectanglesTest {
    String[] args = {"-3", "0", "3", "4", "0", "-1", "9", "2"};
    OverlappingRectangles o = new OverlappingRectangles();
    String oString = overlapRec.overlappingRectangles(args);
    assertEqual(oString, "6 45");
}