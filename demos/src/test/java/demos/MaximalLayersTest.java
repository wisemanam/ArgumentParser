package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class MaximalLayersTest {
  @Test
  public void testMaximalLayersPointsOnly() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)", answer);
  }

  @Test
  public void testMaximalLayersSortedXOnly() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15,7", "--sortedX"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)", answer);
  }

  @Test
  public void testMaximalLayersSortedYOnly() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15,7", "--sortedY"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("1:(15,7)(4,9) 2:(10,2)(5,5) 3:(2,3)", answer);
  }

  @Test
  public void testMaximalLayersSortedXAndY() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15,7", "--sortedX", "--sortedY"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)", answer);
  }

  @Test
  public void testMaximalLayersSortedXAndYRandom() {
    String[] arguments = {"--sortedY", "5,5,4,9,10,2,2,3,15,7", "--sortedX"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)", answer);
  }

  @Test
  public void testMaximalLayersArgumentNotGiven() {
    String[] arguments = {};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("MaximalLayers error: the argument points is required", answer);
  }

  @Test
  public void testMaximalLayersRandomArgument() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15,7", "extra"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("MaximalLayers error: the value extra matches no argument", answer);
  }

  @Test
  public void testMaximalLayersUnevenPoints() {
    String[] arguments = {"5,5,4,9,10,2,2,3,15"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("MaximalLayers error: 15 is an unpaired x coordinate", answer);
  }

  @Test
  public void testMaximalLayersWrongTypeValue() {
    String[] arguments = {"5,5,4,9,10,x,2,3,15,7"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals("MaximalLayers error: the value x is not of type integer", answer);
  }

  @Test
  public void testHelp() {
    String[] arguments = {"5,5,4,9,10,x,2,3,15,7", "--help"};
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals(
        "usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points\n\nSort the points into layers.\n\npositional arguments:\n points      (string)      the data points\n\nnamed arguments:\n -h, --help  show this help message and exit\n --sortedX   sort layers by x coordinate\n --sortedY   sort layers by y coordinate",
        answer);
  }

  @Test
  public void testNextPointSameX() {
    String[] arguments = {
      "6,2,13,18,9,9,20,10,19,19,12,12,3,3,2,15,13,13,5,12,2,14,1,20", "--sortedY"
    };
    MaximalLayers maxLay = new MaximalLayers();
    String answer = maxLay.maximalLayers(arguments);
    assertEquals(
        "1:(20,10)(19,19)(1,20) 2:(13,13)(13,18) 3:(12,12)(5,12)(2,14)(2,15) 4:(9,9) 5:(6,2)(3,3)",
        answer);
  }
}
