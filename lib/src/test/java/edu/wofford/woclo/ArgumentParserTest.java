package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
<<<<<<< HEAD
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "String", "the first string argument");
    argParse.addPositional("string2", "String", "the second string argument");
    argParse.parse(arguments);
    String x = argParse.getValueString("string1");
    String y = argParse.getValueString("string2");
=======
    String[] args = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "first string");
    argParse.addPositional("string2", "string", "second string");
    argParse.parse(args);
    String x = argParse.getString("string1");
    String y = argParse.getString("string2");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
    assertEquals(x, "alice");
    assertEquals(y, "bob");
  }

  @Test
  public void testArgumentParserNoArg() {
    TooFewException e =
        assertThrows(
            TooFewException.class,
            () -> {
<<<<<<< HEAD
              String[] arguments = {};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.parse(arguments);
=======
              String[] args = {};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
            });
    assertEquals(e.getNextExpectedValue(), 0);
  }

  @Test
  public void testNumWordsNotExpected() {
    TooManyException e =
        assertThrows(
            TooManyException.class,
            () -> {
<<<<<<< HEAD
              String[] arguments = {"hello", "hey", "hi"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.addPositional("string2", "String", "the second string argument");
              argParse.parse(arguments);
=======
              String[] args = {"alice", "bob", "hi"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
              assertEquals(x, "alice");
              assertEquals(y, "bob");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
            });
    assertEquals(e.getFirstExtra(), "hi");
    TooFewException e1 =
        assertThrows(
            TooFewException.class,
            () -> {
<<<<<<< HEAD
              String[] arguments = {"hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "String", "the first string argument");
              argParse.addPositional("string2", "String", "the second string argument");
              argParse.parse(arguments);
=======
              String[] args = {"alice"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "first string");
              argParse.addPositional("string2", "string", "second string");
              argParse.parse(args);
              String x = argParse.getString("string1");
              String y = argParse.getString("string2");
              assertEquals(x, "alice");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
            });
    assertEquals(e1.getNextExpectedValue(), 1);
  }

  @Test
  public void testHelp() {
    assertThrows(
        HelpException.class,
        () -> {
<<<<<<< HEAD
          String[] arguments = {"hello", "-h"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "String", "the first string argument");
          argParse.addPositional("string2", "String", "The second string argument");
          argParse.parse(arguments);
=======
          String[] args = {"alice", "bob", "-h"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "string", "first string");
          argParse.addPositional("string2", "string", "second string");
          argParse.parse(args);
          String x = argParse.getString("string1");
          String y = argParse.getString("string2");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
        });
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "hi", "--help"};
          ArgumentParser argParse = new ArgumentParser();
<<<<<<< HEAD
          argParse.addPositional("string1", "String", "the first string argument");
          argParse.parse(arguments);
=======
          argParse.addPositional("string1", "string", "first string");
          argParse.addPositional("string2", "string", "second string");
          argParse.parse(arguments);
          String x = argParse.getString("string1");
          String y = argParse.getString("string2");
          assertEquals(x, "hello");
          assertEquals(y, "hi");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
        });
  }

  @Test
  public void testNumArgs() {
<<<<<<< HEAD
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "String", "the first string argument");
    argParse.addPositional("string2", "String", "the second string argument");
    argParse.parse(arguments);
=======
    String[] args = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "first string");
    argParse.addPositional("string2", "string", "second string");
    argParse.parse(args);
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
    assertEquals(2, argParse.numArgs());
  }

  @Test
  public void testGetInt() {
<<<<<<< HEAD
    String[] arguments = {"1", "2", "3"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "int", "the first int argument");
    argParse.addPositional("x2", "int", "the second int argument");
    argParse.addPositional("x3", "int", "the third int argument");
    argParse.parse(arguments);
    int int1 = argParse.getValueInt("x1");
    int int2 = argParse.getValueInt("x2");
    int int3 = argParse.getValueInt("x3");
=======
    String[] args = {"1", "2"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "int", "first int");
    argParse.addPositional("x2", "int", "second int");
    argParse.parse(args);
    int int1 = argParse.getInt("x1");
    int int2 = argParse.getInt("x2");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
    assertEquals(int1, 1);
    assertEquals(int2, 2);
  }

  @Test
  public void testGetFloat() {
<<<<<<< HEAD
    String[] arguments = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "the first float argument");
    argParse.addPositional("x2", "float", "the second float argument");
    argParse.addPositional("x3", "float", "the third float argument");
    argParse.parse(arguments);
    float float1 = argParse.getValueFloat("x1");
    float float2 = argParse.getValueFloat("x2");
    float float3 = argParse.getValueFloat("x3");
=======
    String[] args = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "first float");
    argParse.addPositional("x2", "float", "second float");
    argParse.addPositional("x2", "float", "third float");
    argParse.parse(args);

    float float1 = argParse.getFloat("x1");
    float float2 = argParse.getFloat("x2");
    float float3 = argParse.getFloat("x3");
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
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
<<<<<<< HEAD
              argParse.addPositional("x1", "int", "the first int argument");
              argParse.parse(arguments);
              int int1 = argParse.getValueInt("x1");
=======
              argParse.addPositional("x1", "int", "first number");
              int int1 = argParse.getInt("x1");
              argParse.parse(arguments);
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testWrongTypeExceptionFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
<<<<<<< HEAD
              String[] arguments = {"awesome"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "float", "the first float argument");
              argParse.parse(arguments);
              float float1 = argParse.getValueFloat("x1");
=======
              String[] args = {"1", "4.5"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "int", "first int");
              argParse.addPositional("x2", "int", "second int");
              argParse.parse(args);
>>>>>>> 3950e2dd0013ec3b48980df9415cca71ad8d2995
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  // @Test
  // public void testNamedArgs() {

  // }
}
