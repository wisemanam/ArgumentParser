// package demos;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;

public class WordSearchTest {
  @Test
  public void testDefaultGrid() {
    String[] args = {"h    a    l    ifax      ", "halifax"};
    WordSearch wordsearch = new WordSearch(args);
    assertTrue(wordsearch.search());

    String[] args = {"softsweskaolzilklqmtreyoy", "yellow"};
    WordSearch wordsearch2 = new WordSearch();
    assertTrue(wordsearch2.search(grid2, "yellow"));

    String grid3 = "w    ol    l    ey       ";
    WordSearch wordsearch3 = new WordSearch();
    assertTrue(wordsearch.search(grid3, "yellow"));
  }

  @Test
  public void testOverwriteHeightNoRepeat() {
    String grid = "tacsdfgelhijkam"
    WordSearch wordsearch = new WordSearch();
    assertTrue(wordsearch.search(grid, "malecat"));
  }

  @Test
  public void testOverwriteWidthNoRepeat() {
    String grid = ""
  }
}
=======
// public class WordSearchTest {
//   /*
//     @Test
//     public void testDefaultGrid() {
//         String grid = "h    a    l    ifax      ";
//         WordSearch wordsearch = new WordSearch();
//         assertTrue(wordsearch.search({grid, "halifax"}));

//         String grid2 = "softsweskaolzilklqmtreyoy";
//         WordSearch wordsearch2 = new WordSearch();
//         assertTrue(wordsearch2.search({grid2, "yellow"}));

//         String grid3 = "";
//   }
//   */
// }
>>>>>>> 777e124b90619481a356abe540883943e45def55
