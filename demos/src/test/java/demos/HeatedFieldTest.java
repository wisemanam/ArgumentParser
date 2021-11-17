package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

public class HeatedFieldTest {

  @Test
  public void testHeatedField1() {
    String[] args = {"70", "60", "80", "90", "2", "2", "-t", "10", "-m", "2"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes", result);
  }

  @Test
  public void testHeatedField2() {
    String[] args = {"70", "60", "-m", "2", "80", "--temperature", "10", "90", "2", "2"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes", result);
  }

  @Test
  public void testHeatedField3() {
    String[] args = {"70", "-t", "10", "60", "--minutes", "2", "80", "90", "2", "2"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes", result);
  }

  @Test
  public void testHeatedField4() {
    String[] args = {"40", "80", "80", "-m", "100", "40", "8", "-t", "60", "8"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("cell (8, 8) is 78.90326 degrees Fahrenheit after 100 minutes", result);
  }

  @Test
  public void testHeatedField5() {
    String[] args = {"45.6", "98.7", "78.9", "12.3", "-t", "65.4", "-m", "100", "5", "6"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("cell (5, 6) is 69.0748 degrees Fahrenheit after 100 minutes", result);
  }

  @Test
  public void testHeatedFieldNoArgsGiven() {
    String[] args = {};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument north is required", result);
  }

  @Test
  public void testHeatedFieldOneArgGiven() {
    String[] args = {"70"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument south is required", result);
  }

  @Test
  public void testHeatedFieldTwoArgsGiven() {
    String[] args = {"70", "60"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument east is required", result);
  }

  @Test
  public void testHeatedFieldThreeArgsGiven() {
    String[] args = {"70", "60", "90"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument west is required", result);
  }

  @Test
  public void testHeatedFieldFourArgsGiven() {
    String[] args = {"70", "60", "90", "80"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument x is required", result);
  }

  @Test
  public void testHeatedFieldFiveArgsGiven() {
    String[] args = {"70", "60", "90", "80", "2"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the argument y is required", result);
  }

  @Test
  public void testHeatedFieldExtraArgGiven() {
    String[] args = {"70", "60", "90", "80", "2", "2", "17"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the value 17 matches no argument", result);
  }

  @Test
  public void testHeatedFieldNotAllowedXGiven() {
    String[] args = {"70", "60", "90", "80", "0", "2"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals(
        "HeatedField error: x value 0 is not a member of [1, 2, 3, 4, 5, 6, 7, 8]", result);
  }

  @Test
  public void testHeatedFieldNotAllowedYGiven() {
    String[] args = {"70", "60", "90", "80", "2", "9"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals(
        "HeatedField error: y value 9 is not a member of [1, 2, 3, 4, 5, 6, 7, 8]", result);
  }

  @Test
  public void testHeatedFieldNegMinutesGiven() {
    String[] args = {"70", "60", "90", "80", "2", "2", "-m", "-1"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: minutes must be positive", result);
  }

  @Test
  public void testHeatedFieldZeroMinutesGiven() {
    String[] args = {"70", "60", "90", "80", "2", "2", "--minutes", "0"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: minutes must be positive", result);
  }

  @Test
  public void testHeatedFieldWrongTypeTempGiven() {
    String[] args = {"70", "60", "90", "80", "2", "2", "-t", "bob"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the value bob is not of type float", result);
  }

  @Test
  public void testHeatedFieldWrongTypeMinGiven() {
    String[] args = {"70", "60", "90", "80", "2", "2", "--minutes", "2.5"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals("HeatedField error: the value 2.5 is not of type integer", result);
  }

  @Test
  public void testHeatedFieldHelp() {
    String[] args = {"70", "60", "90", "80", "2", "2", "-h"};
    HeatedField hf = new HeatedField();
    String result = hf.heatedField(args);
    assertEquals(
        "usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y\n\nCalculate the internal cell temperature.\n\npositional arguments:\n north                                      (float)       the temperature of the north edge\n south                                      (float)       the temperature of the south edge\n east                                       (float)       the temperature of the east edge\n west                                       (float)       the temperature of the west edge\n x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}\n y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}\n\nnamed arguments:\n -h, --help                                 show this help message and exit\n -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)\n -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)",
        result);
  }
}
