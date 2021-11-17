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
}
