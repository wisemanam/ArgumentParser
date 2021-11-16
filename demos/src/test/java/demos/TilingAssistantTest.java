package demos;

import org.junit.jupiter.api.Test;

public class TilingAssistantTest {
    @Test
    public void testBasicGrid() {
        String[] args = {"4", "4", "--tilesize", "1"};
        TilingAssistant tileassistant = new TilingAssistant();
        String result = tileassistant.tileAssistant(args);
        assertEquals(result, "9:(1.0 x 1.0 in)");
    }
}
