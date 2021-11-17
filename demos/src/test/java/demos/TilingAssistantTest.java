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
        String[] args = {"4.5", "4", "--tilesize", "1"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in) 6:(0.5 x 1.0 in)");
    }

    @Test
    public void testPartialOnSides() {
        String[] args = {"4", "4.5", "--tilesize", "1"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in) 6:(1.0 x 0.5 in)");
    }

    @Test
    public void testPartialOnALlSides() {
        String[] args = {"4.5", "4.5", "--tilesize", "1"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in) 6:(1.0 x 0.5 in), 6:(0.5 x 1.0 in), 4:(0.5 x 0.5 in)");
    }

    @Test
    public void testFullOnly() {
        String[] args = {"4.5", "4.5", "--tilesize", "1", "--fullonly"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "6:(1.0 x 1.0 in)");
    }

    @Test
    public void testMetric() {
        String[] args = {"28.5", "-fm", "-g", "0.25", "-s", "4.75", "22"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "20:(4.75 x 4.75 cm)");
    }

    @Test
    public void testArgumentLengthNotGiven() {
        String[] args = {};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: the argument length is required");
    }

    @Test
    public void testArgumentWidthNotGiven() {
        String[] args = {"12"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: the argument width is required");
    }

    @Test public void testTooManyPositionalArguments() {
        String[] args = {"12", "15", "21.0"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: the value 21.0 matches no argument");
    }

    @Test public void testNegativeLength() {
        String[] args = {"-12.0", "15"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: length must be positive");
    }

    @Test public void testNegativeWidth() {
        String[] args = {"12.0", "-1.0"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: width must be positive");
    }

    @Test public void testNegativeTileSize() {
        String[] args = {"12.0", "15.0", "--tilesize", "-2.0"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: tilesize must be positive");
    }

    @Test public void testNegativeGroutGap() {
        String[] args = {"12.0", "15.0", "-g", "-2.0"};
        TilingAssistant t = new TilingAssistant();
        String result = t.tileAssistant(args);
        assertEquals(result, "TilingAssistant error: groutgap must be positive");
    }
}
