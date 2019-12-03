package packagee;

import static org.junit.Assert.*;

public class GameTest {

    @org.junit.Test
    public void allBombs1() {
        Game gTest = new Game.Builder().col(10).row(10).bombs(50).build();

        assertEquals(50,gTest.allBombs().size());
    }

    @org.junit.Test
    public void allBombs2() {
        Game gTest = new Game.Builder().col(100).row(100).bombs(999).build();

        assertEquals(999,gTest.allBombs().size());
    }

    @org.junit.Test
    public void allBombs3() {
        Game gTest = new Game.Builder().col(10).row(10).bombs(9).build();
        assertNotEquals(15,gTest.allBombs().size());
    }
}