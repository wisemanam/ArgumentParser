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

    @Test
    public void testPartialOnTopAndBottom() {
        String[] args = {"4.5", "4", "--tilesize", "1"}
        TilingAssistant t = new TilingAssistant();
        String result = tileassistant.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in) 6:(0.5 x 1.0)")
    }

    @Test
    public void testPartialOnSides() {
        String[] args = {"4", "4.5", "--tilesize", "1"}
        TilingAssistant t = new TilingAssistant();
        String result = tileassistant.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in) 6:(0.5 x 1.0)")
    }
}
