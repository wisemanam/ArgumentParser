package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "String", "the first string argument");
    argParse.addPositional("string2", "String", "the second string argument");
    argParse.parse(arguments);
    String x = argParse.getValueString("string1");
    String y = argParse.getValueString("string2");
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
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.parse(arguments);
            });
    assertEquals(e.getNextExpectedValue(), 0);
  }

  @Test
  public void testNumWordsNotExpected() {
    TooManyException e =
        assertThrows(
            TooManyException.class,
            () -> {
              String[] arguments = {"hello", "hey", "hi"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.addPositional("string2", "String", "the second string argument");
              argParse.parse(arguments);
            });
    assertEquals(e.getFirstExtra(), "hi");
    TooFewException e1 =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] arguments = {"hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.addPositional("string2", "String", "the second string argument");
              argParse.parse(arguments);
            });
    assertEquals(e1.getNextExpectedValue(), 1);
  }

  @Test
  public void testHelp() {
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "-h"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "String", "the first string argument");
          argParse.addPositional("string2", "String", "The second string argument");
          argParse.parse(arguments);
        });
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "hi", "--help"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "String", "the first string argument");
          argParse.parse(arguments);
        });
  }

  @Test
  public void testNumArgs() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "String", "the first string argument");
    argParse.addPositional("string2", "String", "the second string argument");
    argParse.parse(arguments);
    assertEquals(2, argParse.numArgs());
  }

  @Test
  public void testGetInt() {
    String[] arguments = {"1", "2", "3"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "int", "the first int argument");
    argParse.addPositional("x2", "int", "the second int argument");
    argParse.addPositional("x3", "int", "the third int argument");
    argParse.parse(arguments);
    int int1 = argParse.getValueInt("x1");
    int int2 = argParse.getValueInt("x2");
    int int3 = argParse.getValueInt("x3");
    assertEquals(int1, 1);
    assertEquals(int2, 2);
    assertEquals(int3, 3);
  }

  @Test
  public void testGetFloat() {
    String[] arguments = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "the first float argument");
    argParse.addPositional("x2", "float", "the second float argument");
    argParse.addPositional("x3", "float", "the third float argument");
    argParse.parse(arguments);
    float float1 = argParse.getValueFloat("x1");
    float float2 = argParse.getValueFloat("x2");
    float float3 = argParse.getValueFloat("x3");
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
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "int", "the first int argument");
              argParse.parse(arguments);
              int int1 = argParse.getValueInt("x1");
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
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "float", "the first float argument");
              argParse.parse(arguments);
              float float1 = argParse.getValueFloat("x1");
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  // @Test
  // public void testNamedArgs() {

  // }
}
