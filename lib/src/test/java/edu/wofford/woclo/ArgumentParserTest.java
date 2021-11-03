package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
    String[] args = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "first string");
    argParse.addPositional("string2", "string", "second string");
    argParse.parse(args);
    String x = argParse.getString("string1");
    String y = argParse.getString("string2");
    assertEquals(x, "alice");
    assertEquals(y, "bob");
  }

  @Test
  public void testArgumentParserNoArg() {
    TooFewException e =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] args = {};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
            });
    assertEquals(e.getNextExpected(), 0);
  }

  @Test
  public void testNumWordsNotExpected() {
    TooManyException e =
        assertThrows(
            TooManyException.class,
            () -> {
              String[] args = {"alice", "bob", "hi"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
              assertEquals(x, "alice");
              assertEquals(y, "bob");
            });
    assertEquals(e.getFirstExtra(), "hi");
    TooFewException e1 =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] args = {"alice"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
              assertEquals(x, "alice");
            });
    assertEquals(e1.getNextExpected(), 1);
  }

  @Test
  public void testHelp() {
    assertThrows(
        HelpException.class,
        () -> {
          String[] args = {"alice", "bob", "-h"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "string", "first string");
          argParse.addPositional("string2", "string", "second string");
          argParse.parse(args);
          String x = argParse.getString("string1");
          String y = argParse.getString("string2");
        });
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "hi", "--help"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "string", "first string");
          argParse.addPositional("string2", "string", "second string");
          argParse.parse(arguments);
          String x = argParse.getString("string1");
          String y = argParse.getString("string2");
          assertEquals(x, "hello");
          assertEquals(y, "hi");
        });
  }

  @Test
  public void testNumArgs() {
    String[] args = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "first string");
    argParse.addPositional("string2", "string", "second string");
    argParse.parse(args);
    assertEquals(2, argParse.numArgs());
  }

  @Test
  public void testGetInt() {
    String[] args = {"1", "2"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "int", "first int");
    argParse.addPositional("x2", "int", "second int");
    argParse.parse(args);
    int int1 = argParse.getInt("x1");
    int int2 = argParse.getInt("x2");
    assertEquals(int1, 1);
    assertEquals(int2, 2);
  }

  @Test
  public void testGetFloat() {
    String[] args = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "first float");
    argParse.addPositional("x2", "float", "second float");
    argParse.addPositional("x2", "float", "third float");
    argParse.parse(args);

    float float1 = argParse.getFloat("x1");
    float float2 = argParse.getFloat("x2");
    float float3 = argParse.getFloat("x3");
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
              argParse.addPositional("x1", "int", "first number");
              int int1 = argParse.getInt("x1");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testWrongTypeExceptionFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] args = {"1", "4.5"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "int", "first int");
              argParse.addPositional("x2", "int", "second int");
              argParse.parse(args);
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  // @Test
  // public void testNamedArgs() {

  // }
}
