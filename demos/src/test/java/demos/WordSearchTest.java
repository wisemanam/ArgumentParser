package demos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordSearchTest {
  @Test
  public void testDefaultGrid() {
    String[] args = {"h    a    l    ifax      ", "halifax"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "h:0,0 a:1,0 l:2,0 i:3,0 f:3,1 a:3,2 x:3,3");

    String[] args2 = {"softswoskaolzilklqmtreyoy", "yellow"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "y:4,2 e:4,1 l:3,1 l:2,1 o:1,1 w:1,0");

    String[] args3 = {"w    ol    l    ey       ", "yellow"};
    WordSearch w3 = new WordSearch();
    assertEquals(w3.wordsearch(args3), "y:3,2 e:3,1 l:2,1 l:1,1 o:1,0 w:0,0");
  }

  @Test
  public void testOverwriteHeight() {
    String[] args = {"tarsdfgelhijkom", "molerat", "--height", "3"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "m:2,4 o:2,3 l:1,3 e:1,2 r:0,2 a:0,1 t:0,0");

    String[] args2 = {"euimngtrklnwmhaavanneaptyqsvbt", "savannah", "--height", "6"};
    WordSearch w2 = new WordSearch();
    assertEquals(w2.wordsearch(args2), "s:5,1 a:4,1 v:3,1 a:3,2 n:3,3 n:3,4 a:2,4 h:2,3");
  }

  @Test
  public void testOverwriteWidth() {
    String[] args = {"rmgvoynelemsylilwcbq", "emily", "--width", "4"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "e:2,1 m:2,2 i:3,2 l:3,1 y:3,0");
  }
}
