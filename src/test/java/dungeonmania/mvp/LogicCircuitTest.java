package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicCircuitTest {
    private boolean bulbOff(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "light_bulb_off").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean bulbOn(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "light_bulb_on").anyMatch(it -> it.getPosition().equals(pos));
    }

     @Test
    @DisplayName("Test OR bulb on and off multiple switches")
    public void orBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicCircuitTest", "c_basicGoalsTest_exit");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOn(res, 5, 1));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        assertTrue(bulbOff(res, 5, 1));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        assertTrue(bulbOn(res, 5, 1));

    }

     @Test
    @DisplayName("Test XOR bulb on and off multiple switches")
    public void xorBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicCircuitTest", "c_basicGoalsTest_exit");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOn(res, 7, 1));

        res = dmc.tick(Direction.UP);

        assertTrue(bulbOff(res, 7, 1));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertTrue(bulbOn(res, 7, 1));

    }

     @Test
    @DisplayName("Test AND bulb on and off multiple switches")
    public void andBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicCircuitTest", "c_basicGoalsTest_exit");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOff(res, 9, 4));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOn(res, 9, 4));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        assertTrue(bulbOff(res, 9, 4));

    }

     @Test
    @DisplayName("Test CO_AND bulb on and off multiple switches")
    public void coAndBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicCircuitTest", "c_basicGoalsTest_exit");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOff(res, 9, 2));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertTrue(bulbOn(res, 9, 2));

    }


     @Test
    @DisplayName("Test multiple switch door types")
    public void switchDoors() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchDoorTest", "c_basicGoalsTest_exit");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        //2 switches activated
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        //assert player could walk through the or switch (2 activations)
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        //assert player could not move through xor switch door (2 activations)
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Test placing logic bomb next to active switch")
    public void bombSwitch() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicBombTest", "c_bombTest_placeBombRadius2");

        //Player moves boulder
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);

        //place and bomb next to 3 active switches
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        //test 1 bomb left after blowing up
        assertEquals(1, TestUtils.getEntities(res, "bomb").size());


        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        //place or bomb next to 2 active switches
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        //test no bombs left after blowing up
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());

    }

}
