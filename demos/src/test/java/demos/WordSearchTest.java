package demos;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordSearchTest {
    @Test
    public void testDefaultGrid() {
        String grid = "h    a    l    ifax      ";
        WordSearch wordsearch = new WordSearch();
        assertTrue(wordsearch.search(grid, "halifax"));

        String grid2 = "softsweskaolzilklqmtreyoy";
        WordSearch wordsearch2 = new WordSearch();
        assertTrue(wordsearch2.search(grid2, "yellow"));

        String grid3 = ""
  }
}
