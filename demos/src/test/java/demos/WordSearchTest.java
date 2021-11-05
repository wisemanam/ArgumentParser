package demos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordSearchTest {
  @Test
  public void testDefaultGrid() {
    String[] args = {"h    a    l    ifax      ", "halifax"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "h:1,1 a:2,1 l:3,1 i:4,1 f:4,2 a:4,3 x:4,4");

    String[] args2 = {"softswoskaolzilklqmtreyoy", "yellow"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "y:5,3 e:5,2 l:4,2 l:3,2 o:2,2 w:2,1");

    String[] args3 = {"w    ol    l    ey       ", "yellow"};
    WordSearch w3 = new WordSearch();
    assertEquals(w3.wordsearch(args3), "y:4,3 e:4,2 l:3,2 l:2,2 o:2,1 w:1,1");
  }

  @Test
  public void testOverwriteHeight() {
    String[] args = {"tarsdfgelhijkom", "molerat", "--height", "3"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "m:3,5 o:3,4 l:2,4 e:2,3 r:1,3 a:1,2 t:1,1");

    String[] args2 = {"euimngtrklnwmhaavanneaptyqsvbt", "savannah", "--height", "6"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "s:6,2 a:5,2 v:4,2 a:4,3 n:4,4 n:4,5 a:3,5 h:3,4");
  }

  @Test
  public void testOverwriteWidth() {
    String[] args = {"rmgvoynelemsylilwcbq", "emily", "--width", "4"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "e:3,2 m:3,3 i:4,3 l:4,2 y:4,1");

    String[] args2 = {"tterargrad", "drgarrett", "--width", "2"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "d:5,2 r:4,2 g:4,1 a:3,1 r:3,2 r:2,2 e:2,1 t:1,1 t:1,2");
  }

  @Test
  public void testOverwriteWidthandHeight() {
    String[] args = {"--height", "3", "aaaaerduyaaa", "audrey", "--width", "4"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "a:1,4 u:2,4 d:2,3 r:2,2 e:2,1 y:3,1");
  }

  @Test
  public void testTooFewPositional() {
    String[] args = {"--height", "2", "ashutnrk", "--width", "4"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "WordSearch error: the argument target is required");
  }

  @Test
  public void testTooManyPositional() {
    String[] args = {"--height", "2", "ashutnrk", "--width", "4", "hut", "tire"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "WordSearch error: the value tire matches no argument");
  }

  @Test
  public void missingValue() {
    String[] args = {"--height", "2", "ashutnrk", "--width"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "WordSearch error: no value for width");
  }

  @Test
  public void help() {
    String[] args = {"--height", "2", "ashutnrk", "--width", "--help", "4"};
    WordSearch w = new WordSearch();
    assertEquals(
        w.wordsearch(args),
        "usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target\n\nFind a target word in a grid.\n\npositional arguments:\n grid             (string)      the grid to search\n target           (string)      the target word\n\nnamed arguments:\n -h, --help       show this help message and exit\n --width WIDTH    (integer)     the grid width (default: 5)\n --height HEIGHT  (integer)     the grid height (default: 5)");
  }

  @Test
  public void gridDimensionMismatch() {
    String[] args = {"--height", "3", "aaaaerduyaaaq", "audrey", "--width", "4"};
    WordSearch w = new WordSearch();
    assertEquals(
        w.wordsearch(args),
        "WordSearch error: grid dimensions (4 x 3) do not match grid length (13)");
  }

  @Test
  public void targetNotFound() {
    String[] args2 = {"softswoskaolzilklqmtreyoy", "bug"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "bug not found");
  }

  @Test
  public void wrongType() {
    String[] args = {"--height", "2", "ashutnrk", "--width", "hut"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "WordSearch error: the value hut is not of type integer");
  }
}
