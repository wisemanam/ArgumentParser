package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser(2, arguments);
    String x = argParse.getString(0);
    String y = argParse.getString(1);
    assertEquals(x, "alice");
    assertEquals(y, "bob");
  }

  @Test
  public void testArgumentParserNoArg() {
    TooFewException e =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] arguments = {};
              ArgumentParser argParse = new ArgumentParser(2, arguments);
            });
    assertEquals(e.getNextExpected(), 0);
  }

  @Test
  public void testNumWordsNotExpected() {
    TooManyException e =
        assertThrows(
            TooManyException.class,
            () -> {
              String[] arguments = {"hello", "hey", "hi"};
              ArgumentParser argParse = new ArgumentParser(2, arguments);
            });
    assertEquals(e.getFirstExtra(), "hi");
    TooFewException e1 =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] arguments = {"hello"};
              ArgumentParser argParse = new ArgumentParser(2, arguments);
            });
    assertEquals(e1.getNextExpected(), 1);
  }

  @Test
  public void testHelp() {
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "-h"};
          ArgumentParser argParse = new ArgumentParser(2, arguments);
        });
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "hi", "--help"};
          ArgumentParser argParse = new ArgumentParser(2, arguments);
        });
  }

  @Test
  public void testNumArgs() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser(2, arguments);
    assertEquals(2, argParse.numArgs());
  }

  @Test
  public void testGetInt() {
    String[] arguments = {"1", "2", "3"};
    ArgumentParser argParse = new ArgumentParser(3, arguments);
    int int1 = argParse.getInt(0);
    int int2 = argParse.getInt(1);
    int int3 = argParse.getInt(2);
    assertEquals(int1, 1);
    assertEquals(int2, 2);
    assertEquals(int3, 3);
  }

  @Test
  public void testGetFloat() {
    String[] arguments = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser(3, arguments);
    float float1 = argParse.getFloat(0);
    float float2 = argParse.getFloat(1);
    float float3 = argParse.getFloat(2);
    assertEquals(float1, 1.5, 0.1);
    assertEquals(float2, 2.3, 0.1);
    assertEquals(float3, 3.4, 0.1);
  }

  @Test
  public void testWrongTypeExceptionInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"hello"};
              ArgumentParser argParse = new ArgumentParser(1, arguments);
              int int1 = argParse.getInt(0);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testWrongTypeExceptionFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"awesome"};
              ArgumentParser argParse = new ArgumentParser(1, arguments);
              float float1 = argParse.getFloat(0);
            });
    assertEquals(e.getWrongValue(), "awesome");
  }
}
