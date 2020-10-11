package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.FocusGame.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class zhongxuanTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(100);

    @Test
    public void task3True(){
        assertTrue(isPlacementStringWellFormed("a000b013c113d302e323"));
        assertTrue(isPlacementStringWellFormed("a000b013c113d302e323f400g420h522i613j701"));
    }

    @Test
    public void task3Flase(){
        assertFalse(isPlacementStringWellFormed("a000b013c113d302e323f400g420h522i613j70"));
        assertFalse(isPlacementStringWellFormed("a000b013c113d302e323f400g420h522i613j704"));
    }
}
