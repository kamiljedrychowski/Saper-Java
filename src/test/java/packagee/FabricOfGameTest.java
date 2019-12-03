package packagee;

import org.junit.Test;

import static org.junit.Assert.*;

public class FabricOfGameTest {

    @Test
    public void letsplay1() {
        Game gTest = new Game.Builder().col(5).row(5).bombs(3).build();

        assertNotNull(gTest);
    }

    @Test
    public void letsplay2() {

        Game gTest1 = FactoryOfGame.letsplay(1,false);

        assertNotNull(gTest1);
    }

    @Test
    public void letsplay3() {
        Game gTest = new Game.Builder().col(5).row(5).bombs(3).build();


        assertFalse(gTest instanceof GMode);
    }

    @Test
    public void letsplay4() {
        Game gTest1 = FactoryOfGame.letsplay(1,false);
        assertFalse(gTest1 instanceof GMode);
    }

    @Test
    public void letsplay5() {
        GMode gTest = (GMode) new GMode.Builder().col(5).row(5).bombs(3).build();

        assertNotNull(gTest);
    }

    @Test
    public void letsplay6() {
        GMode gTest1 = (GMode) FactoryOfGame.letsplay(1,true);

        assertNotNull(gTest1);
    }
}