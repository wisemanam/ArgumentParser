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
}
