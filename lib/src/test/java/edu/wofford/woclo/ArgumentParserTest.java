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
              argParse.addPositional("x1", "integer", "the first int argument");
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
    assertEquals(e.getExpectedType(), "float");
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
              argParse.addNonPositional("fstname", "integer", "first nonpos", "5");
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
              argParse.addNonPositional("test", "integer", "test", "5");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "test");
  }

  @Test
  public void testGetValueArgumentWrongValueInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              Argument a = new Argument("test", "integer", "test");
              a.setValue("hello");
              int test = a.getValue();
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testGetValueOptionalArgumentWrongValueInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              OptionalArgument a = new OptionalArgument("test", "integer", "test", "hello");
              int test = a.getValue();
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testGetValueOptionalArgumentFloat() {
    OptionalArgument a = new OptionalArgument("test", "float", "test", "3.5");
    float test = a.getValue();
    assertEquals(test, 3.5);
  }

  @Test
  public void testGetValueOptionalArgumentWrongValueFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              OptionalArgument a = new OptionalArgument("test", "float", "test", "hello");
              float test = a.getValue();
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testGetValueArgumentWrongValueFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              Argument a = new Argument("test", "float", "test");
              a.setValue("hello");
              float test = a.getValue();
            });
    assertEquals(e.getWrongValue(), "hello");
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
  public void testBooleanFlagsNotLast() {
    String[] arguments = {"5", "6", "7", "--arg", "23", "--myflag", "17"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "int1");
    argParse.addPositional("int2", "integer", "int2");
    argParse.addPositional("int3", "integer", "int3");
    argParse.addPositional("int4", "integer", "int4");
    argParse.addNonPositional("arg", "a", "integer", "named argument", "0");
    argParse.addNonPositional("myflag", "f", "boolean", "flag", "false");

    argParse.parse(arguments);
    boolean b = argParse.getValue("myflag");
    assertTrue(b);
  }

  @Test
  public void testNonPositionalFloat() {
    String[] arguments = {"--arg", "3.5"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg", "float", "arg", "5.5");
    argParse.parse(arguments);
    float f = argParse.getValue("arg");
    assertEquals(f, 3.5);
  }

  @Test
  public void testLongNameAcceptedValuesInts() {
    String[] arguments = {"--arg", "5"};
    String[] accepted = {"5", "6"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg", "integer", "arg", "6", accepted);
    argParse.parse(arguments);
    int i = argParse.getValue("arg");
    assertEquals(i, 5);
  }

  @Test
  public void testLongNameAcceptedValuesIntsException() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"--arg", "hello"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg", "integer", "arg", "goodbye", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testLongNameAcceptedValuesFloats() {
    String[] arguments = {"--arg", "5.5"};
    String[] accepted = {"5.5", "6.6"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg", "float", "arg", "6.6", accepted);
    argParse.parse(arguments);
    float i = argParse.getValue("arg");
    assertEquals(i, 5.5);
  }

  @Test
  public void testLongNameAcceptedValuesFloatsException() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"--arg", "hello"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg", "float", "arg", "goodbye", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
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
  public void testGetShortName() {
    String[] arguments = {"-a", "4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg", "a", "integer", "arg", "4");
    argParse.parse(arguments);
    Argument a = argParse.getArgument("arg");
    OptionalArgument oa = (OptionalArgument) a;
    String name = oa.getShortName();
    assertEquals(name, "a");
  }

  @Test
  public void testShortNameStacked() {
    String[] arguments = {"-abc", "4"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("int1", "integer", "int1");
    argParse.addNonPositional("arg1", "a", "boolean", "arg1", "false");
    argParse.addNonPositional("arg2", "b", "boolean", "arg2", "false");
    argParse.addNonPositional("arg3", "c", "boolean", "arg3", "false");
    argParse.parse(arguments);
    boolean a = argParse.getValue("arg1");
    assertEquals(a, true);
  }

  @Test
  public void testStackedNotSpecifiedException() {
    ArgumentNameNotSpecifiedException e =
        assertThrows(
            ArgumentNameNotSpecifiedException.class,
            () -> {
              String[] arguments = {"-adb", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "boolean", "arg", "false");
              argParse.parse(arguments);
            });
    assertEquals(e.getArgName(), "d");
  }

  @Test
  public void testStackedNoValueException() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"-ab", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "integer", "arg", "6");
              argParse.addNonPositional("arg2", "b", "boolean", "arg", "false");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "a");
  }

  @Test
  public void testNotStackedNotSpecifiedException() {
    ArgumentNameNotSpecifiedException e =
        assertThrows(
            ArgumentNameNotSpecifiedException.class,
            () -> {
              String[] arguments = {"-a", "-b", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("string1", "string", "string1");
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.parse(arguments);
            });
    assertEquals(e.getArgName(), "b");
  }

  @Test
  public void testNotStackedNoValueException() {
    NoValueException e =
        assertThrows(
            NoValueException.class,
            () -> {
              String[] arguments = {"-a", "-b"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "integer", "arg", "6");
              argParse.parse(arguments);
            });
    assertEquals(e.getNameMissingValue(), "b");
  }

  @Test
  public void testNotStackedWrongTypeExceptionInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"-a", "-b", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "integer", "arg", "6");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testNotStackedWrongTypeExceptionFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"-a", "-b", "hello"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "float", "arg", "6.5");
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testNotStackedFloat() {
    String[] arguments = {"-a", "-b", "3.5"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
    argParse.addNonPositional("arg2", "b", "float", "arg", "6.5");
    argParse.parse(arguments);
    float f = argParse.getValue("b");
    assertEquals(f, 3.5);
  }

  @Test
  public void testNotStackedAcceptedFloat() {
    String[] arguments = {"-a", "-b", "3.5"};
    String[] accepted = {"3.5", "6.5"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
    argParse.addNonPositional("arg2", "b", "float", "arg", "6.5", accepted);
    argParse.parse(arguments);
    float f = argParse.getValue("b");
    assertEquals(f, 3.5);
  }

  @Test
  public void testNotStackedWrongTypeExceptionAcceptedFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"-a", "-b", "hello"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "float", "arg", "goodbye", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testNotStackedWrongTypeExceptionAcceptedInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"-a", "-b", "hello"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "integer", "arg", "goodbye", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "hello");
  }

  @Test
  public void testNotStackedNotAcceptedException() {
    ValueNotAcceptedException e =
        assertThrows(
            ValueNotAcceptedException.class,
            () -> {
              String[] arguments = {"-a", "-b", "emily"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
              argParse.addNonPositional("arg2", "b", "string", "arg", "goodbye", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getUnacceptedValue(), "emily");
  }

  @Test
  public void testNotStackedString() {
    String[] arguments = {"-a", "-b", "hello"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
    argParse.addNonPositional("arg2", "b", "string", "arg", "goodbye");
    argParse.parse(arguments);
    String f = argParse.getValue("b");
    assertEquals(f, "hello");
  }

  @Test
  public void testNotStackedAcceptedString() {
    String[] arguments = {"-a", "-b", "hello"};
    String[] accepted = {"hello", "goodbye"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
    argParse.addNonPositional("arg2", "b", "string", "arg", "goodbye", accepted);
    argParse.parse(arguments);
    String f = argParse.getValue("b");
    assertEquals(f, "hello");
  }

  @Test
  public void testNotStackedAcceptedInt() {
    String[] arguments = {"-a", "-b", "3"};
    String[] accepted = {"3", "6"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addNonPositional("arg1", "a", "boolean", "arg", "false");
    argParse.addNonPositional("arg2", "b", "integer", "arg", "6", accepted);
    argParse.parse(arguments);
    int f = argParse.getValue("b");
    assertEquals(f, 3);
  }

  @Test
  public void testWrongTypeExceptionAcceptedFloat() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"awesome"};
              String[] accepted = {"awesome", "great"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "float", "the first float argument", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  @Test
  public void testWrongTypeExceptionAcceptedInt() {
    WrongTypeException e =
        assertThrows(
            WrongTypeException.class,
            () -> {
              String[] arguments = {"awesome"};
              String[] accepted = {"awesome", "great"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("x1", "integer", "the first integer argument", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getWrongValue(), "awesome");
  }

  @Test
  public void testGetValueWantingAcceptedInt() {
    String[] arguments = {"1", "2", "3"};
    String[] accepted = {"1", "5", "7"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "integer", "the first int argument", accepted);
    argParse.addPositional("x2", "integer", "the second int argument");
    argParse.addPositional("x3", "integer", "the third int argument");
    argParse.parse(arguments);
    int int1 = argParse.getValue("x1");
    assertEquals(int1, 1);
  }

  @Test
  public void testGetValueWantingAcceptedFloat() {
    String[] arguments = {"7.2", "2", "3"};
    String[] accepted = {"1.5", "5.5", "7.2"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("x1", "float", "the first float argument", accepted);
    argParse.addPositional("x2", "integer", "the second int argument");
    argParse.addPositional("x3", "integer", "the third int argument");
    argParse.parse(arguments);
    float float1 = argParse.getValue("x1");
    assertEquals(float1, 7.2, 0.1);
  }

  @Test
  public void testPositionalNotAcceptedException() {
    ValueNotAcceptedException e =
        assertThrows(
            ValueNotAcceptedException.class,
            () -> {
              String[] arguments = {"emily"};
              String[] accepted = {"hello", "goodbye"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("arg1", "string", "arg", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getUnacceptedValue(), "emily");
    assertEquals(e.getVarName(), "arg1");
  }

  @Test
  public void testPositionalAcceptedStrings() {
    String[] arguments = {"hello"};
    String[] accepted = {"hello", "goodbye"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("arg1", "string", "arg", accepted);
    argParse.parse(arguments);
    String arg1 = argParse.getValue("arg1");
    assertEquals(arg1, "hello");
  }

  @Test
  public void testAcceptedValues() {
    String[] arguments = {"5", "6", "--shape", "square"};
    String[] accepted_shapes = {"square", "circle"};
    ArgumentParser argParse = new ArgumentParser();
    argParse.addPositional("width", "integer", "width");
    argParse.addPositional("height", "integer", "height");
    argParse.addNonPositional("shape", "string", "shape", "circle", accepted_shapes);
    argParse.parse(arguments);
    String value = argParse.getValue("shape");
    assertEquals(value, "square");
  }

  @Test
  public void testUnacceptedValuesNonPositional() {
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

  @Test
  public void testUnacceptedValuesPositional() {
    ValueNotAcceptedException e =
        assertThrows(
            ValueNotAcceptedException.class,
            () -> {
              String[] arguments = {"--shape", "triangle"};
              String[] accepted = {"square", "circle"};
              ArgumentParser argParse = new ArgumentParser();
              argParse.addPositional("shape", "string", "shape", accepted);
              argParse.parse(arguments);
            });
    assertEquals(e.getUnacceptedValue(), "triangle");
  }

  @Test
  public void testXmlJustPositionals() {
    String test_string =
        "<?xml version=\"1.0\"?>"
            + "<arguments>"
            + "<positionalArgs>"
            + "<positional>"
            + "<type>float</type>"
            + "<description>the length of the volume</description>"
            + "<name>length</name>"
            + "</positional>"
            + "<positional>"
            + "<name>width</name>"
            + "<type>float</type>"
            + "<description>the width of the volume</description>"
            + "</positional>"
            + "<positional>"
            + "<description>the height of the volume</description>"
            + "<name>height</name>"
            + "<type>float</type>"
            + "</positional>"
            + "</positionalArgs>"
            + "</arguments>";
    String[] arguments = {"4.56", "6.0", "3.12"};
    ArgumentParser a = XMLparser.parseXML(test_string);
    a.parse(arguments);

    float length = a.getValue("length");
    float width = a.getValue("width");
    float height = a.getValue("height");
    assertEquals(length, 4.56, 0.1);
    assertEquals(width, 6.0, 0.1);
    assertEquals(height, 3.12, 0.1);
  }

  @Test
  public void testXMLJustNonPositionals() {
    String test_string =
        "<?xml version=\"1.0\"?>"
            + "<arguments>"
            + "<namedArgs>"
            + "<named>"
            + "<description>the type of volume</description>"
            + "<shortname>t</shortname>"
            + "<type>string</type>"
            + "<name>type</name>"
            + "<default>"
            + "<value>box</value>"
            + "</default>"
            + "<restrictions>"
            + "<restriction>box</restriction>"
            + "<restriction>pyramid</restriction>"
            + "<restriction>ellipsoid</restriction>"
            + "</restrictions>"
            + "</named>"
            + "<named>"
            + "<default>"
            + "<value>4</value>"
            + "</default>"
            + "<type>integer</type>"
            + "<description>the maximum number of decimal places for the volume</description>"
            + "<name>precision</name>"
            + "<shortname>p</shortname>"
            + "</named>"
            + "</namedArgs>"
            + "</arguments>";
    String[] arguments = {"--type", "box", "--precision", "6"};
    ArgumentParser a = XMLparser.parseXML(test_string);

    a.parse(arguments);

    String type = a.getValue("type");
    int p = a.getValue("p");
    assertEquals(type, "box");
    assertEquals(p, 6);
  }

  @Test
  public void testXMLAllArgTypes() {
    String test_string =
        "<?xml version=\"1.0\"?>"
            + "<arguments>"
            + "<positionalArgs>"
            + "<positional>"
            + "<type>float</type>"
            + "<description>the length of the volume</description>"
            + "<name>length</name>"
            + "</positional>"
            + "<positional>"
            + "<type>float</type>"
            + "<name>width</name>"
            + "<description>the width of the volume</description>"
            + "</positional>"
            + "<positional>"
            + "<description>the height of the volume</description>"
            + "<name>height</name>"
            + "<type>float</type>"
            + "</positional>"
            + "</positionalArgs>"
            + "<namedArgs>"
            + "<named>"
            + "<description>the type of volume</description>"
            + "<shortname>t</shortname>"
            + "<type>string</type>"
            + "<name>type</name>"
            + "<default>"
            + "<value>box</value>"
            + "</default>"
            + "<restrictions>"
            + "<restriction>box</restriction>"
            + "<restriction>pyramid</restriction>"
            + "<restriction>ellipsoid</restriction>"
            + "</restrictions>"
            + "</named>"
            + "<named>"
            + "<default>"
            + "<value>4</value>"
            + "</default>"
            + "<type>integer</type>"
            + "<description>the maximum number of decimal places for the volume</description>"
            + "<name>precision</name>"
            + "<shortname>p</shortname>"
            + "</named>"
            + "</namedArgs>"
            + "</arguments>";
    String[] arguments = {"4.56", "6.0", "3.12", "--type", "box", "--precision", "6"};
    ArgumentParser a = XMLparser.parseXML(test_string);

    a.parse(arguments);

    float length = a.getValue("length");
    float width = a.getValue("width");
    float height = a.getValue("height");
    String type = a.getValue("type");
    int p = a.getValue("p");
    assertEquals(length, 4.56, 0.1);
    assertEquals(width, 6.0, 0.1);
    assertEquals(height, 3.12, 0.1);
    assertEquals(type, "box");
    assertEquals(p, 6);
  }
}
