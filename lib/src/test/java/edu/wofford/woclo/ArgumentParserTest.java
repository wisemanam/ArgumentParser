package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ArgumentParserTest {

  @Test
  public void testArgumentParserTwoArg() {
    String arguments = "alice bob"; // "alice bob"
    ArgumentParser argParse = new ArgumentParser(arguments);
    String x = argParse.getValue(0); // "bob"
    String y = argParse.getValue(1);
    assertEquals(x, "alice");
    assertEquals(y, "bob");
    // Demo needs to find out if x and y are equivalent
  }
  /*
  @Test
  public void testArgumentParserNoArg(){
    ArgumentParser argParse = new ArgumentParser();


  }
  */

  @Test
  public void testArgumentParserOneArg() {
    String arguments = "hello";
    ArgumentParser argParse = new ArgumentParser(arguments);
    String x = argParse.getValue(0);
    assertEquals(x, "hello");
  }
}
