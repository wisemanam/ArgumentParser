// package demos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordSearchTest {
  @Test
  public void testDefaultGrid() {
    String[] args = {"h    a    l    ifax      ", "halifax"};
    WordSearch w = new WordSearch();
    assertEquals(w.wordsearch(args), "h:0,0 a:1,0 l:2,0 i:3,0 f:3,1 a:3,2 x:3,3");

    // String[] args2 = {"softsweskaolzilklqmtreyoy", "yellow"};
    // WordSearch w2 = new WordSearch();
    // assertTrue(w2.wordsearch(args2));

    // String grid3 = "w    ol    l    ey       ";
    // WordSearch w = new WordSearch();
    // assertTrue(wordsearch.search(grid3, "yellow"));
  }

//   @Test
//   public void testOverwriteHeightNoRepeat() {
//     String grid = "tarsdfgelhijkom"
//     WordSearch wordsearch = new WordSearch();
//     assertTrue(wordsearch.search(grid, "molerat"));
//   }

//   @Test
//   public void testOverwriteWidthNoRepeat() {
//     String grid = ""
//   }
}
