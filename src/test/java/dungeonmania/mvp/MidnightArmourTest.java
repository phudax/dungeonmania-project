package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @DisplayName("Test InvalidActionException is raised when the player "
            + "does not have sufficient items to build MidnightArmour")
    public void buildInvalidActionException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_BuildablesTest_BuildInvalidArgumentException", "c_BuildablesTest_BuildInvalidArgumentException");
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @DisplayName("Build with sword , sunStone and no zombies")
    public void midnightArmourBuild() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_build", "c_midnightArmourTest_build");
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        //Build MidnightArmour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @DisplayName("Build with sword , sunStone and with zombies")
    public void midnightArmourBuildWithZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_zombies", "c_midnightArmourTest_zombies");
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        //Build MidnightArmour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // cant build
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @DisplayName("Test midnightArmour reduces enemy attack")
    public void testArmourReducesEnemyAttack() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_midnightArmour_battle";
        DungeonResponse res = controller.newGame("d_midnightArmour_battle", config);

        // Pick up Wood and sun_stone
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Pick up sword
        res = controller.tick(Direction.RIGHT);

        // Pick up key
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        res = controller.build("midnight_armour");
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
        res = controller.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);

        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: Shield effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - shield effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("spider_attack", config));
        int armourEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        int expectedDamage = (enemyAttack - armourEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
    }

    @Test
    @DisplayName("Test midnightArmour reduces enemy attack")
    public void testArmourIncreasesPlayerAttack() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_midnightArmour_battle";
        DungeonResponse res = controller.newGame("d_midnightArmour_battle", config);

        // Pick up Wood and sun_stone
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Pick up sword
        res = controller.tick(Direction.RIGHT);

        // Pick up key
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        res = controller.build("midnight_armour");
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
        res = controller.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);

        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: Shield effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - shield effect
        double playerAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double armourEffect = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));
        double expectedDamage = (playerAttack + armourEffect) / 5.0;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaEnemyHealth(), 0.001);
    }
}
