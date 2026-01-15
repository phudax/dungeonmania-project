package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SnakeTest {
    @Test
    @DisplayName("Test snake in line with SnakeFood and moves towards them")
    public void simpleMovement() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_simpleMovement", "c_snakeTest_simpleMovement");

        assertEquals(new Position(5, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(1, 3), getSnakePos(res));
    }

    @Test
    @DisplayName("Test snake movement with all SnakeFood and moves towards them")
    public void simpleMovementToAllFood() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_simpleMovementToAllFood", "c_snakeTest_simpleMovement");

        assertEquals(new Position(9, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(8, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(7, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(6, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(1, 3), getSnakePos(res));
    }

    @Test
    @DisplayName("Test snake movement with all SnakeFood and moves towards them")
    public void complicatedMovements() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_complicatedMovements", "c_snakeTest_simpleMovement");

        assertEquals(new Position(9, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(9, 2), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(9, 1), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(8, 1), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(7, 1), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(7, 2), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(7, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(6, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 3), getSnakePos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(1, 3), getSnakePos(res));

    }

    @Test
    @DisplayName("Test snake invincibility")
    public void invincibility() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_invincibility", "c_snakeTest_simpleMovement");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 2), getSnakePos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(2, TestUtils.getEntities(res, "snake_head").size());
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(3, TestUtils.getEntities(res, "snake_head").size());
    }

    @Test
    @DisplayName("Test player battles snake and player dies")
    public void testPlayerDiesWhenBattleSnake() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = TestUtils.genericSnakeSequence(controller,
                "c_snakeTest_basicBattlePlayerDies");
        List<EntityResponse> entities = postBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }

    @Test
    @DisplayName("Test Arrow buff")
    public void testArrowBuff() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_ArrowBuff", "c_snakeTest_battleArrowBuff");
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }

    private Position getSnakePos(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_head").get(0).getPosition();
    }

    @Test
    @DisplayName("Test treasure Buff")
    public void testTreasureBuff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_TreasureBuff", "c_snakeTest_battleTreasureBuff");
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }

    @Test
    @DisplayName("Test key Buff")
    public void testKeyBuff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_KeyBuff", "c_snakeTest_battleKeyBuff");
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }
}
