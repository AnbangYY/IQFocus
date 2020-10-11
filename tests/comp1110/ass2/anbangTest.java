package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.FocusGame.isPiecePlacementWellFormed;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class anbangTest {
    @Rule
public Timeout globalTimeout = Timeout.millis(100);

    @Test
    public void task3True(){
        assertTrue(isPiecePlacementWellFormed("a123"));
        assertTrue(isPiecePlacementWellFormed("b123"));
        assertTrue(isPiecePlacementWellFormed("c012"));
        assertTrue(isPiecePlacementWellFormed("e023"));
        assertTrue(isPiecePlacementWellFormed("d013"));
    }

    @Test
    public void task3False(){
        assertFalse(isPiecePlacementWellFormed("q123"));
        assertFalse(isPiecePlacementWellFormed("o999"));
        assertFalse(isPiecePlacementWellFormed("4396"));
        assertFalse(isPiecePlacementWellFormed("77777777777"));
        assertFalse(isPiecePlacementWellFormed("clearlove"));

}
}
