package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "the first string argument");
    argParse.addPositional("string2", "string", "the second string argument");
    argParse.parse(arguments);
    String x = argParse.getValue("string1");
    String y = argParse.getValue("string2");
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
              argParse.addPositional("string1", "string", "the first string argument");
              argParse.parse(arguments);
            });
    assertEquals(e.getNextExpectedName(), "string1");
  }

  @Test
  public void testNumWordsNotExpected() {
    TooManyException e =
        assertThrows(
            TooManyException.class,
            () -> {
              String[] arguments = {"hello", "hey", "hi"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "the first string argument");
              argParse.addPositional("string2", "string", "the second string argument");
              argParse.parse(arguments);
            });
    assertEquals(e.getFirstExtra(), "hi");
    TooFewException e1 =
        assertThrows(
            TooFewException.class,
            () -> {
              String[] arguments = {"hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "the first string argument");
              argParse.addPositional("string2", "string", "the second string argument");
              argParse.parse(arguments);
            });
    assertEquals(e1.getNextExpectedName(), "string2");
  }

  @Test
  public void testHelp() {
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "-h"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "string", "the first string argument");
          argParse.addPositional("string2", "string", "The second string argument");
          argParse.parse(arguments);
        });
    assertThrows(
        HelpException.class,
        () -> {
          String[] arguments = {"hello", "hi", "--help"};
          ArgumentParser argParse = new ArgumentParser();
          argParse.addPositional("string1", "string", "the first string argument");
          argParse.parse(arguments);
        });
  }

  @Test
  public void testNumArgs() {
    String[] arguments = {"alice", "bob"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("string1", "string", "the first string argument");
    argParse.addPositional("string2", "string", "the second string argument");
    argParse.parse(arguments);
    assertEquals(2, argParse.numArgs());
  }

  @Test
  public void testGetValueWantingInt() {
    String[] arguments = {"1", "2", "3"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "integer", "the first int argument");
    argParse.addPositional("x2", "integer", "the second int argument");
    argParse.addPositional("x3", "integer", "the third int argument");
    argParse.parse(arguments);
    int int1 = argParse.getValue("x1");
    int int2 = argParse.getValue("x2");
    int int3 = argParse.getValue("x3");
    assertEquals(int1, 1);
    assertEquals(int2, 2);
    assertEquals(int3, 3);
  }

  @Test
  public void testGetValueWantingFloat() {
    String[] arguments = {"1.5", "2.3", "3.4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "the first float argument");
    argParse.addPositional("x2", "float", "the second float argument");
    argParse.addPositional("x3", "float", "the third float argument");
    argParse.parse(arguments);
    float float1 = argParse.getValue("x1");
    float float2 = argParse.getValue("x2");
    float float3 = argParse.getValue("x3");
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
              int int1 = argParse.getValue("x1");
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
              float float1 = argParse.getValue("x1");
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  @Test
  public void testNamedArgsAllGiven() {
    String[] arguments = {"5", "6", "7", "--myargs1", "11", "--myargs2", "hello"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "first pos");
    argParse.addPositional("int2", "integer", "second pos");
    argParse.addPositional("int3", "integer", "third pos");
    argParse.addNonPositional("myargs1", "a", "integer", "first nonpos", "20");
    argParse.addNonPositional("myargs2", "b", "string", "second nonpos", "audrey");
    argParse.parse(arguments);
    int int1 = argParse.getValue("int1");
    int int2 = argParse.getValue("int2");
    int int3 = argParse.getValue("int3");
    int myargs1 = argParse.getValue("myargs1");
    String myargs2 = argParse.getValue("myargs2");
    assertEquals(int1, 5);
    assertEquals(int2, 6);
    assertEquals(int3, 7);
    assertEquals(myargs1, 11);
    assertEquals(myargs2, "hello");
  }

  @Test
  public void testNamedArgsAllGivenUnordered() {
    String[] arguments = {"--string1", "moose", "5", "--string2", "cow", "7"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "first pos");
    argParse.addPositional("int2", "integer", "second pos");
    argParse.addNonPositional("string1", "s", "string", "first nonpos", "awesome");
    argParse.addNonPositional("string2", "t", "string", "second nonpos", "cat");
    argParse.parse(arguments);
    int int1 = argParse.getValue("int1");
    int int2 = argParse.getValue("int2");
    String string1 = argParse.getValue("string1");
    String string2 = argParse.getValue("string2");
    assertEquals(int1, 5);
    assertEquals(int2, 7);
    assertEquals(string1, "moose");
    assertEquals(string2, "cow");
  }

  @Test
  public void testNamedArgumentsNotGiven() {
    String[] arguments = {"5"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "first pos");
    argParse.addNonPositional("arg1", "a", "integer", "first nonpos", "2");
    argParse.addNonPositional("arg2", "b", "integer", "second nonpos", "10");
    argParse.parse(arguments);
    int int1 = argParse.getValue("int1");
    int arg1 = argParse.getValue("arg1");
    int arg2 = argParse.getValue("arg2");
    assertEquals(int1, 5);
    assertEquals(arg1, 2);
    assertEquals(arg2, 10);
  }

  @Test
  public void testGetValueNamedInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"--fstname", "3.5", "5"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("int1", "integer", "first pos");
              argParse.addNonPositional("fstname", "f", "integer", "first nonpos", "5");
              argParse.parse(arguments);
              int int1 = argParse.getValue("int1");
              int fstname = argParse.getValue("fstname");
            });
    assertEquals(e.getWrongValue(), "3.5");
  }

  @Test
  public void testGetArgument() {
    String[] arguments = {"hello"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("hello", "string", "first pos");
    argParse.parse(arguments);
    Argument test = argParse.getArgument("hello");
    String value = test.getValue();
    String type = test.getType();
    String description = test.getDescription();
    String name = test.getName();
    assertEquals(name, "hello");
    assertEquals(value, "hello");
    assertEquals(type, "string");
    assertEquals(description, "first pos");
  }

  @Test
  public void testArgumentNameNotSpecifiedException() {
    ArgumentNameNotSpecifiedException e =
        assertThrows(
            ArgumentNameNotSpecifiedException.class,
            () -> {
              String[] arguments = {"3", "--myarg1", "1"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("int1", "integer", "first pos");
              argParse.addNonPositional("myargs1", "a", "integer", "first nonpos", "5");
              argParse.parse(arguments);
            });
    assertEquals(e.getArgName(), "myarg1");
  }

  @Test
  public void testNoValueException() {
    NoValueException e =
        assertThrows(
            NoValueException.class,
            () -> {
              String[] arguments = {"--test"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("test", "t", "string", "test", "test");
              argParse.parse(arguments);
            });
    assertEquals(e.getNameMissingValue(), "test");
  }

  @Test
  public void testGivenOtherThanFloatNamed() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"--test", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("test", "t", "float", "test", "3.5");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testWrongTypeExceptionExpectInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"--test", "test"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("test", "t", "integer", "test", "5");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "test");
  }

  @Test
  public void testBooleanFlags() {
    String[] arguments = {"5", "6", "7", "--arg", "23", "--myflag"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "int1");
    argParse.addPositional("int2", "integer", "int2");
    argParse.addPositional("int3", "integer", "int3");
    argParse.addNonPositional("arg", "a", "integer", "named argument", "0");
    argParse.addNonPositional("myflag", "f", "boolean", "flag", "false");

    argParse.parse(arguments);
    boolean b = argParse.getValue("myflag");
    assertTrue(b);
  }

  @Test
  public void testShortNamedArguments() {
    String[] arguments = {"5", "6", "7", "--arg", "23", "--myflag"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "int1");
    argParse.addPositional("int2", "integer", "int2");
    argParse.addPositional("int3", "integer", "int3");
    argParse.addNonPositional("arg", "a", "integer", "named argument", "0");
    argParse.addNonPositional("myflag", "f", "boolean", "flag", "false");

    argParse.parse(arguments);
    int i = argParse.getValue("a");
    assertEquals(i, 23);
  }

  @Test
  public void testAcceptedValues() {
    String[] arguments = {"5", "6", "--shape", "square"};
    String[] accepted_shapes = {"square", "circle"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("width", "integer", "width");
    argParse.addPositional("height", "integer", "height");
    argParse.addNonPositional("shape", "s", "string", "shape", "circle", accepted_shapes);
    argParse.parse(arguments);
    String value = argParse.getValue("shape");
    assertEquals(value, "square");
  }

  @Test
  public void testUnacceptedValues() {
    ValueNotAcceptedException e =
        assertThrows(
            ValueNotAcceptedException.class,
            () -> {
              String[] arguments = {"--shape", "triangle"};
              String[] accepted = {"square", "circle"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("shape", "s", "string", "shape", "circle", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getUnacceptedValue(), "triangle");
  }
}
